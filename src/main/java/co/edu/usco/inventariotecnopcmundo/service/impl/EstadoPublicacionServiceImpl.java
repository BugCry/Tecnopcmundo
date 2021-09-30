package co.edu.usco.inventariotecnopcmundo.service.impl;

import co.edu.usco.inventariotecnopcmundo.domain.EstadoPublicacion;
import co.edu.usco.inventariotecnopcmundo.repository.EstadoPublicacionRepository;
import co.edu.usco.inventariotecnopcmundo.service.EstadoPublicacionService;
import co.edu.usco.inventariotecnopcmundo.service.dto.EstadoPublicacionDTO;
import co.edu.usco.inventariotecnopcmundo.service.mapper.EstadoPublicacionMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EstadoPublicacion}.
 */
@Service
@Transactional
public class EstadoPublicacionServiceImpl implements EstadoPublicacionService {

    private final Logger log = LoggerFactory.getLogger(EstadoPublicacionServiceImpl.class);

    private final EstadoPublicacionRepository estadoPublicacionRepository;

    private final EstadoPublicacionMapper estadoPublicacionMapper;

    public EstadoPublicacionServiceImpl(
        EstadoPublicacionRepository estadoPublicacionRepository,
        EstadoPublicacionMapper estadoPublicacionMapper
    ) {
        this.estadoPublicacionRepository = estadoPublicacionRepository;
        this.estadoPublicacionMapper = estadoPublicacionMapper;
    }

    @Override
    public EstadoPublicacionDTO save(EstadoPublicacionDTO estadoPublicacionDTO) {
        log.debug("Request to save EstadoPublicacion : {}", estadoPublicacionDTO);
        EstadoPublicacion estadoPublicacion = estadoPublicacionMapper.toEntity(estadoPublicacionDTO);
        estadoPublicacion = estadoPublicacionRepository.save(estadoPublicacion);
        return estadoPublicacionMapper.toDto(estadoPublicacion);
    }

    @Override
    public Optional<EstadoPublicacionDTO> partialUpdate(EstadoPublicacionDTO estadoPublicacionDTO) {
        log.debug("Request to partially update EstadoPublicacion : {}", estadoPublicacionDTO);

        return estadoPublicacionRepository
            .findById(estadoPublicacionDTO.getId())
            .map(
                existingEstadoPublicacion -> {
                    estadoPublicacionMapper.partialUpdate(existingEstadoPublicacion, estadoPublicacionDTO);

                    return existingEstadoPublicacion;
                }
            )
            .map(estadoPublicacionRepository::save)
            .map(estadoPublicacionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EstadoPublicacionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EstadoPublicacions");
        return estadoPublicacionRepository.findAll(pageable).map(estadoPublicacionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EstadoPublicacionDTO> findOne(Long id) {
        log.debug("Request to get EstadoPublicacion : {}", id);
        return estadoPublicacionRepository.findById(id).map(estadoPublicacionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete EstadoPublicacion : {}", id);
        estadoPublicacionRepository.deleteById(id);
    }
}
