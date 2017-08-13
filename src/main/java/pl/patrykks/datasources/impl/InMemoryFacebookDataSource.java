package pl.patrykks.datasources.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.patrykks.datasources.FacebookDataSource;
import pl.patrykks.domain.Facebook;
import pl.patrykks.exceptions.NotFoundException;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class InMemoryFacebookDataSource implements FacebookDataSource {
    private static final Logger logger = LoggerFactory.getLogger(InMemoryFacebookDataSource.class);
    private SortedMap<String, Facebook> facebookProfilesDataSource;

    public InMemoryFacebookDataSource() {
        facebookProfilesDataSource = new TreeMap<>();
    }

    public Set<Facebook> findAll() {
        logger.debug("Fetching all stored facebook profiles");
        return new HashSet<>(facebookProfilesDataSource.values());
    }

    public Facebook findById(String id) throws NotFoundException {
        logger.debug("Finding facebook profile with id: {}", id);
        Facebook facebook = facebookProfilesDataSource.get(id);

        if (facebook == null) {
            logger.info("Facebook profile with id: {} cannot be found", id);
            throw new NotFoundException();
        }

        logger.debug("Facebook profile with id: {} was found", id);
        return facebook;
    }

    public void insert(Facebook facebook) {
        logger.debug("Inserting facebook profile with id: {}", facebook.getId());
        facebookProfilesDataSource.put(facebook.getId(), facebook);
    }
}
