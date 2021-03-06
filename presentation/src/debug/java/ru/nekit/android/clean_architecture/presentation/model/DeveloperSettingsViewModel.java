package ru.nekit.android.clean_architecture.presentation.model;

import android.support.annotation.NonNull;

import hu.supercluster.paperwork.Paperwork;

/**
 * Created by ru.nekit.android on 30.03.16.
 */
public class DeveloperSettingsViewModel implements IDeveloperSettingsViewModel {

    @NonNull
    private final Paperwork paperwork;

    public DeveloperSettingsViewModel(@NonNull Paperwork paperwork) {
        this.paperwork = paperwork;
    }

    @Override
    public String gitSha() {
        return paperwork.get("gitSha");
    }

    @Override
    public String buildDate() {
        return paperwork.get("buildDate");
    }
}
