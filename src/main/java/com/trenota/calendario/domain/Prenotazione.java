package com.trenota.calendario.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Prenotazione.
 */
@Entity
@Table(name = "prenotazione")
public class Prenotazione implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "cod_prenotazione", nullable = false)
    private String codPrenotazione;

    @NotNull
    @Column(name = "data_prenotazione", nullable = false)
    private LocalDate dataPrenotazione;

    @NotNull
    @Column(name = "recapito", nullable = false)
    private String recapito;

    @Column(name = "note")
    private String note;

    @Column(name = "codice_fiscale_ext")
    private String codiceFiscaleExt;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodPrenotazione() {
        return codPrenotazione;
    }

    public Prenotazione codPrenotazione(String codPrenotazione) {
        this.codPrenotazione = codPrenotazione;
        return this;
    }

    public void setCodPrenotazione(String codPrenotazione) {
        this.codPrenotazione = codPrenotazione;
    }

    public LocalDate getDataPrenotazione() {
        return dataPrenotazione;
    }

    public Prenotazione dataPrenotazione(LocalDate dataPrenotazione) {
        this.dataPrenotazione = dataPrenotazione;
        return this;
    }

    public void setDataPrenotazione(LocalDate dataPrenotazione) {
        this.dataPrenotazione = dataPrenotazione;
    }

    public String getRecapito() {
        return recapito;
    }

    public Prenotazione recapito(String recapito) {
        this.recapito = recapito;
        return this;
    }

    public void setRecapito(String recapito) {
        this.recapito = recapito;
    }

    public String getNote() {
        return note;
    }

    public Prenotazione note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCodiceFiscaleExt() {
        return codiceFiscaleExt;
    }

    public Prenotazione codiceFiscaleExt(String codiceFiscaleExt) {
        this.codiceFiscaleExt = codiceFiscaleExt;
        return this;
    }

    public void setCodiceFiscaleExt(String codiceFiscaleExt) {
        this.codiceFiscaleExt = codiceFiscaleExt;
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
        Prenotazione prenotazione = (Prenotazione) o;
        if (prenotazione.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), prenotazione.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Prenotazione{" +
            "id=" + getId() +
            ", codPrenotazione='" + getCodPrenotazione() + "'" +
            ", dataPrenotazione='" + getDataPrenotazione() + "'" +
            ", recapito='" + getRecapito() + "'" +
            ", note='" + getNote() + "'" +
            ", codiceFiscaleExt='" + getCodiceFiscaleExt() + "'" +
            "}";
    }
}
