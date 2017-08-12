package pl.patrykks.service.impl;

import pl.patrykks.dao.FacebookDAO;
import pl.patrykks.domain.Facebook;
import pl.patrykks.domain.Post;
import pl.patrykks.exceptions.NotFoundException;
import pl.patrykks.service.FacebookService;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingLong;

public class DefaultFacebookService implements FacebookService {
    private FacebookDAO dao;

    public DefaultFacebookService(FacebookDAO dao) {
        this.dao = dao;
    }

    public Facebook findById(String id) throws NotFoundException {
        return dao.findById(id);
    }

    public Map<String, Long> findMostCommonWords() {
        return dao.findAll().stream().
                flatMap(facebook -> facebook.getPosts().stream()).
                flatMap(post -> Arrays.stream(post.getMessage().split("\\s+"))).
                collect(groupingBy(Function.identity(), summingLong(e -> 1)));
    }

    public Set<String> findPostIdsByKeyword(String word) {
        return dao.findAll().stream().
                flatMap(facebook -> facebook.getPosts().stream()).
                filter(post -> post.getMessage().contains(word)).
                map(Post::getId).
                collect(Collectors.toSet());
    }

    public Set<Facebook> findAll() {
        Comparator<Facebook> personComparator = Comparator.comparing(Facebook::getLastname)
                .thenComparing(Comparator.comparing(Facebook::getFirstname));

        SortedSet<Facebook> sortedFacebookProfiles = new TreeSet<>(personComparator);
        sortedFacebookProfiles.addAll(dao.findAll());

        return sortedFacebookProfiles;

    }
}
