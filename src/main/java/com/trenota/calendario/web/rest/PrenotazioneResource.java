package com.trenota.calendario.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.trenota.calendario.domain.Prenotazione;
import com.trenota.calendario.repository.PrenotazioneRepository;
import com.trenota.calendario.web.rest.errors.BadRequestAlertException;
import com.trenota.calendario.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Prenotazione.
 */
@RestController
@RequestMapping("/api")
public class PrenotazioneResource {

    private final Logger log = LoggerFactory.getLogger(PrenotazioneResource.class);

    private static final String ENTITY_NAME = "prenotazione";

    private final PrenotazioneRepository prenotazioneRepository;

    public PrenotazioneResource(PrenotazioneRepository prenotazioneRepository) {
        this.prenotazioneRepository = prenotazioneRepository;
    }

    /**
     * POST  /prenotaziones : Create a new prenotazione.
     *
     * @param prenotazione the prenotazione to create
     * @return the ResponseEntity with status 201 (Created) and with body the new prenotazione, or with status 400 (Bad Request) if the prenotazione has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/prenotaziones")
    @Timed
    public ResponseEntity<Prenotazione> createPrenotazione(@Valid @RequestBody Prenotazione prenotazione) throws URISyntaxException {
        log.debug("REST request to save Prenotazione : {}", prenotazione);
        if (prenotazione.getId() != null) {
            throw new BadRequestAlertException("A new prenotazione cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Prenotazione result = prenotazioneRepository.save(prenotazione);
        return ResponseEntity.created(new URI("/api/prenotaziones/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /prenotaziones : Updates an existing prenotazione.
     *
     * @param prenotazione the prenotazione to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated prenotazione,
     * or with status 400 (Bad Request) if the prenotazione is not valid,
     * or with status 500 (Internal Server Error) if the prenotazione couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/prenotaziones")
    @Timed
    public ResponseEntity<Prenotazione> updatePrenotazione(@Valid @RequestBody Prenotazione prenotazione) throws URISyntaxException {
        log.debug("REST request to update Prenotazione : {}", prenotazione);
        if (prenotazione.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Prenotazione result = prenotazioneRepository.save(prenotazione);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, prenotazione.getId().toString()))
            .body(result);
    }

    /**
     * GET  /prenotaziones : get all the prenotaziones.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of prenotaziones in body
     */
    @GetMapping("/prenotaziones")
    @Timed
    public List<Prenotazione> getAllPrenotaziones() {
        log.debug("REST request to get all Prenotaziones");
        return prenotazioneRepository.findAll();
    }

    /**
     * GET  /prenotaziones/:id : get the "id" prenotazione.
     *
     * @param id the id of the prenotazione to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the prenotazione, or with status 404 (Not Found)
     */
    @GetMapping("/prenotaziones/{id}")
    @Timed
    public ResponseEntity<Prenotazione> getPrenotazione(@PathVariable Long id) {
        log.debug("REST request to get Prenotazione : {}", id);
        Optional<Prenotazione> prenotazione = prenotazioneRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(prenotazione);
    }

    /**
     * DELETE  /prenotaziones/:id : delete the "id" prenotazione.
     *
     * @param id the id of the prenotazione to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/prenotaziones/{id}")
    @Timed
    public ResponseEntity<Void> deletePrenotazione(@PathVariable Long id) {
        log.debug("REST request to delete Prenotazione : {}", id);

        prenotazioneRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
