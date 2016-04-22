package ru.nekit.android.clean_architecture.data.repository;

import android.support.annotation.NonNull;

import java.util.List;

import ru.nekit.android.clean_architecture.data.api.GithubApi;
import ru.nekit.android.clean_architecture.data.utils.rx.RxTransformers;
import ru.nekit.android.clean_architecture.domain.entities.RepositoryEntity;
import ru.nekit.android.clean_architecture.domain.repository.IGithubRepository;
import rx.Observable;
import rx.Scheduler;

/**
 * Created by ru.nekit.android on 15.03.16.
 */
public class GithubRepository implements IGithubRepository {

    private final GithubApi mGithubApi;
    private final Scheduler mLongOperationThread;
    private final Scheduler mMainThread;

    public GithubRepository(GithubApi githubApi,
                            Scheduler longOperationThread,
                            Scheduler mainThread) {
        mGithubApi = githubApi;
        mLongOperationThread = longOperationThread;
        mMainThread = mainThread;
    }

    @Override
    @NonNull
    public Observable<List<RepositoryEntity>> getRepositories(final String userName) {
        return mGithubApi.getRepositories(userName)
                .compose(RxTransformers.applySchedulers(mLongOperationThread, mMainThread));
    }
}
