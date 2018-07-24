package com.trenota.calendario.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Calendario.
 */
@Entity
@Table(name = "calendario")
public class Calendario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "cod_calendario", nullable = false)
    private String codCalendario;

    @NotNull
    @Column(name = "desc_calendario", nullable = false)
    private String descCalendario;

    @NotNull
    @Column(name = "anno", nullable = false)
    private Integer anno;

    @Column(name = "data_calendario")
    private LocalDate dataCalendario;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodCalendario() {
        return codCalendario;
    }

    public Calendario codCalendario(String codCalendario) {
        this.codCalendario = codCalendario;
        return this;
    }

    public void setCodCalendario(String codCalendario) {
        this.codCalendario = codCalendario;
    }

    public String getDescCalendario() {
        return descCalendario;
    }

    public Calendario descCalendario(String descCalendario) {
        this.descCalendario = descCalendario;
        return this;
    }

    public void setDescCalendario(String descCalendario) {
        this.descCalendario = descCalendario;
    }

    public Integer getAnno() {
        return anno;
    }

    public Calendario anno(Integer anno) {
        this.anno = anno;
        return this;
    }

    public void setAnno(Integer anno) {
        this.anno = anno;
    }

    public LocalDate getDataCalendario() {
        return dataCalendario;
    }

    public Calendario dataCalendario(LocalDate dataCalendario) {
        this.dataCalendario = dataCalendario;
        return this;
    }

    public void setDataCalendario(LocalDate dataCalendario) {
        this.dataCalendario = dataCalendario;
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
        Calendario calendario = (Calendario) o;
        if (calendario.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), calendario.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Calendario{" +
            "id=" + getId() +
            ", codCalendario='" + getCodCalendario() + "'" +
            ", descCalendario='" + getDescCalendario() + "'" +
            ", anno=" + getAnno() +
            ", dataCalendario='" + getDataCalendario() + "'" +
            "}";
    }
}
