package co.edu.usco.inventariotecnopcmundo.service;

import co.edu.usco.inventariotecnopcmundo.service.dto.EstadoPublicacionDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing
 * {@link co.edu.usco.inventariotecnopcmundo.domain.EstadoPublicacion}.
 */
public interface EstadoPublicacionService {
    /**
     * Save a estadoPublicacion.
     *
     * @param estadoPublicacionDTO the entity to save.
     * @return the persisted entity.
     */
    EstadoPublicacionDTO save(EstadoPublicacionDTO estadoPublicacionDTO);

    /**
     * Partially updates a estadoPublicacion.
     *
     * @param estadoPublicacionDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EstadoPublicacionDTO> partialUpdate(EstadoPublicacionDTO estadoPublicacionDTO);

    /**
     * Get all the estadoPublicacions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EstadoPublicacionDTO> findAll(Pageable pageable);

    /**
     * Get the "id" estadoPublicacion.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EstadoPublicacionDTO> findOne(Long id);

    /**
     * Delete the "id" estadoPublicacion.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
