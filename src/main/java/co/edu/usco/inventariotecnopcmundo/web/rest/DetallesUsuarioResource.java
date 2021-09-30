package co.edu.usco.inventariotecnopcmundo.web.rest;

import co.edu.usco.inventariotecnopcmundo.repository.DetallesUsuarioRepository;
import co.edu.usco.inventariotecnopcmundo.service.DetallesUsuarioService;
import co.edu.usco.inventariotecnopcmundo.service.dto.DetallesUsuarioDTO;
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
 * REST controller for managing
 * {@link co.edu.usco.inventariotecnopcmundo.domain.DetallesUsuario}.
 */
@RestController
@RequestMapping("/api")
public class DetallesUsuarioResource {

    private final Logger log = LoggerFactory.getLogger(DetallesUsuarioResource.class);

    private static final String ENTITY_NAME = "detallesUsuario";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DetallesUsuarioService detallesUsuarioService;

    private final DetallesUsuarioRepository detallesUsuarioRepository;

    public DetallesUsuarioResource(DetallesUsuarioService detallesUsuarioService, DetallesUsuarioRepository detallesUsuarioRepository) {
        this.detallesUsuarioService = detallesUsuarioService;
        this.detallesUsuarioRepository = detallesUsuarioRepository;
    }

    /**
     * {@code POST  /detalles-usuarios} : Create a new detallesUsuario.
     *
     * @param detallesUsuarioDTO the detallesUsuarioDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     *         body the new detallesUsuarioDTO, or with status
     *         {@code 400 (Bad Request)} if the detallesUsuario has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/detalles-usuarios")
    public ResponseEntity<DetallesUsuarioDTO> createDetallesUsuario(@RequestBody DetallesUsuarioDTO detallesUsuarioDTO)
        throws URISyntaxException {
        log.debug("REST request to save DetallesUsuario : {}", detallesUsuarioDTO);
        if (detallesUsuarioDTO.getId() != null) {
            throw new BadRequestAlertException("A new detallesUsuario cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DetallesUsuarioDTO result = detallesUsuarioService.save(detallesUsuarioDTO);
        return ResponseEntity
            .created(new URI("/api/detalles-usuarios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /detalles-usuarios/:id} : Updates an existing detallesUsuario.
     *
     * @param id                 the id of the detallesUsuarioDTO to save.
     * @param detallesUsuarioDTO the detallesUsuarioDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated detallesUsuarioDTO, or with status
     *         {@code 400 (Bad Request)} if the detallesUsuarioDTO is not valid, or
     *         with status {@code 500 (Internal Server Error)} if the
     *         detallesUsuarioDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/detalles-usuarios/{id}")
    public ResponseEntity<DetallesUsuarioDTO> updateDetallesUsuario(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DetallesUsuarioDTO detallesUsuarioDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DetallesUsuario : {}, {}", id, detallesUsuarioDTO);
        if (detallesUsuarioDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, detallesUsuarioDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!detallesUsuarioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DetallesUsuarioDTO result = detallesUsuarioService.save(detallesUsuarioDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, detallesUsuarioDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /detalles-usuarios/:id} : Partial updates given fields of an
     * existing detallesUsuario, field will ignore if it is null
     *
     * @param id                 the id of the detallesUsuarioDTO to save.
     * @param detallesUsuarioDTO the detallesUsuarioDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated detallesUsuarioDTO, or with status
     *         {@code 400 (Bad Request)} if the detallesUsuarioDTO is not valid, or
     *         with status {@code 404 (Not Found)} if the detallesUsuarioDTO is not
     *         found, or with status {@code 500 (Internal Server Error)} if the
     *         detallesUsuarioDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/detalles-usuarios/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<DetallesUsuarioDTO> partialUpdateDetallesUsuario(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DetallesUsuarioDTO detallesUsuarioDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DetallesUsuario partially : {}, {}", id, detallesUsuarioDTO);
        if (detallesUsuarioDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, detallesUsuarioDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!detallesUsuarioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DetallesUsuarioDTO> result = detallesUsuarioService.partialUpdate(detallesUsuarioDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, detallesUsuarioDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /detalles-usuarios} : get all the detallesUsuarios.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     *         of detallesUsuarios in body.
     */
    @GetMapping("/detalles-usuarios")
    public ResponseEntity<List<DetallesUsuarioDTO>> getAllDetallesUsuarios(Pageable pageable) {
        log.debug("REST request to get a page of DetallesUsuarios");
        Page<DetallesUsuarioDTO> page = detallesUsuarioService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /detalles-usuarios/:id} : get the "id" detallesUsuario.
     *
     * @param id the id of the detallesUsuarioDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the detallesUsuarioDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/detalles-usuarios/{id}")
    public ResponseEntity<DetallesUsuarioDTO> getDetallesUsuario(@PathVariable Long id) {
        log.debug("REST request to get DetallesUsuario : {}", id);
        Optional<DetallesUsuarioDTO> detallesUsuarioDTO = detallesUsuarioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(detallesUsuarioDTO);
    }

    @GetMapping("/detalles-usuarios/userid/{id}")
    public ResponseEntity<?> getDetallesUsuarioUserId(@PathVariable Long id) {
        log.debug("REST request to get DetallesUsuario : {}", id);
        Optional<DetallesUsuarioDTO> detallesUsuarioDTO = detallesUsuarioService.findByUserId(id);
        return ResponseUtil.wrapOrNotFound(detallesUsuarioDTO);
    }

    @GetMapping("/detalles-usuarios/login/{login}")
    public ResponseEntity<?> getDetallesUsuarioUserLogin(@PathVariable String login) {
        log.debug("REST request to get DetallesUsuario : {}", login);
        Optional<DetallesUsuarioDTO> detallesUsuarioDTO = detallesUsuarioService.findByUserLogin(login);
        return ResponseUtil.wrapOrNotFound(detallesUsuarioDTO);
    }

    /**
     * {@code DELETE  /detalles-usuarios/:id} : delete the "id" detallesUsuario.
     *
     * @param id the id of the detallesUsuarioDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/detalles-usuarios/{id}")
    public ResponseEntity<Void> deleteDetallesUsuario(@PathVariable Long id) {
        log.debug("REST request to delete DetallesUsuario : {}", id);
        detallesUsuarioService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
