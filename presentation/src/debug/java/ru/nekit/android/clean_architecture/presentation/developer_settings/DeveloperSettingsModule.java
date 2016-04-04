package ru.nekit.android.clean_architecture.presentation.developer_settings;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.github.pedrovgs.lynx.LynxActivity;
import com.github.pedrovgs.lynx.LynxConfig;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import hu.supercluster.paperwork.Paperwork;
import ru.nekit.android.clean_architecture.presentation.model.DeveloperSettingsModel;
import ru.nekit.android.clean_architecture.presentation.model.IDeveloperSettingsModel;
import ru.nekit.android.clean_architecture.presentation.navigation.NavigationToLogcatCommand;
import ru.nekit.android.clean_architecture.presentation.navigation.qualifier.NavigateToLogcat;
import ru.nekit.android.clean_architecture.presentation.presenter.DeveloperSettingsPresenter;
import ru.nekit.android.clean_architecture.presentation.view.other.IViewModifier;

/**
 * Created by ru.nekit.android on 29.03.16.
 */
@Module
public class DeveloperSettingsModule {

    @Provides
    @Singleton
    @NonNull
    public ILeakCanaryProxy provideILeakCanaryProxy(@NonNull Application application) {
        return new LeakCanaryProxy(application);
    }

    @NonNull
    @Provides
    @Singleton
    public LynxConfig provideLynxConfig() {
        return new LynxConfig();
    }

    @NonNull
    @Provides
    public IViewModifier provideIViewModifier() {
        return new MainActivityViewModifier();
    }

    @NonNull
    @Provides
    @Singleton
    public IDeveloperSettingsModel provideIDeveloperSettingsModel(@NonNull Paperwork paperwork) {
        return new DeveloperSettingsModel(paperwork);
    }

    @NonNull
    @Provides
    @Singleton
    public DeveloperSettingsPresenter provideDeveloperSettingsPresenter(@NonNull IDeveloperSettingsModel model) {
        return new DeveloperSettingsPresenter(model);
    }

    @NonNull
    @Provides
    @Singleton
    public Paperwork providePaperwork(@NonNull Application application) {
        return new Paperwork(application);
    }

    @NonNull
    @Provides
    @NavigateToLogcat
    public NavigationToLogcatCommand provideNavigationToLogcatCommand(@NonNull LynxConfig config) {
        return new NavigationToLogcatCommand() {
            @Override
            public void navigate() {
                Context context = getParentActivity();
                context.startActivity(LynxActivity.getIntent(context, config));
            }
        };
    }
}