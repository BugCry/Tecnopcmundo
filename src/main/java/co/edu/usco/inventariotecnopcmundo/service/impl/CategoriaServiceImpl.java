package co.edu.usco.inventariotecnopcmundo.service.impl;

import co.edu.usco.inventariotecnopcmundo.domain.Categoria;
import co.edu.usco.inventariotecnopcmundo.repository.CategoriaRepository;
import co.edu.usco.inventariotecnopcmundo.service.CategoriaService;
import co.edu.usco.inventariotecnopcmundo.service.dto.CategoriaDTO;
import co.edu.usco.inventariotecnopcmundo.service.mapper.CategoriaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Categoria}.
 */
@Service
@Transactional
public class CategoriaServiceImpl implements CategoriaService {

    private final Logger log = LoggerFactory.getLogger(CategoriaServiceImpl.class);

    private final CategoriaRepository categoriaRepository;

    private final CategoriaMapper categoriaMapper;

    public CategoriaServiceImpl(CategoriaRepository categoriaRepository, CategoriaMapper categoriaMapper) {
        this.categoriaRepository = categoriaRepository;
        this.categoriaMapper = categoriaMapper;
    }

    @Override
    public CategoriaDTO save(CategoriaDTO categoriaDTO) {
        log.debug("Request to save Categoria : {}", categoriaDTO);
        Categoria categoria = categoriaMapper.toEntity(categoriaDTO);
        categoria = categoriaRepository.save(categoria);
        return categoriaMapper.toDto(categoria);
    }

    @Override
    public Optional<CategoriaDTO> partialUpdate(CategoriaDTO categoriaDTO) {
        log.debug("Request to partially update Categoria : {}", categoriaDTO);

        return categoriaRepository
            .findById(categoriaDTO.getId())
            .map(
                existingCategoria -> {
                    categoriaMapper.partialUpdate(existingCategoria, categoriaDTO);

                    return existingCategoria;
                }
            )
            .map(categoriaRepository::save)
            .map(categoriaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CategoriaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Categorias");
        return categoriaRepository.findAll(pageable).map(categoriaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CategoriaDTO> findOne(Long id) {
        log.debug("Request to get Categoria : {}", id);
        return categoriaRepository.findById(id).map(categoriaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Categoria : {}", id);
        categoriaRepository.deleteById(id);
    }
}
