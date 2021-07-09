package com.cybercom.fruitstore.web.rest;

import com.cybercom.fruitstore.domain.Fruit;
import com.cybercom.fruitstore.service.FruitService;
import com.cybercom.fruitstore.web.rest.util.HeaderUtil;
import com.cybercom.fruitstore.web.rest.util.ResponseUtil;
import com.cybercom.fruitstore.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Fruit.
 */
@RestController
@RequestMapping(path = "/api")
public class FruitResource {

    private static final String ENTITY_NAME = "fruit";
    private static final String applicationName = "fruit-store";
    private final Logger log = LoggerFactory.getLogger(FruitResource.class);
    private final FruitService fruitService;

    public FruitResource(FruitService fruitService) {
        this.fruitService = fruitService;
    }

    /**
     * POST  /fruits : Create a new fruit.
     *
     * @param fruit the fruit to create
     * @return the ResponseEntity with status 201 (Created) and with body the new fruit, or with status 400 (Bad Request) if the fruit has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/fruits")
    public ResponseEntity<Fruit> createFruit(@RequestBody Fruit fruit) throws URISyntaxException, BadRequestAlertException {
        log.debug("REST request to save Fruit : {}", fruit);
        if (fruit.getId() != null) {
            throw new BadRequestAlertException("A new fruit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Fruit result = fruitService.save(fruit);
        return ResponseEntity.created(new URI("/api/fruits/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /fruits : Updates an existing fruit.
     *
     * @param fruit the fruit to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated fruit,
     * or with status 400 (Bad Request) if the fruit is not valid,
     * or with status 500 (Internal Server Error) if the fruit couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/fruits")
    public ResponseEntity<Fruit> updateFruit(@RequestBody Fruit fruit) throws BadRequestAlertException {
        log.debug("REST request to update Fruit : {}", fruit);
        if (fruit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Fruit result = fruitService.save(fruit);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * GET  /fruits : get all the fruits.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of fruits in body
     */
    @GetMapping("/fruits")
    public List<Fruit> getAllFruits() {
        log.debug("REST request to get all Fruits");
        return fruitService.findAll();
    }

    /**
     * GET  /fruits/:id : get the "id" fruit.
     *
     * @param id the id of the fruit to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the fruit, or with status 404 (Not Found)
     */
    @GetMapping("/fruits/{id}")
    public ResponseEntity<Fruit> getFruit(@PathVariable Long id) {
        log.debug("REST request to get Fruit : {}", id);
        Optional<Fruit> fruit = fruitService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fruit);
    }

    /**
     * DELETE  /fruits/:id : delete the "id" fruit.
     *
     * @param id the id of the fruit to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/fruits/{id}")
    public ResponseEntity<Void> deleteFruit(@PathVariable Long id) {
        log.debug("REST request to delete Fruit : {}", id);
        fruitService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, null))
                .build();
    }
}
