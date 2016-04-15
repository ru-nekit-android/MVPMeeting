package ru.nekit.android.clean_architecture.presentation.core.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.ref.WeakReference;

import ru.nekit.android.clean_architecture.presentation.core.model.IMVPModel;
import ru.nekit.android.clean_architecture.presentation.core.view.IMVPView;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by ru.nekit.android on 04.03.16.
 */
public abstract class MVPPresenter<V extends IMVPView, M extends IMVPModel> implements IMVPPresenter<V, M> {

    @Nullable
    private final M model;
    private CompositeSubscription subscriptionList;
    private WeakReference<V> mViewRef;

    protected MVPPresenter(@Nullable M model) {
        this.model = model;
    }

    protected void addSubscriber(@NonNull Subscription subscription) {
        if (subscriptionList == null) {
            subscriptionList = new CompositeSubscription();
        }
        subscriptionList.add(subscription);
    }

    public void onDestroy() {
        if (subscriptionList != null) {
            subscriptionList.clear();
        }
        subscriptionList = null;
    }

    @Override
    @NonNull
    public M getModel() {
        return model;
    }

    @Override
    abstract public void onAttachView();

    @Override
    public void attachView(@NonNull V view) {
        mViewRef = new WeakReference<>(view);
    }

    @Override
    public void detachView() {
        if (mViewRef != null) {
            mViewRef.clear();
        }
        mViewRef = null;
    }

    @Override
    @Nullable
    public V getView() {
        if (mViewRef != null && mViewRef.get() != null) {
            return mViewRef.get();
        }
        return null;
    }
}
