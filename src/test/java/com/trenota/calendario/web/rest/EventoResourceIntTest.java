package com.trenota.calendario.web.rest;

import com.trenota.calendario.CalendarioApp;

import com.trenota.calendario.domain.Evento;
import com.trenota.calendario.repository.EventoRepository;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;


import static com.trenota.calendario.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.trenota.calendario.domain.enumeration.TipoEvento;
import com.trenota.calendario.domain.enumeration.TipoGenerazioneEvento;
/**
 * Test class for the EventoResource REST controller.
 *
 * @see EventoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CalendarioApp.class)
public class EventoResourceIntTest {

    private static final String DEFAULT_COD_EVENTO = "AAAAAAAAAA";
    private static final String UPDATED_COD_EVENTO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_FLAG_AUTOMATICO = false;
    private static final Boolean UPDATED_FLAG_AUTOMATICO = true;

    private static final Instant DEFAULT_DATA_DA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_DA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATA_A = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_A = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final TipoEvento DEFAULT_TIPO_EVENTO = TipoEvento.ALLOCATO;
    private static final TipoEvento UPDATED_TIPO_EVENTO = TipoEvento.LIBERO;

    private static final TipoGenerazioneEvento DEFAULT_TIPO_GENERAZIONE_EVENTO = TipoGenerazioneEvento.AUTOMATICO;
    private static final TipoGenerazioneEvento UPDATED_TIPO_GENERAZIONE_EVENTO = TipoGenerazioneEvento.MANUALE;

    @Autowired
    private EventoRepository eventoRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEventoMockMvc;

    private Evento evento;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EventoResource eventoResource = new EventoResource(eventoRepository);
        this.restEventoMockMvc = MockMvcBuilders.standaloneSetup(eventoResource)
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
    public static Evento createEntity(EntityManager em) {
        Evento evento = new Evento()
            .codEvento(DEFAULT_COD_EVENTO)
            .flagAutomatico(DEFAULT_FLAG_AUTOMATICO)
            .dataDa(DEFAULT_DATA_DA)
            .dataA(DEFAULT_DATA_A)
            .tipoEvento(DEFAULT_TIPO_EVENTO)
            .tipoGenerazioneEvento(DEFAULT_TIPO_GENERAZIONE_EVENTO);
        return evento;
    }

    @Before
    public void initTest() {
        evento = createEntity(em);
    }

    @Test
    @Transactional
    public void createEvento() throws Exception {
        int databaseSizeBeforeCreate = eventoRepository.findAll().size();

        // Create the Evento
        restEventoMockMvc.perform(post("/api/eventos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(evento)))
            .andExpect(status().isCreated());

        // Validate the Evento in the database
        List<Evento> eventoList = eventoRepository.findAll();
        assertThat(eventoList).hasSize(databaseSizeBeforeCreate + 1);
        Evento testEvento = eventoList.get(eventoList.size() - 1);
        assertThat(testEvento.getCodEvento()).isEqualTo(DEFAULT_COD_EVENTO);
        assertThat(testEvento.isFlagAutomatico()).isEqualTo(DEFAULT_FLAG_AUTOMATICO);
        assertThat(testEvento.getDataDa()).isEqualTo(DEFAULT_DATA_DA);
        assertThat(testEvento.getDataA()).isEqualTo(DEFAULT_DATA_A);
        assertThat(testEvento.getTipoEvento()).isEqualTo(DEFAULT_TIPO_EVENTO);
        assertThat(testEvento.getTipoGenerazioneEvento()).isEqualTo(DEFAULT_TIPO_GENERAZIONE_EVENTO);
    }

    @Test
    @Transactional
    public void createEventoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = eventoRepository.findAll().size();

        // Create the Evento with an existing ID
        evento.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEventoMockMvc.perform(post("/api/eventos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(evento)))
            .andExpect(status().isBadRequest());

        // Validate the Evento in the database
        List<Evento> eventoList = eventoRepository.findAll();
        assertThat(eventoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodEventoIsRequired() throws Exception {
        int databaseSizeBeforeTest = eventoRepository.findAll().size();
        // set the field null
        evento.setCodEvento(null);

        // Create the Evento, which fails.

        restEventoMockMvc.perform(post("/api/eventos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(evento)))
            .andExpect(status().isBadRequest());

        List<Evento> eventoList = eventoRepository.findAll();
        assertThat(eventoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataDaIsRequired() throws Exception {
        int databaseSizeBeforeTest = eventoRepository.findAll().size();
        // set the field null
        evento.setDataDa(null);

        // Create the Evento, which fails.

        restEventoMockMvc.perform(post("/api/eventos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(evento)))
            .andExpect(status().isBadRequest());

        List<Evento> eventoList = eventoRepository.findAll();
        assertThat(eventoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataAIsRequired() throws Exception {
        int databaseSizeBeforeTest = eventoRepository.findAll().size();
        // set the field null
        evento.setDataA(null);

        // Create the Evento, which fails.

        restEventoMockMvc.perform(post("/api/eventos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(evento)))
            .andExpect(status().isBadRequest());

        List<Evento> eventoList = eventoRepository.findAll();
        assertThat(eventoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTipoEventoIsRequired() throws Exception {
        int databaseSizeBeforeTest = eventoRepository.findAll().size();
        // set the field null
        evento.setTipoEvento(null);

        // Create the Evento, which fails.

        restEventoMockMvc.perform(post("/api/eventos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(evento)))
            .andExpect(status().isBadRequest());

        List<Evento> eventoList = eventoRepository.findAll();
        assertThat(eventoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTipoGenerazioneEventoIsRequired() throws Exception {
        int databaseSizeBeforeTest = eventoRepository.findAll().size();
        // set the field null
        evento.setTipoGenerazioneEvento(null);

        // Create the Evento, which fails.

        restEventoMockMvc.perform(post("/api/eventos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(evento)))
            .andExpect(status().isBadRequest());

        List<Evento> eventoList = eventoRepository.findAll();
        assertThat(eventoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEventos() throws Exception {
        // Initialize the database
        eventoRepository.saveAndFlush(evento);

        // Get all the eventoList
        restEventoMockMvc.perform(get("/api/eventos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(evento.getId().intValue())))
            .andExpect(jsonPath("$.[*].codEvento").value(hasItem(DEFAULT_COD_EVENTO.toString())))
            .andExpect(jsonPath("$.[*].flagAutomatico").value(hasItem(DEFAULT_FLAG_AUTOMATICO.booleanValue())))
            .andExpect(jsonPath("$.[*].dataDa").value(hasItem(DEFAULT_DATA_DA.toString())))
            .andExpect(jsonPath("$.[*].dataA").value(hasItem(DEFAULT_DATA_A.toString())))
            .andExpect(jsonPath("$.[*].tipoEvento").value(hasItem(DEFAULT_TIPO_EVENTO.toString())))
            .andExpect(jsonPath("$.[*].tipoGenerazioneEvento").value(hasItem(DEFAULT_TIPO_GENERAZIONE_EVENTO.toString())));
    }
    

    @Test
    @Transactional
    public void getEvento() throws Exception {
        // Initialize the database
        eventoRepository.saveAndFlush(evento);

        // Get the evento
        restEventoMockMvc.perform(get("/api/eventos/{id}", evento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(evento.getId().intValue()))
            .andExpect(jsonPath("$.codEvento").value(DEFAULT_COD_EVENTO.toString()))
            .andExpect(jsonPath("$.flagAutomatico").value(DEFAULT_FLAG_AUTOMATICO.booleanValue()))
            .andExpect(jsonPath("$.dataDa").value(DEFAULT_DATA_DA.toString()))
            .andExpect(jsonPath("$.dataA").value(DEFAULT_DATA_A.toString()))
            .andExpect(jsonPath("$.tipoEvento").value(DEFAULT_TIPO_EVENTO.toString()))
            .andExpect(jsonPath("$.tipoGenerazioneEvento").value(DEFAULT_TIPO_GENERAZIONE_EVENTO.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingEvento() throws Exception {
        // Get the evento
        restEventoMockMvc.perform(get("/api/eventos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEvento() throws Exception {
        // Initialize the database
        eventoRepository.saveAndFlush(evento);

        int databaseSizeBeforeUpdate = eventoRepository.findAll().size();

        // Update the evento
        Evento updatedEvento = eventoRepository.findById(evento.getId()).get();
        // Disconnect from session so that the updates on updatedEvento are not directly saved in db
        em.detach(updatedEvento);
        updatedEvento
            .codEvento(UPDATED_COD_EVENTO)
            .flagAutomatico(UPDATED_FLAG_AUTOMATICO)
            .dataDa(UPDATED_DATA_DA)
            .dataA(UPDATED_DATA_A)
            .tipoEvento(UPDATED_TIPO_EVENTO)
            .tipoGenerazioneEvento(UPDATED_TIPO_GENERAZIONE_EVENTO);

        restEventoMockMvc.perform(put("/api/eventos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEvento)))
            .andExpect(status().isOk());

        // Validate the Evento in the database
        List<Evento> eventoList = eventoRepository.findAll();
        assertThat(eventoList).hasSize(databaseSizeBeforeUpdate);
        Evento testEvento = eventoList.get(eventoList.size() - 1);
        assertThat(testEvento.getCodEvento()).isEqualTo(UPDATED_COD_EVENTO);
        assertThat(testEvento.isFlagAutomatico()).isEqualTo(UPDATED_FLAG_AUTOMATICO);
        assertThat(testEvento.getDataDa()).isEqualTo(UPDATED_DATA_DA);
        assertThat(testEvento.getDataA()).isEqualTo(UPDATED_DATA_A);
        assertThat(testEvento.getTipoEvento()).isEqualTo(UPDATED_TIPO_EVENTO);
        assertThat(testEvento.getTipoGenerazioneEvento()).isEqualTo(UPDATED_TIPO_GENERAZIONE_EVENTO);
    }

    @Test
    @Transactional
    public void updateNonExistingEvento() throws Exception {
        int databaseSizeBeforeUpdate = eventoRepository.findAll().size();

        // Create the Evento

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEventoMockMvc.perform(put("/api/eventos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(evento)))
            .andExpect(status().isBadRequest());

        // Validate the Evento in the database
        List<Evento> eventoList = eventoRepository.findAll();
        assertThat(eventoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEvento() throws Exception {
        // Initialize the database
        eventoRepository.saveAndFlush(evento);

        int databaseSizeBeforeDelete = eventoRepository.findAll().size();

        // Get the evento
        restEventoMockMvc.perform(delete("/api/eventos/{id}", evento.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Evento> eventoList = eventoRepository.findAll();
        assertThat(eventoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Evento.class);
        Evento evento1 = new Evento();
        evento1.setId(1L);
        Evento evento2 = new Evento();
        evento2.setId(evento1.getId());
        assertThat(evento1).isEqualTo(evento2);
        evento2.setId(2L);
        assertThat(evento1).isNotEqualTo(evento2);
        evento1.setId(null);
        assertThat(evento1).isNotEqualTo(evento2);
    }
}
