package ru.nekit.android.mvpmeeting.presentation.view.base;

/**
 * Created by MacOS on 18.03.16.
 */
public interface IStateableView<T extends ViewState> {

    void setState(T state);

    T getState();

}