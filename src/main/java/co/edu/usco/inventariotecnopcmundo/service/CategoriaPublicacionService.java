package co.edu.usco.inventariotecnopcmundo.service;

import co.edu.usco.inventariotecnopcmundo.service.dto.CategoriaPublicacionDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing
 * {@link co.edu.usco.inventariotecnopcmundo.domain.CategoriaPublicacion}.
 */
public interface CategoriaPublicacionService {
    /**
     * Save a categoriaPublicacion.
     *
     * @param categoriaPublicacionDTO the entity to save.
     * @return the persisted entity.
     */
    CategoriaPublicacionDTO save(CategoriaPublicacionDTO categoriaPublicacionDTO);

    /**
     * Partially updates a categoriaPublicacion.
     *
     * @param categoriaPublicacionDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CategoriaPublicacionDTO> partialUpdate(CategoriaPublicacionDTO categoriaPublicacionDTO);

    /**
     * Get all the categoriaPublicacions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CategoriaPublicacionDTO> findAll(Pageable pageable);

    /**
     * Get the "id" categoriaPublicacion.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CategoriaPublicacionDTO> findOne(Long id);

    /**
     * Delete the "id" categoriaPublicacion.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
