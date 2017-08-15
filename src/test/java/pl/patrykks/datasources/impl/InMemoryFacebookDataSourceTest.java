package pl.patrykks.datasources.impl;

import org.junit.Before;
import org.junit.Test;
import pl.patrykks.datasources.FacebookDataSource;
import pl.patrykks.domain.FacebookProfile;
import pl.patrykks.exceptions.NotFoundException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class InMemoryFacebookDataSourceTest {
    private FacebookDataSource facebookDataSource;

    @Before
    public void initializeFacebookDataSource() {
        facebookDataSource  = new InMemoryFacebookDataSource();
    }

    @Test
    public void inMemeryFacebookDataSourceShouldBeAbleToFindProfile()  {
        //given
        FacebookProfile profile = mock(FacebookProfile.class);
        when(profile.getId()).thenReturn("1");

        //when
        facebookDataSource.insert(profile);

        //then
        assertEquals("1", facebookDataSource.findAll().stream().findAny().get().getId());
    }

    @Test
    public void inMemeryFacebookDataSourceShouldNotInsertFacebookWithNullId()  {
        //given
        FacebookProfile profile = mock(FacebookProfile.class);
        when(profile.getId()).thenReturn(null);

        //when
        facebookDataSource.insert(profile);

        //then
        assertEquals(0, facebookDataSource.findAll().size());
    }

    @Test(expected = NotFoundException.class)
    public void inMemeryFacebookDataSourceShouldThrowExceptionWhenFetchingProfileWhichNotExist() throws NotFoundException {
        //given
        //when
        facebookDataSource.findById("1");
        //then
    }

    @Test(expected = NotFoundException.class)
    public void inMemeryFacebookDataSrouceShouldBeAbleToHandleFindingProfileWithNullId() throws NotFoundException {
        //given
        //when
        facebookDataSource.findById(null);
        //then
    }
}
