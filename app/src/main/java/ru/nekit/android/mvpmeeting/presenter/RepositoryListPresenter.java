package ru.nekit.android.mvpmeeting.presenter;

import android.os.Bundle;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import ru.nekit.android.mvpmeeting.model.GithubModel;
import ru.nekit.android.mvpmeeting.model.IGithubModel;
import ru.nekit.android.mvpmeeting.model.interactors.GetRepositoriesInteractor;
import ru.nekit.android.mvpmeeting.presenter.base.MVPBasePresenter;
import ru.nekit.android.mvpmeeting.presenter.mapper.RepositoryListMapper;
import ru.nekit.android.mvpmeeting.presenter.vo.Repository;
import ru.nekit.android.mvpmeeting.utils.rx.RxTransformers;
import ru.nekit.android.mvpmeeting.view.fragments.IRepositoryListView;

/**
 * Created by ru.nekit.android on 02.03.16.
 */
public class RepositoryListPresenter extends MVPBasePresenter<IRepositoryListView, IGithubModel> {

    private static final String BUNDLE_REPOSITORY_LIST_KEY = "repository_list_key";

    private final RepositoryListMapper mMapper;
    private final GetRepositoriesInteractor mGetRepositoriesInteractor;
    private List<Repository> mRepositoryList;


    public RepositoryListPresenter() {
        super(new GithubModel());
        mMapper = new RepositoryListMapper();
        mGetRepositoriesInteractor = new GetRepositoriesInteractor(getModel().getApiInterface());
    }

    @Override
    public IGithubModel getModel() {
        return model;
    }

    public void onSearchClick() {
        IRepositoryListView view = getView();
        if (isAttached()) {
            String userName = view.getUserName();
            if (TextUtils.isEmpty(userName)) return;
            addSubscriber(mGetRepositoriesInteractor.get(userName)
                    .map(mMapper)
                    .compose(RxTransformers.applyOperationBeforeAndAfter((Runnable) () -> {
                        if (isAttached()) {
                            view.setData(null);
                            view.showContent();
                            view.showLoading();
                        }
                    }, (Runnable) () -> {
                        if (isAttached()) {
                            view.hideLoading();
                        }
                    }))
                    .subscribe(result -> {
                        if (result != null && !result.isEmpty()) {
                            mRepositoryList = result;
                            view.setData(result);
                            view.showContent();
                        } else {
                            view.showEmptyList();
                        }
                    }, view::showError));
        }
    }

    private boolean isRepoListEmpty() {
        return mRepositoryList == null || mRepositoryList.isEmpty();
    }

    public void onCreate(Bundle savedState) {
        IRepositoryListView view = getView();
        if (savedState != null) {
            mRepositoryList = (List<Repository>) savedState.getSerializable(BUNDLE_REPOSITORY_LIST_KEY);
            if (isAttached()) {
                view.setData(mRepositoryList);
                view.showContent();
            }
        } else {
            view.showEmptyList();
        }
    }

    public void onSaveInstanceState(Bundle outState) {
        if (!isRepoListEmpty()) {
            outState.putSerializable(BUNDLE_REPOSITORY_LIST_KEY, new ArrayList<>(mRepositoryList));
        }
    }

    public void selectRepository(Repository repository) {
        if (isAttached()) {
            getView().showRepository(repository);
        }
    }
}
