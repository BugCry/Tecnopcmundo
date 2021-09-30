package co.edu.usco.inventariotecnopcmundo.service;

import co.edu.usco.inventariotecnopcmundo.service.dto.PrecioDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing
 * {@link co.edu.usco.inventariotecnopcmundo.domain.Precio}.
 */
public interface PrecioService {
    /**
     * Save a precio.
     *
     * @param precioDTO the entity to save.
     * @return the persisted entity.
     */
    PrecioDTO save(PrecioDTO precioDTO);

    /**
     * Partially updates a precio.
     *
     * @param precioDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PrecioDTO> partialUpdate(PrecioDTO precioDTO);

    /**
     * Get all the precios.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PrecioDTO> findAll(Pageable pageable);

    /**
     * Get all the PrecioDTO where Producto is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<PrecioDTO> findAllWhereProductoIsNull();

    /**
     * Get the "id" precio.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PrecioDTO> findOne(Long id);

    /**
     * Delete the "id" precio.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
