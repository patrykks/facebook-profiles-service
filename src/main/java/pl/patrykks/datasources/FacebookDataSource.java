package pl.patrykks.datasources;

import pl.patrykks.domain.FacebookProfile;
import pl.patrykks.exceptions.NotFoundException;

import java.util.Set;

public interface FacebookDataSource {
    Set<FacebookProfile> findAll();

    FacebookProfile findById(String id) throws NotFoundException;

    void insert(FacebookProfile profile);
}
