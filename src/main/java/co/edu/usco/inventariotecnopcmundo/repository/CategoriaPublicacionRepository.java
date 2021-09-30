package co.edu.usco.inventariotecnopcmundo.repository;

import co.edu.usco.inventariotecnopcmundo.domain.CategoriaPublicacion;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CategoriaPublicacion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategoriaPublicacionRepository extends JpaRepository<CategoriaPublicacion, Long> {}
