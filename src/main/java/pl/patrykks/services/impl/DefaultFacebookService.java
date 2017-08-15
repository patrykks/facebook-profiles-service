package pl.patrykks.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.patrykks.datasources.FacebookDataSource;
import pl.patrykks.domain.FacebookProfile;
import pl.patrykks.domain.Post;
import pl.patrykks.exceptions.NotFoundException;
import pl.patrykks.services.FacebookService;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingLong;

public class DefaultFacebookService implements FacebookService {
    private static final Logger logger = LoggerFactory.getLogger(DefaultFacebookService.class);
    private FacebookDataSource facebookDataSource;

    public DefaultFacebookService(FacebookDataSource facebookDataSource) {
        this.facebookDataSource = facebookDataSource;
    }

    public FacebookProfile findById(String id) throws NotFoundException {
        return facebookDataSource.findById(id);
    }

    public Map<String, Long> findMostCommonWords() {
        logger.debug("Generating the word frequency histogram using posts content");
        return facebookDataSource.findAll().stream().
                flatMap(profile -> profile.getPosts().stream()).
                flatMap(post -> Arrays.stream(
                        post.getMessage().
                                toLowerCase().
                                split("\\W+"))).
                collect(groupingBy(Function.identity(), summingLong(e -> 1)));
    }

    public Set<String> findPostIdsByKeyword(String word) {
        logger.debug("Finding ids of posts containg word: {}", word);
        return facebookDataSource.findAll().stream().
                flatMap(profile -> profile.getPosts().stream()).
                filter(post ->
                        post.getMessage().
                                toLowerCase().
                                matches(".*\\b" + word.toLowerCase() + "\\b.*")).
                map(Post::getId).
                collect(Collectors.toSet());
    }

    public SortedSet<FacebookProfile> findAll() {
        logger.debug("Finding all stored facebook profiles");
        Comparator<FacebookProfile> personComparator = Comparator.comparing(FacebookProfile::getLastname)
                .thenComparing(Comparator.comparing(FacebookProfile::getFirstname));

        SortedSet<FacebookProfile> sortedProfiles = new TreeSet<>(personComparator);
        sortedProfiles.addAll(facebookDataSource.findAll());

        return sortedProfiles;

    }
}
