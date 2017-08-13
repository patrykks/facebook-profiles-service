package pl.patrykks.datasources;

import pl.patrykks.domain.Facebook;
import pl.patrykks.exceptions.NotFoundException;

import java.util.Set;

public interface FacebookDataSource {
    Set<Facebook> findAll();

    Facebook findById(String id) throws NotFoundException;

    void insert(Facebook facebook);
}