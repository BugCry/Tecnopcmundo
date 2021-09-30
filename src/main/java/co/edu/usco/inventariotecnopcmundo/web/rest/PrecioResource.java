package co.edu.usco.inventariotecnopcmundo.web.rest;

import co.edu.usco.inventariotecnopcmundo.repository.PrecioRepository;
import co.edu.usco.inventariotecnopcmundo.service.PrecioService;
import co.edu.usco.inventariotecnopcmundo.service.dto.PrecioDTO;
import co.edu.usco.inventariotecnopcmundo.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
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
 * REST controller for managing {@link co.edu.usco.inventariotecnopcmundo.domain.Precio}.
 */
@RestController
@RequestMapping("/api")
public class PrecioResource {

    private final Logger log = LoggerFactory.getLogger(PrecioResource.class);

    private static final String ENTITY_NAME = "precio";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PrecioService precioService;

    private final PrecioRepository precioRepository;

    public PrecioResource(PrecioService precioService, PrecioRepository precioRepository) {
        this.precioService = precioService;
        this.precioRepository = precioRepository;
    }

    /**
     * {@code POST  /precios} : Create a new precio.
     *
     * @param precioDTO the precioDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new precioDTO, or with status {@code 400 (Bad Request)} if the precio has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/precios")
    public ResponseEntity<PrecioDTO> createPrecio(@RequestBody PrecioDTO precioDTO) throws URISyntaxException {
        log.debug("REST request to save Precio : {}", precioDTO);
        if (precioDTO.getId() != null) {
            throw new BadRequestAlertException("A new precio cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PrecioDTO result = precioService.save(precioDTO);
        return ResponseEntity
            .created(new URI("/api/precios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /precios/:id} : Updates an existing precio.
     *
     * @param id the id of the precioDTO to save.
     * @param precioDTO the precioDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated precioDTO,
     * or with status {@code 400 (Bad Request)} if the precioDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the precioDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/precios/{id}")
    public ResponseEntity<PrecioDTO> updatePrecio(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PrecioDTO precioDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Precio : {}, {}", id, precioDTO);
        if (precioDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, precioDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!precioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PrecioDTO result = precioService.save(precioDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, precioDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /precios/:id} : Partial updates given fields of an existing precio, field will ignore if it is null
     *
     * @param id the id of the precioDTO to save.
     * @param precioDTO the precioDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated precioDTO,
     * or with status {@code 400 (Bad Request)} if the precioDTO is not valid,
     * or with status {@code 404 (Not Found)} if the precioDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the precioDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/precios/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<PrecioDTO> partialUpdatePrecio(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PrecioDTO precioDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Precio partially : {}, {}", id, precioDTO);
        if (precioDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, precioDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!precioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PrecioDTO> result = precioService.partialUpdate(precioDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, precioDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /precios} : get all the precios.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of precios in body.
     */
    @GetMapping("/precios")
    public ResponseEntity<List<PrecioDTO>> getAllPrecios(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("producto-is-null".equals(filter)) {
            log.debug("REST request to get all Precios where producto is null");
            return new ResponseEntity<>(precioService.findAllWhereProductoIsNull(), HttpStatus.OK);
        }
        log.debug("REST request to get a page of Precios");
        Page<PrecioDTO> page = precioService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /precios/:id} : get the "id" precio.
     *
     * @param id the id of the precioDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the precioDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/precios/{id}")
    public ResponseEntity<PrecioDTO> getPrecio(@PathVariable Long id) {
        log.debug("REST request to get Precio : {}", id);
        Optional<PrecioDTO> precioDTO = precioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(precioDTO);
    }

    /**
     * {@code DELETE  /precios/:id} : delete the "id" precio.
     *
     * @param id the id of the precioDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/precios/{id}")
    public ResponseEntity<Void> deletePrecio(@PathVariable Long id) {
        log.debug("REST request to delete Precio : {}", id);
        precioService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
