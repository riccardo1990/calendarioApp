package com.trenota.calendario.repository;

import com.trenota.calendario.domain.Calendario;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Calendario entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CalendarioRepository extends JpaRepository<Calendario, Long> {

}
