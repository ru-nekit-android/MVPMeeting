package ru.nekit.android.clean_architecture.presentation.core.presenter;

import android.support.annotation.NonNull;

public interface IComponentFactory<C> {

    @NonNull
    C createComponent();
}
