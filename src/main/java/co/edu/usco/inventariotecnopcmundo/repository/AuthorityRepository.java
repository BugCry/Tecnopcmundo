package co.edu.usco.inventariotecnopcmundo.repository;

import co.edu.usco.inventariotecnopcmundo.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {}
