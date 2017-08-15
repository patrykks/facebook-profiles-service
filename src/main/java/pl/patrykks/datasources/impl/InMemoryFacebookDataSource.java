package pl.patrykks.datasources.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.patrykks.datasources.FacebookDataSource;
import pl.patrykks.domain.FacebookProfile;
import pl.patrykks.exceptions.NotFoundException;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class InMemoryFacebookDataSource implements FacebookDataSource {
    private static final Logger logger = LoggerFactory.getLogger(InMemoryFacebookDataSource.class);
    private SortedMap<String, FacebookProfile> facebookProfiles;

    public InMemoryFacebookDataSource() {
        facebookProfiles = new TreeMap<>();
    }

    public Set<FacebookProfile> findAll() {
        logger.debug("Fetching all stored facebook profiles");
        return new HashSet<>(facebookProfiles.values());
    }

    public FacebookProfile findById(String id) throws NotFoundException {
        logger.debug("Finding facebook profile with id: {}", id);

        if (id == null) {
            logger.warn("Trying to find facebook profile with id: {}", id);
            throw new NotFoundException();
        }

        FacebookProfile profile = facebookProfiles.get(id);

        if (profile == null) {
            logger.info("Facebook profile with id: {} cannot be found", id);
            throw new NotFoundException();
        }

        logger.debug("Facebook profile with id: {} was found", id);
        return profile;
    }

    public void insert(FacebookProfile profile) {
        logger.debug("Inserting facebook profile with id: {}", profile.getId());

        if (profile.getId() == null) {
            logger.warn("Cannot insert facebook profile with id: {}", profile.getId());
            return;
        }

        facebookProfiles.put(profile.getId(), profile);
    }
}
