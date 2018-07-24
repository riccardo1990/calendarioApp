package com.trenota.calendario.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import com.trenota.calendario.domain.enumeration.TipoEvento;

import com.trenota.calendario.domain.enumeration.TipoGenerazioneEvento;

/**
 * A Evento.
 */
@Entity
@Table(name = "evento")
public class Evento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "cod_evento", nullable = false)
    private String codEvento;

    @Column(name = "flag_automatico")
    private Boolean flagAutomatico;

    @NotNull
    @Column(name = "data_da", nullable = false)
    private Instant dataDa;

    @NotNull
    @Column(name = "data_a", nullable = false)
    private Instant dataA;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_evento", nullable = false)
    private TipoEvento tipoEvento;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_generazione_evento", nullable = false)
    private TipoGenerazioneEvento tipoGenerazioneEvento;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Prenotazione eventoRelPrenot;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Calendario eventoRelCal;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodEvento() {
        return codEvento;
    }

    public Evento codEvento(String codEvento) {
        this.codEvento = codEvento;
        return this;
    }

    public void setCodEvento(String codEvento) {
        this.codEvento = codEvento;
    }

    public Boolean isFlagAutomatico() {
        return flagAutomatico;
    }

    public Evento flagAutomatico(Boolean flagAutomatico) {
        this.flagAutomatico = flagAutomatico;
        return this;
    }

    public void setFlagAutomatico(Boolean flagAutomatico) {
        this.flagAutomatico = flagAutomatico;
    }

    public Instant getDataDa() {
        return dataDa;
    }

    public Evento dataDa(Instant dataDa) {
        this.dataDa = dataDa;
        return this;
    }

    public void setDataDa(Instant dataDa) {
        this.dataDa = dataDa;
    }

    public Instant getDataA() {
        return dataA;
    }

    public Evento dataA(Instant dataA) {
        this.dataA = dataA;
        return this;
    }

    public void setDataA(Instant dataA) {
        this.dataA = dataA;
    }

    public TipoEvento getTipoEvento() {
        return tipoEvento;
    }

    public Evento tipoEvento(TipoEvento tipoEvento) {
        this.tipoEvento = tipoEvento;
        return this;
    }

    public void setTipoEvento(TipoEvento tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public TipoGenerazioneEvento getTipoGenerazioneEvento() {
        return tipoGenerazioneEvento;
    }

    public Evento tipoGenerazioneEvento(TipoGenerazioneEvento tipoGenerazioneEvento) {
        this.tipoGenerazioneEvento = tipoGenerazioneEvento;
        return this;
    }

    public void setTipoGenerazioneEvento(TipoGenerazioneEvento tipoGenerazioneEvento) {
        this.tipoGenerazioneEvento = tipoGenerazioneEvento;
    }

    public Prenotazione getEventoRelPrenot() {
        return eventoRelPrenot;
    }

    public Evento eventoRelPrenot(Prenotazione prenotazione) {
        this.eventoRelPrenot = prenotazione;
        return this;
    }

    public void setEventoRelPrenot(Prenotazione prenotazione) {
        this.eventoRelPrenot = prenotazione;
    }

    public Calendario getEventoRelCal() {
        return eventoRelCal;
    }

    public Evento eventoRelCal(Calendario calendario) {
        this.eventoRelCal = calendario;
        return this;
    }

    public void setEventoRelCal(Calendario calendario) {
        this.eventoRelCal = calendario;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Evento evento = (Evento) o;
        if (evento.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), evento.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Evento{" +
            "id=" + getId() +
            ", codEvento='" + getCodEvento() + "'" +
            ", flagAutomatico='" + isFlagAutomatico() + "'" +
            ", dataDa='" + getDataDa() + "'" +
            ", dataA='" + getDataA() + "'" +
            ", tipoEvento='" + getTipoEvento() + "'" +
            ", tipoGenerazioneEvento='" + getTipoGenerazioneEvento() + "'" +
            "}";
    }
}
