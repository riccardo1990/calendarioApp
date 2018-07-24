package com.trenota.calendario.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.trenota.calendario.domain.Calendario;
import com.trenota.calendario.repository.CalendarioRepository;
import com.trenota.calendario.web.rest.errors.BadRequestAlertException;
import com.trenota.calendario.web.rest.util.HeaderUtil;
import com.trenota.calendario.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Calendario.
 */
@RestController
@RequestMapping("/api")
public class CalendarioResource {

    private final Logger log = LoggerFactory.getLogger(CalendarioResource.class);

    private static final String ENTITY_NAME = "calendario";

    private final CalendarioRepository calendarioRepository;

    public CalendarioResource(CalendarioRepository calendarioRepository) {
        this.calendarioRepository = calendarioRepository;
    }

    /**
     * POST  /calendarios : Create a new calendario.
     *
     * @param calendario the calendario to create
     * @return the ResponseEntity with status 201 (Created) and with body the new calendario, or with status 400 (Bad Request) if the calendario has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/calendarios")
    @Timed
    public ResponseEntity<Calendario> createCalendario(@Valid @RequestBody Calendario calendario) throws URISyntaxException {
        log.debug("REST request to save Calendario : {}", calendario);
        if (calendario.getId() != null) {
            throw new BadRequestAlertException("A new calendario cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Calendario result = calendarioRepository.save(calendario);
        return ResponseEntity.created(new URI("/api/calendarios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /calendarios : Updates an existing calendario.
     *
     * @param calendario the calendario to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated calendario,
     * or with status 400 (Bad Request) if the calendario is not valid,
     * or with status 500 (Internal Server Error) if the calendario couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/calendarios")
    @Timed
    public ResponseEntity<Calendario> updateCalendario(@Valid @RequestBody Calendario calendario) throws URISyntaxException {
        log.debug("REST request to update Calendario : {}", calendario);
        if (calendario.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Calendario result = calendarioRepository.save(calendario);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, calendario.getId().toString()))
            .body(result);
    }

    /**
     * GET  /calendarios : get all the calendarios.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of calendarios in body
     */
    @GetMapping("/calendarios")
    @Timed
    public ResponseEntity<List<Calendario>> getAllCalendarios(Pageable pageable) {
        log.debug("REST request to get a page of Calendarios");
        Page<Calendario> page = calendarioRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/calendarios");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /calendarios/:id : get the "id" calendario.
     *
     * @param id the id of the calendario to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the calendario, or with status 404 (Not Found)
     */
    @GetMapping("/calendarios/{id}")
    @Timed
    public ResponseEntity<Calendario> getCalendario(@PathVariable Long id) {
        log.debug("REST request to get Calendario : {}", id);
        Optional<Calendario> calendario = calendarioRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(calendario);
    }

    /**
     * DELETE  /calendarios/:id : delete the "id" calendario.
     *
     * @param id the id of the calendario to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/calendarios/{id}")
    @Timed
    public ResponseEntity<Void> deleteCalendario(@PathVariable Long id) {
        log.debug("REST request to delete Calendario : {}", id);

        calendarioRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
