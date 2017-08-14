package pl.patrykks.services.impl;

import org.junit.Test;
import pl.patrykks.datasources.FacebookDataSource;
import pl.patrykks.domain.Facebook;
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

        Set<Facebook> facebookSet = prepareFacebookSetToCheckSorting(ids, firstNames, lastNames);
        FacebookDataSource facebookDataSource = mock(FacebookDataSource.class);
        when(facebookDataSource.findAll()).thenReturn(facebookSet);
        FacebookService facebookService = new DefaultFacebookService(facebookDataSource);

        //when
        List<Facebook> sortedFacebookProfiles = new ArrayList<>(facebookService.findAll());

        //then
        assertEquals("3", sortedFacebookProfiles.get(0).getId());
        assertEquals("1", sortedFacebookProfiles.get(1).getId());
        assertEquals("2", sortedFacebookProfiles.get(2).getId());
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

        Set<Facebook> facebookSet = prepareFacebookSetWithPosts(ids, postsIds, postMessages);
        FacebookDataSource facebookDataSource = mock(FacebookDataSource.class);
        when(facebookDataSource.findAll()).thenReturn(facebookSet);
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

        Set<Facebook> facebookSet = prepareFacebookSetWithPosts(ids, postsIds, postMessages);
        FacebookDataSource facebookDataSource = mock(FacebookDataSource.class);
        when(facebookDataSource.findAll()).thenReturn(facebookSet);
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

        Set<Facebook> facebookSet = prepareFacebookSetWithPosts(ids, postsIds, postMessages);
        FacebookDataSource facebookDataSource = mock(FacebookDataSource.class);
        when(facebookDataSource.findAll()).thenReturn(facebookSet);
        FacebookService facebookService = new DefaultFacebookService(facebookDataSource);

        //when
        Map<String, Long> wordFrequencyMap = facebookService.findMostCommonWords();

        //then
        assertEquals(wordFrequencyMap.values().size(), 10);
    }

    private Set<Facebook> prepareFacebookSetToCheckSorting(String [] ids, String [] firstNames, String [] lastNames) {
        Set<Facebook> facebookSet = new HashSet<>();

        for (int index = 0; index < ids.length; index++) {
            Facebook facebook = mock(Facebook.class);
            when(facebook.getId()).thenReturn(ids[index]);
            when(facebook.getLastname()).thenReturn(lastNames[index]);
            when(facebook.getFirstname()).thenReturn(firstNames[index]);
            facebookSet.add(facebook);
        }

        return facebookSet;
    }

    private Set<Facebook> prepareFacebookSetWithPosts(String [] ids, String [] postsIds, String [] postMessages) {
        Set<Facebook> facebookSet = new HashSet<>();

        for (int index = 0; index < ids.length; index++) {
            Facebook facebook = mock(Facebook.class);
            Post post = mock(Post.class);
            List<Post> posts = Collections.singletonList(post);

            when(facebook.getId()).thenReturn(ids[index]);
            when(post.getId()).thenReturn(postsIds[index]);
            when(post.getMessage()).thenReturn(postMessages[index]);
            when(facebook.getPosts()).thenReturn(posts);
            facebookSet.add(facebook);
        }

        return facebookSet;
    }
}
