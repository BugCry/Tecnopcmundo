package co.edu.usco.inventariotecnopcmundo.service.impl;

import co.edu.usco.inventariotecnopcmundo.domain.Precio;
import co.edu.usco.inventariotecnopcmundo.repository.PrecioRepository;
import co.edu.usco.inventariotecnopcmundo.service.PrecioService;
import co.edu.usco.inventariotecnopcmundo.service.dto.PrecioDTO;
import co.edu.usco.inventariotecnopcmundo.service.mapper.PrecioMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Precio}.
 */
@Service
@Transactional
public class PrecioServiceImpl implements PrecioService {

    private final Logger log = LoggerFactory.getLogger(PrecioServiceImpl.class);

    private final PrecioRepository precioRepository;

    private final PrecioMapper precioMapper;

    public PrecioServiceImpl(PrecioRepository precioRepository, PrecioMapper precioMapper) {
        this.precioRepository = precioRepository;
        this.precioMapper = precioMapper;
    }

    @Override
    public PrecioDTO save(PrecioDTO precioDTO) {
        log.debug("Request to save Precio : {}", precioDTO);
        Precio precio = precioMapper.toEntity(precioDTO);
        precio = precioRepository.save(precio);
        return precioMapper.toDto(precio);
    }

    @Override
    public Optional<PrecioDTO> partialUpdate(PrecioDTO precioDTO) {
        log.debug("Request to partially update Precio : {}", precioDTO);

        return precioRepository
            .findById(precioDTO.getId())
            .map(
                existingPrecio -> {
                    precioMapper.partialUpdate(existingPrecio, precioDTO);

                    return existingPrecio;
                }
            )
            .map(precioRepository::save)
            .map(precioMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PrecioDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Precios");
        return precioRepository.findAll(pageable).map(precioMapper::toDto);
    }

    /**
     * Get all the precios where Producto is {@code null}.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PrecioDTO> findAllWhereProductoIsNull() {
        log.debug("Request to get all precios where Producto is null");
        return StreamSupport
            .stream(precioRepository.findAll().spliterator(), false)
            .filter(precio -> precio.getProducto() == null)
            .map(precioMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PrecioDTO> findOne(Long id) {
        log.debug("Request to get Precio : {}", id);
        return precioRepository.findById(id).map(precioMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Precio : {}", id);
        precioRepository.deleteById(id);
    }
}
