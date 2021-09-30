package co.edu.usco.inventariotecnopcmundo.repository;

import co.edu.usco.inventariotecnopcmundo.domain.Precio;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Precio entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrecioRepository extends JpaRepository<Precio, Long> {}
