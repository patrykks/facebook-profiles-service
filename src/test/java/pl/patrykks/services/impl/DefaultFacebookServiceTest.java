package pl.patrykks.services.impl;

import org.junit.Test;
import pl.patrykks.datasources.FacebookDataSource;
import pl.patrykks.domain.FacebookProfile;
import pl.patrykks.domain.Post;
import pl.patrykks.services.FacebookService;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DefaultFacebookServiceTest {

    @Test
    public void serviceShouldReturnSetOfFacebookOrderedByLastnameThenByName() {
        //given
        String [] ids = {"1","2","3"};
        String [] firstNames = {"A","B","C"};
        String [] lastNames = {"F","F","E"};

        Set<FacebookProfile> unsortedProfiles = prepareFacebookProfilesToCheckSorting(ids, firstNames, lastNames);
        FacebookDataSource facebookDataSource = mock(FacebookDataSource.class);
        when(facebookDataSource.findAll()).thenReturn(unsortedProfiles);
        FacebookService facebookService = new DefaultFacebookService(facebookDataSource);

        //when
        List<FacebookProfile> sortedProfiles = new ArrayList<>(facebookService.findAll());

        //then
        assertEquals("3", sortedProfiles.get(0).getId());
        assertEquals("1", sortedProfiles.get(1).getId());
        assertEquals("2", sortedProfiles.get(2).getId());
    }

    @Test
    public void serviceShouldFindCorrectPostsIdsContaingGivenKeyword() {
        //given
        String keyword = "picture";
        String [] ids = {"1","2","3"};
        String [] postsIds = {"10","20","30"};
        String [] postMessages = {"Picture is beautiful. Curve is nice if you sit within The radius. Nice interface.",
                "What a great picture, what great sound.! No more glare.!!!! I love this TV.",
                "We definitely wound up with buyers remorse once we got it home and set up."};

        Set<FacebookProfile> profiles = prepareFacebookProfilesWithPosts(ids, postsIds, postMessages);
        FacebookDataSource facebookDataSource = mock(FacebookDataSource.class);
        when(facebookDataSource.findAll()).thenReturn(profiles);
        FacebookService facebookService = new DefaultFacebookService(facebookDataSource);

        //when
        Set<String> foundPostsIds = facebookService.findPostIdsByKeyword(keyword);

        //then
        assertEquals(2, foundPostsIds.size());
        assertEquals(true, foundPostsIds.contains("10"));
        assertEquals(true, foundPostsIds.contains("20"));
    }

    @Test
    public void serviceShouldCorrectlyCountWordInPosts() {
        //given
        String [] ids = {"1"};
        String [] postsIds = {"10"};
        String [] postMessages = {"Picture is beautiful. It is the most beautifull picture I have ever seen!!!"};

        Set<FacebookProfile> profiles = prepareFacebookProfilesWithPosts(ids, postsIds, postMessages);
        FacebookDataSource facebookDataSource = mock(FacebookDataSource.class);
        when(facebookDataSource.findAll()).thenReturn(profiles);
        FacebookService facebookService = new DefaultFacebookService(facebookDataSource);

        //when
        Map<String, Long> wordFrequencyMap = facebookService.findMostCommonWords();

        //then
        assertEquals(wordFrequencyMap.get("picture"), new Long(2));
    }

    @Test
    public void serviceShouldPutAllWordsFromPostsIntoWordFrequencyMap() {
        //given
        String [] ids = {"1"};
        String [] postsIds = {"10"};
        String [] postMessages = {"Picture is beautiful. It is the most beautiful picture I have ever seen!!!"};

        Set<FacebookProfile> profiles = prepareFacebookProfilesWithPosts(ids, postsIds, postMessages);
        FacebookDataSource facebookDataSource = mock(FacebookDataSource.class);
        when(facebookDataSource.findAll()).thenReturn(profiles);
        FacebookService facebookService = new DefaultFacebookService(facebookDataSource);

        //when
        Map<String, Long> wordFrequencyMap = facebookService.findMostCommonWords();

        //then
        assertEquals(wordFrequencyMap.values().size(), 10);
    }

    private Set<FacebookProfile> prepareFacebookProfilesToCheckSorting(String [] ids, String [] firstNames, String [] lastNames) {
        Set<FacebookProfile> profiles = new HashSet<>();

        for (int index = 0; index < ids.length; index++) {
            FacebookProfile profile = mock(FacebookProfile.class);
            when(profile.getId()).thenReturn(ids[index]);
            when(profile.getLastname()).thenReturn(lastNames[index]);
            when(profile.getFirstname()).thenReturn(firstNames[index]);
            profiles.add(profile);
        }

        return profiles;
    }

    private Set<FacebookProfile> prepareFacebookProfilesWithPosts(String [] ids, String [] postsIds, String [] postMessages) {
        Set<FacebookProfile> profiles = new HashSet<>();

        for (int index = 0; index < ids.length; index++) {
            FacebookProfile profile = mock(FacebookProfile.class);
            Post post = mock(Post.class);
            List<Post> posts = Collections.singletonList(post);

            when(profile.getId()).thenReturn(ids[index]);
            when(post.getId()).thenReturn(postsIds[index]);
            when(post.getMessage()).thenReturn(postMessages[index]);
            when(profile.getPosts()).thenReturn(posts);
            profiles.add(profile);
        }

        return profiles;
    }
}
