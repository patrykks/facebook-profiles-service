package pl.patrykks.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.patrykks.datasources.FacebookDataSource;
import pl.patrykks.domain.Facebook;
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

    public Facebook findById(String id) throws NotFoundException {
        return facebookDataSource.findById(id);
    }

    public Map<String, Long> findMostCommonWords() {
        logger.debug("Generating the word frequency histogram using posts content");
        return facebookDataSource.findAll().stream().
                flatMap(facebook -> facebook.getPosts().stream()).
                flatMap(post -> Arrays.stream(
                        post.getMessage().
                                toLowerCase().
                                split("\\W+"))).
                collect(groupingBy(Function.identity(), summingLong(e -> 1)));
    }

    public Set<String> findPostIdsByKeyword(String word) {
        logger.debug("Finding ids of posts containg word: {}", word);
        return facebookDataSource.findAll().stream().
                flatMap(facebook -> facebook.getPosts().stream()).
                filter(post ->
                        post.getMessage().
                                toLowerCase().
                                matches(".*\\b" + word.toLowerCase() + "\\b.*")).
                map(Post::getId).
                collect(Collectors.toSet());
    }

    public Set<Facebook> findAll() {
        logger.debug("Finding all stored facebook profiles");
        Comparator<Facebook> personComparator = Comparator.comparing(Facebook::getLastname)
                .thenComparing(Comparator.comparing(Facebook::getFirstname));

        SortedSet<Facebook> sortedFacebookProfiles = new TreeSet<>(personComparator);
        sortedFacebookProfiles.addAll(facebookDataSource.findAll());

        return sortedFacebookProfiles;

    }
}
