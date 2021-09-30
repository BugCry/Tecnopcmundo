package co.edu.usco.inventariotecnopcmundo.service.impl;

import co.edu.usco.inventariotecnopcmundo.domain.DetallesUsuario;
import co.edu.usco.inventariotecnopcmundo.repository.DetallesUsuarioRepository;
import co.edu.usco.inventariotecnopcmundo.service.DetallesUsuarioService;
import co.edu.usco.inventariotecnopcmundo.service.dto.DetallesUsuarioDTO;
import co.edu.usco.inventariotecnopcmundo.service.mapper.DetallesUsuarioMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DetallesUsuario}.
 */
@Service
@Transactional
public class DetallesUsuarioServiceImpl implements DetallesUsuarioService {

    private final Logger log = LoggerFactory.getLogger(DetallesUsuarioServiceImpl.class);

    private final DetallesUsuarioRepository detallesUsuarioRepository;

    private final DetallesUsuarioMapper detallesUsuarioMapper;

    public DetallesUsuarioServiceImpl(DetallesUsuarioRepository detallesUsuarioRepository, DetallesUsuarioMapper detallesUsuarioMapper) {
        this.detallesUsuarioRepository = detallesUsuarioRepository;
        this.detallesUsuarioMapper = detallesUsuarioMapper;
    }

    @Override
    public DetallesUsuarioDTO save(DetallesUsuarioDTO detallesUsuarioDTO) {
        log.debug("Request to save DetallesUsuario : {}", detallesUsuarioDTO);
        DetallesUsuario detallesUsuario = detallesUsuarioMapper.toEntity(detallesUsuarioDTO);
        detallesUsuario = detallesUsuarioRepository.save(detallesUsuario);
        return detallesUsuarioMapper.toDto(detallesUsuario);
    }

    @Override
    public Optional<DetallesUsuarioDTO> partialUpdate(DetallesUsuarioDTO detallesUsuarioDTO) {
        log.debug("Request to partially update DetallesUsuario : {}", detallesUsuarioDTO);

        return detallesUsuarioRepository
            .findById(detallesUsuarioDTO.getId())
            .map(
                existingDetallesUsuario -> {
                    detallesUsuarioMapper.partialUpdate(existingDetallesUsuario, detallesUsuarioDTO);

                    return existingDetallesUsuario;
                }
            )
            .map(detallesUsuarioRepository::save)
            .map(detallesUsuarioMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DetallesUsuarioDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DetallesUsuarios");
        return detallesUsuarioRepository.findAll(pageable).map(detallesUsuarioMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DetallesUsuarioDTO> findOne(Long id) {
        log.debug("Request to get DetallesUsuario : {}", id);
        return detallesUsuarioRepository.findById(id).map(detallesUsuarioMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DetallesUsuarioDTO> findByUserId(Long id) {
        return detallesUsuarioRepository.findByUserId(id).map(detallesUsuarioMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DetallesUsuarioDTO> findByUserLogin(String login) {
        return detallesUsuarioRepository.findByUserLogin(login).map(detallesUsuarioMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DetallesUsuario : {}", id);
        detallesUsuarioRepository.deleteById(id);
    }
}
