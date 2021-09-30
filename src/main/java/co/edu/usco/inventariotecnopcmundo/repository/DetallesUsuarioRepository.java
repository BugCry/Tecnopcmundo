package co.edu.usco.inventariotecnopcmundo.repository;

import co.edu.usco.inventariotecnopcmundo.domain.DetallesUsuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DetallesUsuario entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DetallesUsuarioRepository extends JpaRepository<DetallesUsuario, Long> {
    public Optional<DetallesUsuario> findByUserId(Long id);

    public Optional<DetallesUsuario> findByUserLogin(String login);
}
