package co.edu.usco.inventariotecnopcmundo.web.rest;

import co.edu.usco.inventariotecnopcmundo.repository.EstadoPublicacionRepository;
import co.edu.usco.inventariotecnopcmundo.service.EstadoPublicacionService;
import co.edu.usco.inventariotecnopcmundo.service.dto.EstadoPublicacionDTO;
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
 * REST controller for managing {@link co.edu.usco.inventariotecnopcmundo.domain.EstadoPublicacion}.
 */
@RestController
@RequestMapping("/api")
public class EstadoPublicacionResource {

    private final Logger log = LoggerFactory.getLogger(EstadoPublicacionResource.class);

    private static final String ENTITY_NAME = "estadoPublicacion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EstadoPublicacionService estadoPublicacionService;

    private final EstadoPublicacionRepository estadoPublicacionRepository;

    public EstadoPublicacionResource(
        EstadoPublicacionService estadoPublicacionService,
        EstadoPublicacionRepository estadoPublicacionRepository
    ) {
        this.estadoPublicacionService = estadoPublicacionService;
        this.estadoPublicacionRepository = estadoPublicacionRepository;
    }

    /**
     * {@code POST  /estado-publicacions} : Create a new estadoPublicacion.
     *
     * @param estadoPublicacionDTO the estadoPublicacionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new estadoPublicacionDTO, or with status {@code 400 (Bad Request)} if the estadoPublicacion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/estado-publicacions")
    public ResponseEntity<EstadoPublicacionDTO> createEstadoPublicacion(@RequestBody EstadoPublicacionDTO estadoPublicacionDTO)
        throws URISyntaxException {
        log.debug("REST request to save EstadoPublicacion : {}", estadoPublicacionDTO);
        if (estadoPublicacionDTO.getId() != null) {
            throw new BadRequestAlertException("A new estadoPublicacion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EstadoPublicacionDTO result = estadoPublicacionService.save(estadoPublicacionDTO);
        return ResponseEntity
            .created(new URI("/api/estado-publicacions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /estado-publicacions/:id} : Updates an existing estadoPublicacion.
     *
     * @param id the id of the estadoPublicacionDTO to save.
     * @param estadoPublicacionDTO the estadoPublicacionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated estadoPublicacionDTO,
     * or with status {@code 400 (Bad Request)} if the estadoPublicacionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the estadoPublicacionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/estado-publicacions/{id}")
    public ResponseEntity<EstadoPublicacionDTO> updateEstadoPublicacion(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EstadoPublicacionDTO estadoPublicacionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update EstadoPublicacion : {}, {}", id, estadoPublicacionDTO);
        if (estadoPublicacionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, estadoPublicacionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!estadoPublicacionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EstadoPublicacionDTO result = estadoPublicacionService.save(estadoPublicacionDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, estadoPublicacionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /estado-publicacions/:id} : Partial updates given fields of an existing estadoPublicacion, field will ignore if it is null
     *
     * @param id the id of the estadoPublicacionDTO to save.
     * @param estadoPublicacionDTO the estadoPublicacionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated estadoPublicacionDTO,
     * or with status {@code 400 (Bad Request)} if the estadoPublicacionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the estadoPublicacionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the estadoPublicacionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/estado-publicacions/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<EstadoPublicacionDTO> partialUpdateEstadoPublicacion(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EstadoPublicacionDTO estadoPublicacionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update EstadoPublicacion partially : {}, {}", id, estadoPublicacionDTO);
        if (estadoPublicacionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, estadoPublicacionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!estadoPublicacionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EstadoPublicacionDTO> result = estadoPublicacionService.partialUpdate(estadoPublicacionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, estadoPublicacionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /estado-publicacions} : get all the estadoPublicacions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of estadoPublicacions in body.
     */
    @GetMapping("/estado-publicacions")
    public ResponseEntity<List<EstadoPublicacionDTO>> getAllEstadoPublicacions(Pageable pageable) {
        log.debug("REST request to get a page of EstadoPublicacions");
        Page<EstadoPublicacionDTO> page = estadoPublicacionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /estado-publicacions/:id} : get the "id" estadoPublicacion.
     *
     * @param id the id of the estadoPublicacionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the estadoPublicacionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/estado-publicacions/{id}")
    public ResponseEntity<EstadoPublicacionDTO> getEstadoPublicacion(@PathVariable Long id) {
        log.debug("REST request to get EstadoPublicacion : {}", id);
        Optional<EstadoPublicacionDTO> estadoPublicacionDTO = estadoPublicacionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(estadoPublicacionDTO);
    }

    /**
     * {@code DELETE  /estado-publicacions/:id} : delete the "id" estadoPublicacion.
     *
     * @param id the id of the estadoPublicacionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/estado-publicacions/{id}")
    public ResponseEntity<Void> deleteEstadoPublicacion(@PathVariable Long id) {
        log.debug("REST request to delete EstadoPublicacion : {}", id);
        estadoPublicacionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
