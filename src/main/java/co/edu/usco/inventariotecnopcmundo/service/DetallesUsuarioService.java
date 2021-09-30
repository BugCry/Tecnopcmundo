package co.edu.usco.inventariotecnopcmundo.service;

import co.edu.usco.inventariotecnopcmundo.service.dto.DetallesUsuarioDTO;
import java.util.Optional;
import liquibase.statement.core.FindForeignKeyConstraintsStatement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing
 * {@link co.edu.usco.inventariotecnopcmundo.domain.DetallesUsuario}.
 */
public interface DetallesUsuarioService {
    /**
     * Save a detallesUsuario.
     *
     * @param detallesUsuarioDTO the entity to save.
     * @return the persisted entity.
     */
    DetallesUsuarioDTO save(DetallesUsuarioDTO detallesUsuarioDTO);

    /**
     * Partially updates a detallesUsuario.
     *
     * @param detallesUsuarioDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DetallesUsuarioDTO> partialUpdate(DetallesUsuarioDTO detallesUsuarioDTO);

    /**
     * Get all the detallesUsuarios.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DetallesUsuarioDTO> findAll(Pageable pageable);

    /**
     * Get the "id" detallesUsuario.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DetallesUsuarioDTO> findOne(Long id);

    Optional<DetallesUsuarioDTO> findByUserId(Long id);

    Optional<DetallesUsuarioDTO> findByUserLogin(String login);

    /**
     * Delete the "id" detallesUsuario.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
