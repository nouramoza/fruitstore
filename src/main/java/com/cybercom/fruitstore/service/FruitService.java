package com.cybercom.fruitstore.service;

import com.cybercom.fruitstore.domain.Fruit;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Fruit.
 */
public interface FruitService {

    /**
     * Save a fruit.
     *
     * @param fruit the entity to save
     * @return the persisted entity
     */
    Fruit save(Fruit fruit);

    /**
     * Get all the fruits.
     *
     * @return the list of entities
     */
    List<Fruit> findAll();


    /**
     * Get the "id" fruit.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Fruit> findOne(Long id);

    /**
     * Delete the "id" fruit.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
