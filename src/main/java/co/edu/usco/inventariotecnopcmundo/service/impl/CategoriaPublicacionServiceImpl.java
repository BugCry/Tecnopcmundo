package co.edu.usco.inventariotecnopcmundo.service.impl;

import co.edu.usco.inventariotecnopcmundo.domain.CategoriaPublicacion;
import co.edu.usco.inventariotecnopcmundo.repository.CategoriaPublicacionRepository;
import co.edu.usco.inventariotecnopcmundo.service.CategoriaPublicacionService;
import co.edu.usco.inventariotecnopcmundo.service.dto.CategoriaPublicacionDTO;
import co.edu.usco.inventariotecnopcmundo.service.mapper.CategoriaPublicacionMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CategoriaPublicacion}.
 */
@Service
@Transactional
public class CategoriaPublicacionServiceImpl implements CategoriaPublicacionService {

    private final Logger log = LoggerFactory.getLogger(CategoriaPublicacionServiceImpl.class);

    private final CategoriaPublicacionRepository categoriaPublicacionRepository;

    private final CategoriaPublicacionMapper categoriaPublicacionMapper;

    public CategoriaPublicacionServiceImpl(
        CategoriaPublicacionRepository categoriaPublicacionRepository,
        CategoriaPublicacionMapper categoriaPublicacionMapper
    ) {
        this.categoriaPublicacionRepository = categoriaPublicacionRepository;
        this.categoriaPublicacionMapper = categoriaPublicacionMapper;
    }

    @Override
    public CategoriaPublicacionDTO save(CategoriaPublicacionDTO categoriaPublicacionDTO) {
        log.debug("Request to save CategoriaPublicacion : {}", categoriaPublicacionDTO);
        CategoriaPublicacion categoriaPublicacion = categoriaPublicacionMapper.toEntity(categoriaPublicacionDTO);
        categoriaPublicacion = categoriaPublicacionRepository.save(categoriaPublicacion);
        return categoriaPublicacionMapper.toDto(categoriaPublicacion);
    }

    @Override
    public Optional<CategoriaPublicacionDTO> partialUpdate(CategoriaPublicacionDTO categoriaPublicacionDTO) {
        log.debug("Request to partially update CategoriaPublicacion : {}", categoriaPublicacionDTO);

        return categoriaPublicacionRepository
            .findById(categoriaPublicacionDTO.getId())
            .map(
                existingCategoriaPublicacion -> {
                    categoriaPublicacionMapper.partialUpdate(existingCategoriaPublicacion, categoriaPublicacionDTO);

                    return existingCategoriaPublicacion;
                }
            )
            .map(categoriaPublicacionRepository::save)
            .map(categoriaPublicacionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CategoriaPublicacionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CategoriaPublicacions");
        return categoriaPublicacionRepository.findAll(pageable).map(categoriaPublicacionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CategoriaPublicacionDTO> findOne(Long id) {
        log.debug("Request to get CategoriaPublicacion : {}", id);
        return categoriaPublicacionRepository.findById(id).map(categoriaPublicacionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CategoriaPublicacion : {}", id);
        categoriaPublicacionRepository.deleteById(id);
    }
}
