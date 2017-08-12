package pl.patrykks.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.patrykks.dao.FacebookDAO;
import pl.patrykks.domain.Facebook;
import pl.patrykks.exceptions.NotFoundException;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class FlatFileFacebookDAO implements FacebookDAO {
    private static final Logger logger = LoggerFactory.getLogger(FlatFileFacebookDAO.class);
    private SortedMap<String, Facebook> facebookProfilesDataSource;

    public FlatFileFacebookDAO() {
        facebookProfilesDataSource = new TreeMap<>();
    }

    public Set<Facebook> findAll() {
        return new HashSet<>(facebookProfilesDataSource.values());
    }

    public Facebook findById(String id) throws NotFoundException {
        Facebook facebook = facebookProfilesDataSource.get(id);

        if (facebook == null) {
            throw new NotFoundException();
        }

        return facebook;
    }

    public void insert(Facebook facebook) {
        facebookProfilesDataSource.put(facebook.getId(), facebook);
    }
}
