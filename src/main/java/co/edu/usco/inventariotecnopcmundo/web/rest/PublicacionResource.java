package co.edu.usco.inventariotecnopcmundo.web.rest;

import co.edu.usco.inventariotecnopcmundo.repository.PublicacionRepository;
import co.edu.usco.inventariotecnopcmundo.service.PublicacionService;
import co.edu.usco.inventariotecnopcmundo.service.dto.PublicacionDTO;
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
 * REST controller for managing {@link co.edu.usco.inventariotecnopcmundo.domain.Publicacion}.
 */
@RestController
@RequestMapping("/api")
public class PublicacionResource {

    private final Logger log = LoggerFactory.getLogger(PublicacionResource.class);

    private static final String ENTITY_NAME = "publicacion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PublicacionService publicacionService;

    private final PublicacionRepository publicacionRepository;

    public PublicacionResource(PublicacionService publicacionService, PublicacionRepository publicacionRepository) {
        this.publicacionService = publicacionService;
        this.publicacionRepository = publicacionRepository;
    }

    /**
     * {@code POST  /publicacions} : Create a new publicacion.
     *
     * @param publicacionDTO the publicacionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new publicacionDTO, or with status {@code 400 (Bad Request)} if the publicacion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/publicacions")
    public ResponseEntity<PublicacionDTO> createPublicacion(@RequestBody PublicacionDTO publicacionDTO) throws URISyntaxException {
        log.debug("REST request to save Publicacion : {}", publicacionDTO);
        if (publicacionDTO.getId() != null) {
            throw new BadRequestAlertException("A new publicacion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PublicacionDTO result = publicacionService.save(publicacionDTO);
        return ResponseEntity
            .created(new URI("/api/publicacions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /publicacions/:id} : Updates an existing publicacion.
     *
     * @param id the id of the publicacionDTO to save.
     * @param publicacionDTO the publicacionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated publicacionDTO,
     * or with status {@code 400 (Bad Request)} if the publicacionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the publicacionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/publicacions/{id}")
    public ResponseEntity<PublicacionDTO> updatePublicacion(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PublicacionDTO publicacionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Publicacion : {}, {}", id, publicacionDTO);
        if (publicacionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, publicacionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!publicacionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PublicacionDTO result = publicacionService.save(publicacionDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, publicacionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /publicacions/:id} : Partial updates given fields of an existing publicacion, field will ignore if it is null
     *
     * @param id the id of the publicacionDTO to save.
     * @param publicacionDTO the publicacionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated publicacionDTO,
     * or with status {@code 400 (Bad Request)} if the publicacionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the publicacionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the publicacionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/publicacions/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<PublicacionDTO> partialUpdatePublicacion(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PublicacionDTO publicacionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Publicacion partially : {}, {}", id, publicacionDTO);
        if (publicacionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, publicacionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!publicacionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PublicacionDTO> result = publicacionService.partialUpdate(publicacionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, publicacionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /publicacions} : get all the publicacions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of publicacions in body.
     */
    @GetMapping("/publicacions")
    public ResponseEntity<List<PublicacionDTO>> getAllPublicacions(Pageable pageable) {
        log.debug("REST request to get a page of Publicacions");
        Page<PublicacionDTO> page = publicacionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /publicacions/:id} : get the "id" publicacion.
     *
     * @param id the id of the publicacionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the publicacionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/publicacions/{id}")
    public ResponseEntity<PublicacionDTO> getPublicacion(@PathVariable Long id) {
        log.debug("REST request to get Publicacion : {}", id);
        Optional<PublicacionDTO> publicacionDTO = publicacionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(publicacionDTO);
    }

    /**
     * {@code DELETE  /publicacions/:id} : delete the "id" publicacion.
     *
     * @param id the id of the publicacionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/publicacions/{id}")
    public ResponseEntity<Void> deletePublicacion(@PathVariable Long id) {
        log.debug("REST request to delete Publicacion : {}", id);
        publicacionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
