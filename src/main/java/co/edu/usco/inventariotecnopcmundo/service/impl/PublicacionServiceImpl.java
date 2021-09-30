package co.edu.usco.inventariotecnopcmundo.service.impl;

import co.edu.usco.inventariotecnopcmundo.domain.Publicacion;
import co.edu.usco.inventariotecnopcmundo.repository.PublicacionRepository;
import co.edu.usco.inventariotecnopcmundo.service.PublicacionService;
import co.edu.usco.inventariotecnopcmundo.service.dto.PublicacionDTO;
import co.edu.usco.inventariotecnopcmundo.service.mapper.PublicacionMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Publicacion}.
 */
@Service
@Transactional
public class PublicacionServiceImpl implements PublicacionService {

    private final Logger log = LoggerFactory.getLogger(PublicacionServiceImpl.class);

    private final PublicacionRepository publicacionRepository;

    private final PublicacionMapper publicacionMapper;

    public PublicacionServiceImpl(PublicacionRepository publicacionRepository, PublicacionMapper publicacionMapper) {
        this.publicacionRepository = publicacionRepository;
        this.publicacionMapper = publicacionMapper;
    }

    @Override
    public PublicacionDTO save(PublicacionDTO publicacionDTO) {
        log.debug("Request to save Publicacion : {}", publicacionDTO);
        Publicacion publicacion = publicacionMapper.toEntity(publicacionDTO);
        publicacion = publicacionRepository.save(publicacion);
        return publicacionMapper.toDto(publicacion);
    }

    @Override
    public Optional<PublicacionDTO> partialUpdate(PublicacionDTO publicacionDTO) {
        log.debug("Request to partially update Publicacion : {}", publicacionDTO);

        return publicacionRepository
            .findById(publicacionDTO.getId())
            .map(
                existingPublicacion -> {
                    publicacionMapper.partialUpdate(existingPublicacion, publicacionDTO);

                    return existingPublicacion;
                }
            )
            .map(publicacionRepository::save)
            .map(publicacionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PublicacionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Publicacions");
        return publicacionRepository.findAll(pageable).map(publicacionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PublicacionDTO> findOne(Long id) {
        log.debug("Request to get Publicacion : {}", id);
        return publicacionRepository.findById(id).map(publicacionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Publicacion : {}", id);
        publicacionRepository.deleteById(id);
    }
}
