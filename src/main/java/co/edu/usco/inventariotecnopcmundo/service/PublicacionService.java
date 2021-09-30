package co.edu.usco.inventariotecnopcmundo.service;

import co.edu.usco.inventariotecnopcmundo.service.dto.PublicacionDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing
 * {@link co.edu.usco.inventariotecnopcmundo.domain.Publicacion}.
 */
public interface PublicacionService {
    /**
     * Save a publicacion.
     *
     * @param publicacionDTO the entity to save.
     * @return the persisted entity.
     */
    PublicacionDTO save(PublicacionDTO publicacionDTO);

    /**
     * Partially updates a publicacion.
     *
     * @param publicacionDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PublicacionDTO> partialUpdate(PublicacionDTO publicacionDTO);

    /**
     * Get all the publicacions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PublicacionDTO> findAll(Pageable pageable);

    /**
     * Get the "id" publicacion.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PublicacionDTO> findOne(Long id);

    /**
     * Delete the "id" publicacion.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
