package co.edu.usco.inventariotecnopcmundo.service;

import co.edu.usco.inventariotecnopcmundo.service.dto.CategoriaDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing
 * {@link co.edu.usco.inventariotecnopcmundo.domain.Categoria}.
 */
public interface CategoriaService {
    /**
     * Save a categoria.
     *
     * @param categoriaDTO the entity to save.
     * @return the persisted entity.
     */
    CategoriaDTO save(CategoriaDTO categoriaDTO);

    /**
     * Partially updates a categoria.
     *
     * @param categoriaDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CategoriaDTO> partialUpdate(CategoriaDTO categoriaDTO);

    /**
     * Get all the categorias.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CategoriaDTO> findAll(Pageable pageable);

    /**
     * Get the "id" categoria.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CategoriaDTO> findOne(Long id);

    /**
     * Delete the "id" categoria.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
