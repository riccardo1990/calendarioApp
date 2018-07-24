package com.trenota.calendario.repository;

import com.trenota.calendario.domain.Prenotazione;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Prenotazione entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {

}
