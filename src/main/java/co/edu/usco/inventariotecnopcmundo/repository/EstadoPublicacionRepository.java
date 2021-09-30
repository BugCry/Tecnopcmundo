package co.edu.usco.inventariotecnopcmundo.repository;

import co.edu.usco.inventariotecnopcmundo.domain.EstadoPublicacion;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the EstadoPublicacion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EstadoPublicacionRepository extends JpaRepository<EstadoPublicacion, Long> {}
