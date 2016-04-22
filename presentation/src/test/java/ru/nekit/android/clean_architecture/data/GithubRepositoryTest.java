package ru.nekit.android.clean_architecture.data;

import junit.framework.Assert;

import org.junit.Test;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import ru.nekit.android.clean_architecture.core.BaseTest;
import ru.nekit.android.clean_architecture.data.api.GithubApi;
import ru.nekit.android.clean_architecture.data.repository.GithubRepository;
import ru.nekit.android.clean_architecture.domain.entities.RepositoryEntity;
import ru.nekit.android.clean_architecture.domain.repository.IGithubRepository;
import ru.nekit.android.clean_architecture.presentation.di.qualifier.LongOperationThread;
import ru.nekit.android.clean_architecture.presentation.di.qualifier.MainThread;
import ru.nekit.android.clean_architecture.presentation.di.qualifier.UserName;
import rx.Observable;
import rx.Scheduler;
import rx.observers.TestSubscriber;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Created by ru.nekit.android on 22.04.16.
 */
public class GithubRepositoryTest extends BaseTest {

    @Inject
    protected GithubApi githubApi;

    @Inject
    @LongOperationThread
    protected Scheduler longOperationScheduler;

    @Inject
    @MainThread
    protected Scheduler mainThreadScheduler;

    @Inject
    @UserName
    protected String userName;

    @Inject
    protected List<RepositoryEntity> repositoryEntities;

    //real
    private IGithubRepository githubRepository;

    @Override
    public void setUp() throws IOException {
        super.setUp();
        testApplicationComponent.inject(this);
        githubRepository = new GithubRepository(githubApi, longOperationScheduler, mainThreadScheduler);
    }

    @Test
    public void testGetRepositoryList() {

        when(githubApi.getRepositories(userName)).thenReturn(Observable.just(repositoryEntities));

        TestSubscriber<List<RepositoryEntity>> subscriber = new TestSubscriber<>();
        githubRepository.getRepositories(userName).subscribe(subscriber);

        subscriber.assertNoErrors();
        subscriber.assertValueCount(1);
        subscriber.assertCompleted();

        List<RepositoryEntity> actual = subscriber.getOnNextEvents().get(0);
        Assert.assertNotNull(actual);
        Assert.assertEquals(30, actual.size());
        //first
        RepositoryEntity entity = actual.get(0);
        assertEquals(entity.getName(), "abs-search-view");
        assertTrue(entity.getId() == 5301791);
        //last
        entity = actual.get(actual.size() - 1);
        assertEquals(entity.getName(), "GIS");
        assertTrue(entity.getId() == 5528510);

    }
}
