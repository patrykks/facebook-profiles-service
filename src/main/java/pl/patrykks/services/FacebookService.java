package pl.patrykks.services;

import pl.patrykks.domain.FacebookProfile;
import pl.patrykks.exceptions.NotFoundException;

import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

public interface FacebookService {

    /**
     * Zwraca obiekt reprezentujący profil Facebooka na podstawie id
     *      * w czasie logarytmicznym
     */
    FacebookProfile findById(String id) throws NotFoundException;

    /**
     * Zwraca mapę której kluczem jest słowo a wartością liczba jego
     *      * wystąpień - pod uwagę brane są wszystkie posty      
     */
    Map<String, Long> findMostCommonWords();

    /**
     * Zwraca zbiór id Postów zawierających słowo word
     */
    Set<String> findPostIdsByKeyword(String word);

    /**
     *      * Zwraca zbiór obiektów reprezentujących profile Facebooka
     *      * posortowane po firstname, lastname
     */
    SortedSet<FacebookProfile> findAll();
}