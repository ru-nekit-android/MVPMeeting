package ru.nekit.android.clean_architecture.presentation.view.base;

import ru.nekit.android.clean_architecture.presentation.model.base.IMVPModel;

/**
 * Created by ru.nekit.android on 06.03.16.
 */
public interface ILCEView<M extends IMVPModel, E> extends IMVPView {

    void showLoading();

    void hideLoading();

    void showContent(M content);

    void hideContent();

    void showError(E e);
}