package co.edu.usco.inventariotecnopcmundo.web.rest;

import co.edu.usco.inventariotecnopcmundo.repository.CategoriaPublicacionRepository;
import co.edu.usco.inventariotecnopcmundo.service.CategoriaPublicacionService;
import co.edu.usco.inventariotecnopcmundo.service.dto.CategoriaPublicacionDTO;
import co.edu.usco.inventariotecnopcmundo.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link co.edu.usco.inventariotecnopcmundo.domain.CategoriaPublicacion}.
 */
@RestController
@RequestMapping("/api")
public class CategoriaPublicacionResource {

    private final Logger log = LoggerFactory.getLogger(CategoriaPublicacionResource.class);

    private static final String ENTITY_NAME = "categoriaPublicacion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CategoriaPublicacionService categoriaPublicacionService;

    private final CategoriaPublicacionRepository categoriaPublicacionRepository;

    public CategoriaPublicacionResource(
        CategoriaPublicacionService categoriaPublicacionService,
        CategoriaPublicacionRepository categoriaPublicacionRepository
    ) {
        this.categoriaPublicacionService = categoriaPublicacionService;
        this.categoriaPublicacionRepository = categoriaPublicacionRepository;
    }

    /**
     * {@code POST  /categoria-publicacions} : Create a new categoriaPublicacion.
     *
     * @param categoriaPublicacionDTO the categoriaPublicacionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new categoriaPublicacionDTO, or with status {@code 400 (Bad Request)} if the categoriaPublicacion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/categoria-publicacions")
    public ResponseEntity<CategoriaPublicacionDTO> createCategoriaPublicacion(@RequestBody CategoriaPublicacionDTO categoriaPublicacionDTO)
        throws URISyntaxException {
        log.debug("REST request to save CategoriaPublicacion : {}", categoriaPublicacionDTO);
        if (categoriaPublicacionDTO.getId() != null) {
            throw new BadRequestAlertException("A new categoriaPublicacion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CategoriaPublicacionDTO result = categoriaPublicacionService.save(categoriaPublicacionDTO);
        return ResponseEntity
            .created(new URI("/api/categoria-publicacions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /categoria-publicacions/:id} : Updates an existing categoriaPublicacion.
     *
     * @param id the id of the categoriaPublicacionDTO to save.
     * @param categoriaPublicacionDTO the categoriaPublicacionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categoriaPublicacionDTO,
     * or with status {@code 400 (Bad Request)} if the categoriaPublicacionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the categoriaPublicacionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/categoria-publicacions/{id}")
    public ResponseEntity<CategoriaPublicacionDTO> updateCategoriaPublicacion(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CategoriaPublicacionDTO categoriaPublicacionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CategoriaPublicacion : {}, {}", id, categoriaPublicacionDTO);
        if (categoriaPublicacionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, categoriaPublicacionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!categoriaPublicacionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CategoriaPublicacionDTO result = categoriaPublicacionService.save(categoriaPublicacionDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, categoriaPublicacionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /categoria-publicacions/:id} : Partial updates given fields of an existing categoriaPublicacion, field will ignore if it is null
     *
     * @param id the id of the categoriaPublicacionDTO to save.
     * @param categoriaPublicacionDTO the categoriaPublicacionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categoriaPublicacionDTO,
     * or with status {@code 400 (Bad Request)} if the categoriaPublicacionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the categoriaPublicacionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the categoriaPublicacionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/categoria-publicacions/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CategoriaPublicacionDTO> partialUpdateCategoriaPublicacion(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CategoriaPublicacionDTO categoriaPublicacionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CategoriaPublicacion partially : {}, {}", id, categoriaPublicacionDTO);
        if (categoriaPublicacionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, categoriaPublicacionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!categoriaPublicacionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CategoriaPublicacionDTO> result = categoriaPublicacionService.partialUpdate(categoriaPublicacionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, categoriaPublicacionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /categoria-publicacions} : get all the categoriaPublicacions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of categoriaPublicacions in body.
     */
    @GetMapping("/categoria-publicacions")
    public ResponseEntity<List<CategoriaPublicacionDTO>> getAllCategoriaPublicacions(Pageable pageable) {
        log.debug("REST request to get a page of CategoriaPublicacions");
        Page<CategoriaPublicacionDTO> page = categoriaPublicacionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /categoria-publicacions/:id} : get the "id" categoriaPublicacion.
     *
     * @param id the id of the categoriaPublicacionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the categoriaPublicacionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/categoria-publicacions/{id}")
    public ResponseEntity<CategoriaPublicacionDTO> getCategoriaPublicacion(@PathVariable Long id) {
        log.debug("REST request to get CategoriaPublicacion : {}", id);
        Optional<CategoriaPublicacionDTO> categoriaPublicacionDTO = categoriaPublicacionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(categoriaPublicacionDTO);
    }

    /**
     * {@code DELETE  /categoria-publicacions/:id} : delete the "id" categoriaPublicacion.
     *
     * @param id the id of the categoriaPublicacionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/categoria-publicacions/{id}")
    public ResponseEntity<Void> deleteCategoriaPublicacion(@PathVariable Long id) {
        log.debug("REST request to delete CategoriaPublicacion : {}", id);
        categoriaPublicacionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
