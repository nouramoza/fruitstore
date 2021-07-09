package com.cybercom.fruitstore.service.impl;

import com.cybercom.fruitstore.service.FruitService;
import com.cybercom.fruitstore.domain.Fruit;
import com.cybercom.fruitstore.repository.FruitRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Fruit.
 */
@Service
@Transactional
public class FruitServiceImpl implements FruitService {

    private final Logger log = LoggerFactory.getLogger(FruitServiceImpl.class);

    private final FruitRepository fruitRepository;

    public FruitServiceImpl(FruitRepository fruitRepository) {
        this.fruitRepository = fruitRepository;
    }

    /**
     * Save a fruit.
     *
     * @param fruit the entity to save
     * @return the persisted entity
     */
    @Override
    public Fruit save(Fruit fruit) {
        log.debug("Request to save Fruit : {}", fruit);
        return fruitRepository.save(fruit);
    }

    /**
     * Get all the fruits.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Fruit> findAll() {
        log.debug("Request to get all Fruits");
        return fruitRepository.findAll();
    }


    /**
     * Get one fruit by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Fruit> findOne(Long id) {
        log.debug("Request to get Fruit : {}", id);
        return fruitRepository.findById(id);
    }

    /**
     * Delete the fruit by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Fruit : {}", id);
        fruitRepository.deleteById(id);
    }
}
