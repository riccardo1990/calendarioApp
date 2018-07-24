package com.trenota.calendario.web.rest;

import com.trenota.calendario.CalendarioApp;

import com.trenota.calendario.domain.Calendario;
import com.trenota.calendario.repository.CalendarioRepository;
import com.trenota.calendario.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static com.trenota.calendario.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CalendarioResource REST controller.
 *
 * @see CalendarioResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CalendarioApp.class)
public class CalendarioResourceIntTest {

    private static final String DEFAULT_COD_CALENDARIO = "AAAAAAAAAA";
    private static final String UPDATED_COD_CALENDARIO = "BBBBBBBBBB";

    private static final String DEFAULT_DESC_CALENDARIO = "AAAAAAAAAA";
    private static final String UPDATED_DESC_CALENDARIO = "BBBBBBBBBB";

    private static final Integer DEFAULT_ANNO = 1;
    private static final Integer UPDATED_ANNO = 2;

    private static final LocalDate DEFAULT_DATA_CALENDARIO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_CALENDARIO = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private CalendarioRepository calendarioRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCalendarioMockMvc;

    private Calendario calendario;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CalendarioResource calendarioResource = new CalendarioResource(calendarioRepository);
        this.restCalendarioMockMvc = MockMvcBuilders.standaloneSetup(calendarioResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Calendario createEntity(EntityManager em) {
        Calendario calendario = new Calendario()
            .codCalendario(DEFAULT_COD_CALENDARIO)
            .descCalendario(DEFAULT_DESC_CALENDARIO)
            .anno(DEFAULT_ANNO)
            .dataCalendario(DEFAULT_DATA_CALENDARIO);
        return calendario;
    }

    @Before
    public void initTest() {
        calendario = createEntity(em);
    }

    @Test
    @Transactional
    public void createCalendario() throws Exception {
        int databaseSizeBeforeCreate = calendarioRepository.findAll().size();

        // Create the Calendario
        restCalendarioMockMvc.perform(post("/api/calendarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(calendario)))
            .andExpect(status().isCreated());

        // Validate the Calendario in the database
        List<Calendario> calendarioList = calendarioRepository.findAll();
        assertThat(calendarioList).hasSize(databaseSizeBeforeCreate + 1);
        Calendario testCalendario = calendarioList.get(calendarioList.size() - 1);
        assertThat(testCalendario.getCodCalendario()).isEqualTo(DEFAULT_COD_CALENDARIO);
        assertThat(testCalendario.getDescCalendario()).isEqualTo(DEFAULT_DESC_CALENDARIO);
        assertThat(testCalendario.getAnno()).isEqualTo(DEFAULT_ANNO);
        assertThat(testCalendario.getDataCalendario()).isEqualTo(DEFAULT_DATA_CALENDARIO);
    }

    @Test
    @Transactional
    public void createCalendarioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = calendarioRepository.findAll().size();

        // Create the Calendario with an existing ID
        calendario.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCalendarioMockMvc.perform(post("/api/calendarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(calendario)))
            .andExpect(status().isBadRequest());

        // Validate the Calendario in the database
        List<Calendario> calendarioList = calendarioRepository.findAll();
        assertThat(calendarioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodCalendarioIsRequired() throws Exception {
        int databaseSizeBeforeTest = calendarioRepository.findAll().size();
        // set the field null
        calendario.setCodCalendario(null);

        // Create the Calendario, which fails.

        restCalendarioMockMvc.perform(post("/api/calendarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(calendario)))
            .andExpect(status().isBadRequest());

        List<Calendario> calendarioList = calendarioRepository.findAll();
        assertThat(calendarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescCalendarioIsRequired() throws Exception {
        int databaseSizeBeforeTest = calendarioRepository.findAll().size();
        // set the field null
        calendario.setDescCalendario(null);

        // Create the Calendario, which fails.

        restCalendarioMockMvc.perform(post("/api/calendarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(calendario)))
            .andExpect(status().isBadRequest());

        List<Calendario> calendarioList = calendarioRepository.findAll();
        assertThat(calendarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAnnoIsRequired() throws Exception {
        int databaseSizeBeforeTest = calendarioRepository.findAll().size();
        // set the field null
        calendario.setAnno(null);

        // Create the Calendario, which fails.

        restCalendarioMockMvc.perform(post("/api/calendarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(calendario)))
            .andExpect(status().isBadRequest());

        List<Calendario> calendarioList = calendarioRepository.findAll();
        assertThat(calendarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCalendarios() throws Exception {
        // Initialize the database
        calendarioRepository.saveAndFlush(calendario);

        // Get all the calendarioList
        restCalendarioMockMvc.perform(get("/api/calendarios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(calendario.getId().intValue())))
            .andExpect(jsonPath("$.[*].codCalendario").value(hasItem(DEFAULT_COD_CALENDARIO.toString())))
            .andExpect(jsonPath("$.[*].descCalendario").value(hasItem(DEFAULT_DESC_CALENDARIO.toString())))
            .andExpect(jsonPath("$.[*].anno").value(hasItem(DEFAULT_ANNO)))
            .andExpect(jsonPath("$.[*].dataCalendario").value(hasItem(DEFAULT_DATA_CALENDARIO.toString())));
    }
    

    @Test
    @Transactional
    public void getCalendario() throws Exception {
        // Initialize the database
        calendarioRepository.saveAndFlush(calendario);

        // Get the calendario
        restCalendarioMockMvc.perform(get("/api/calendarios/{id}", calendario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(calendario.getId().intValue()))
            .andExpect(jsonPath("$.codCalendario").value(DEFAULT_COD_CALENDARIO.toString()))
            .andExpect(jsonPath("$.descCalendario").value(DEFAULT_DESC_CALENDARIO.toString()))
            .andExpect(jsonPath("$.anno").value(DEFAULT_ANNO))
            .andExpect(jsonPath("$.dataCalendario").value(DEFAULT_DATA_CALENDARIO.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingCalendario() throws Exception {
        // Get the calendario
        restCalendarioMockMvc.perform(get("/api/calendarios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCalendario() throws Exception {
        // Initialize the database
        calendarioRepository.saveAndFlush(calendario);

        int databaseSizeBeforeUpdate = calendarioRepository.findAll().size();

        // Update the calendario
        Calendario updatedCalendario = calendarioRepository.findById(calendario.getId()).get();
        // Disconnect from session so that the updates on updatedCalendario are not directly saved in db
        em.detach(updatedCalendario);
        updatedCalendario
            .codCalendario(UPDATED_COD_CALENDARIO)
            .descCalendario(UPDATED_DESC_CALENDARIO)
            .anno(UPDATED_ANNO)
            .dataCalendario(UPDATED_DATA_CALENDARIO);

        restCalendarioMockMvc.perform(put("/api/calendarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCalendario)))
            .andExpect(status().isOk());

        // Validate the Calendario in the database
        List<Calendario> calendarioList = calendarioRepository.findAll();
        assertThat(calendarioList).hasSize(databaseSizeBeforeUpdate);
        Calendario testCalendario = calendarioList.get(calendarioList.size() - 1);
        assertThat(testCalendario.getCodCalendario()).isEqualTo(UPDATED_COD_CALENDARIO);
        assertThat(testCalendario.getDescCalendario()).isEqualTo(UPDATED_DESC_CALENDARIO);
        assertThat(testCalendario.getAnno()).isEqualTo(UPDATED_ANNO);
        assertThat(testCalendario.getDataCalendario()).isEqualTo(UPDATED_DATA_CALENDARIO);
    }

    @Test
    @Transactional
    public void updateNonExistingCalendario() throws Exception {
        int databaseSizeBeforeUpdate = calendarioRepository.findAll().size();

        // Create the Calendario

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCalendarioMockMvc.perform(put("/api/calendarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(calendario)))
            .andExpect(status().isBadRequest());

        // Validate the Calendario in the database
        List<Calendario> calendarioList = calendarioRepository.findAll();
        assertThat(calendarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCalendario() throws Exception {
        // Initialize the database
        calendarioRepository.saveAndFlush(calendario);

        int databaseSizeBeforeDelete = calendarioRepository.findAll().size();

        // Get the calendario
        restCalendarioMockMvc.perform(delete("/api/calendarios/{id}", calendario.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Calendario> calendarioList = calendarioRepository.findAll();
        assertThat(calendarioList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Calendario.class);
        Calendario calendario1 = new Calendario();
        calendario1.setId(1L);
        Calendario calendario2 = new Calendario();
        calendario2.setId(calendario1.getId());
        assertThat(calendario1).isEqualTo(calendario2);
        calendario2.setId(2L);
        assertThat(calendario1).isNotEqualTo(calendario2);
        calendario1.setId(null);
        assertThat(calendario1).isNotEqualTo(calendario2);
    }
}
