package co.edu.usco.inventariotecnopcmundo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.edu.usco.inventariotecnopcmundo.IntegrationTest;
import co.edu.usco.inventariotecnopcmundo.domain.CategoriaPublicacion;
import co.edu.usco.inventariotecnopcmundo.repository.CategoriaPublicacionRepository;
import co.edu.usco.inventariotecnopcmundo.service.dto.CategoriaPublicacionDTO;
import co.edu.usco.inventariotecnopcmundo.service.mapper.CategoriaPublicacionMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link CategoriaPublicacionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CategoriaPublicacionResourceIT {

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATE_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATE_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/categoria-publicacions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CategoriaPublicacionRepository categoriaPublicacionRepository;

    @Autowired
    private CategoriaPublicacionMapper categoriaPublicacionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCategoriaPublicacionMockMvc;

    private CategoriaPublicacion categoriaPublicacion;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategoriaPublicacion createEntity(EntityManager em) {
        CategoriaPublicacion categoriaPublicacion = new CategoriaPublicacion()
            .titulo(DEFAULT_TITULO)
            .descripcion(DEFAULT_DESCRIPCION)
            .createAt(DEFAULT_CREATE_AT);
        return categoriaPublicacion;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategoriaPublicacion createUpdatedEntity(EntityManager em) {
        CategoriaPublicacion categoriaPublicacion = new CategoriaPublicacion()
            .titulo(UPDATED_TITULO)
            .descripcion(UPDATED_DESCRIPCION)
            .createAt(UPDATED_CREATE_AT);
        return categoriaPublicacion;
    }

    @BeforeEach
    public void initTest() {
        categoriaPublicacion = createEntity(em);
    }

    @Test
    @Transactional
    void createCategoriaPublicacion() throws Exception {
        int databaseSizeBeforeCreate = categoriaPublicacionRepository.findAll().size();
        // Create the CategoriaPublicacion
        CategoriaPublicacionDTO categoriaPublicacionDTO = categoriaPublicacionMapper.toDto(categoriaPublicacion);
        restCategoriaPublicacionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categoriaPublicacionDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CategoriaPublicacion in the database
        List<CategoriaPublicacion> categoriaPublicacionList = categoriaPublicacionRepository.findAll();
        assertThat(categoriaPublicacionList).hasSize(databaseSizeBeforeCreate + 1);
        CategoriaPublicacion testCategoriaPublicacion = categoriaPublicacionList.get(categoriaPublicacionList.size() - 1);
        assertThat(testCategoriaPublicacion.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testCategoriaPublicacion.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testCategoriaPublicacion.getCreateAt()).isEqualTo(DEFAULT_CREATE_AT);
    }

    @Test
    @Transactional
    void createCategoriaPublicacionWithExistingId() throws Exception {
        // Create the CategoriaPublicacion with an existing ID
        categoriaPublicacion.setId(1L);
        CategoriaPublicacionDTO categoriaPublicacionDTO = categoriaPublicacionMapper.toDto(categoriaPublicacion);

        int databaseSizeBeforeCreate = categoriaPublicacionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCategoriaPublicacionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categoriaPublicacionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategoriaPublicacion in the database
        List<CategoriaPublicacion> categoriaPublicacionList = categoriaPublicacionRepository.findAll();
        assertThat(categoriaPublicacionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCategoriaPublicacions() throws Exception {
        // Initialize the database
        categoriaPublicacionRepository.saveAndFlush(categoriaPublicacion);

        // Get all the categoriaPublicacionList
        restCategoriaPublicacionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categoriaPublicacion.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].createAt").value(hasItem(DEFAULT_CREATE_AT.toString())));
    }

    @Test
    @Transactional
    void getCategoriaPublicacion() throws Exception {
        // Initialize the database
        categoriaPublicacionRepository.saveAndFlush(categoriaPublicacion);

        // Get the categoriaPublicacion
        restCategoriaPublicacionMockMvc
            .perform(get(ENTITY_API_URL_ID, categoriaPublicacion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(categoriaPublicacion.getId().intValue()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.createAt").value(DEFAULT_CREATE_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingCategoriaPublicacion() throws Exception {
        // Get the categoriaPublicacion
        restCategoriaPublicacionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCategoriaPublicacion() throws Exception {
        // Initialize the database
        categoriaPublicacionRepository.saveAndFlush(categoriaPublicacion);

        int databaseSizeBeforeUpdate = categoriaPublicacionRepository.findAll().size();

        // Update the categoriaPublicacion
        CategoriaPublicacion updatedCategoriaPublicacion = categoriaPublicacionRepository.findById(categoriaPublicacion.getId()).get();
        // Disconnect from session so that the updates on updatedCategoriaPublicacion are not directly saved in db
        em.detach(updatedCategoriaPublicacion);
        updatedCategoriaPublicacion.titulo(UPDATED_TITULO).descripcion(UPDATED_DESCRIPCION).createAt(UPDATED_CREATE_AT);
        CategoriaPublicacionDTO categoriaPublicacionDTO = categoriaPublicacionMapper.toDto(updatedCategoriaPublicacion);

        restCategoriaPublicacionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, categoriaPublicacionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categoriaPublicacionDTO))
            )
            .andExpect(status().isOk());

        // Validate the CategoriaPublicacion in the database
        List<CategoriaPublicacion> categoriaPublicacionList = categoriaPublicacionRepository.findAll();
        assertThat(categoriaPublicacionList).hasSize(databaseSizeBeforeUpdate);
        CategoriaPublicacion testCategoriaPublicacion = categoriaPublicacionList.get(categoriaPublicacionList.size() - 1);
        assertThat(testCategoriaPublicacion.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testCategoriaPublicacion.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testCategoriaPublicacion.getCreateAt()).isEqualTo(UPDATED_CREATE_AT);
    }

    @Test
    @Transactional
    void putNonExistingCategoriaPublicacion() throws Exception {
        int databaseSizeBeforeUpdate = categoriaPublicacionRepository.findAll().size();
        categoriaPublicacion.setId(count.incrementAndGet());

        // Create the CategoriaPublicacion
        CategoriaPublicacionDTO categoriaPublicacionDTO = categoriaPublicacionMapper.toDto(categoriaPublicacion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategoriaPublicacionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, categoriaPublicacionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categoriaPublicacionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategoriaPublicacion in the database
        List<CategoriaPublicacion> categoriaPublicacionList = categoriaPublicacionRepository.findAll();
        assertThat(categoriaPublicacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCategoriaPublicacion() throws Exception {
        int databaseSizeBeforeUpdate = categoriaPublicacionRepository.findAll().size();
        categoriaPublicacion.setId(count.incrementAndGet());

        // Create the CategoriaPublicacion
        CategoriaPublicacionDTO categoriaPublicacionDTO = categoriaPublicacionMapper.toDto(categoriaPublicacion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategoriaPublicacionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categoriaPublicacionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategoriaPublicacion in the database
        List<CategoriaPublicacion> categoriaPublicacionList = categoriaPublicacionRepository.findAll();
        assertThat(categoriaPublicacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCategoriaPublicacion() throws Exception {
        int databaseSizeBeforeUpdate = categoriaPublicacionRepository.findAll().size();
        categoriaPublicacion.setId(count.incrementAndGet());

        // Create the CategoriaPublicacion
        CategoriaPublicacionDTO categoriaPublicacionDTO = categoriaPublicacionMapper.toDto(categoriaPublicacion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategoriaPublicacionMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categoriaPublicacionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CategoriaPublicacion in the database
        List<CategoriaPublicacion> categoriaPublicacionList = categoriaPublicacionRepository.findAll();
        assertThat(categoriaPublicacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCategoriaPublicacionWithPatch() throws Exception {
        // Initialize the database
        categoriaPublicacionRepository.saveAndFlush(categoriaPublicacion);

        int databaseSizeBeforeUpdate = categoriaPublicacionRepository.findAll().size();

        // Update the categoriaPublicacion using partial update
        CategoriaPublicacion partialUpdatedCategoriaPublicacion = new CategoriaPublicacion();
        partialUpdatedCategoriaPublicacion.setId(categoriaPublicacion.getId());

        partialUpdatedCategoriaPublicacion.createAt(UPDATED_CREATE_AT);

        restCategoriaPublicacionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCategoriaPublicacion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCategoriaPublicacion))
            )
            .andExpect(status().isOk());

        // Validate the CategoriaPublicacion in the database
        List<CategoriaPublicacion> categoriaPublicacionList = categoriaPublicacionRepository.findAll();
        assertThat(categoriaPublicacionList).hasSize(databaseSizeBeforeUpdate);
        CategoriaPublicacion testCategoriaPublicacion = categoriaPublicacionList.get(categoriaPublicacionList.size() - 1);
        assertThat(testCategoriaPublicacion.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testCategoriaPublicacion.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testCategoriaPublicacion.getCreateAt()).isEqualTo(UPDATED_CREATE_AT);
    }

    @Test
    @Transactional
    void fullUpdateCategoriaPublicacionWithPatch() throws Exception {
        // Initialize the database
        categoriaPublicacionRepository.saveAndFlush(categoriaPublicacion);

        int databaseSizeBeforeUpdate = categoriaPublicacionRepository.findAll().size();

        // Update the categoriaPublicacion using partial update
        CategoriaPublicacion partialUpdatedCategoriaPublicacion = new CategoriaPublicacion();
        partialUpdatedCategoriaPublicacion.setId(categoriaPublicacion.getId());

        partialUpdatedCategoriaPublicacion.titulo(UPDATED_TITULO).descripcion(UPDATED_DESCRIPCION).createAt(UPDATED_CREATE_AT);

        restCategoriaPublicacionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCategoriaPublicacion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCategoriaPublicacion))
            )
            .andExpect(status().isOk());

        // Validate the CategoriaPublicacion in the database
        List<CategoriaPublicacion> categoriaPublicacionList = categoriaPublicacionRepository.findAll();
        assertThat(categoriaPublicacionList).hasSize(databaseSizeBeforeUpdate);
        CategoriaPublicacion testCategoriaPublicacion = categoriaPublicacionList.get(categoriaPublicacionList.size() - 1);
        assertThat(testCategoriaPublicacion.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testCategoriaPublicacion.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testCategoriaPublicacion.getCreateAt()).isEqualTo(UPDATED_CREATE_AT);
    }

    @Test
    @Transactional
    void patchNonExistingCategoriaPublicacion() throws Exception {
        int databaseSizeBeforeUpdate = categoriaPublicacionRepository.findAll().size();
        categoriaPublicacion.setId(count.incrementAndGet());

        // Create the CategoriaPublicacion
        CategoriaPublicacionDTO categoriaPublicacionDTO = categoriaPublicacionMapper.toDto(categoriaPublicacion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategoriaPublicacionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, categoriaPublicacionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(categoriaPublicacionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategoriaPublicacion in the database
        List<CategoriaPublicacion> categoriaPublicacionList = categoriaPublicacionRepository.findAll();
        assertThat(categoriaPublicacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCategoriaPublicacion() throws Exception {
        int databaseSizeBeforeUpdate = categoriaPublicacionRepository.findAll().size();
        categoriaPublicacion.setId(count.incrementAndGet());

        // Create the CategoriaPublicacion
        CategoriaPublicacionDTO categoriaPublicacionDTO = categoriaPublicacionMapper.toDto(categoriaPublicacion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategoriaPublicacionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(categoriaPublicacionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategoriaPublicacion in the database
        List<CategoriaPublicacion> categoriaPublicacionList = categoriaPublicacionRepository.findAll();
        assertThat(categoriaPublicacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCategoriaPublicacion() throws Exception {
        int databaseSizeBeforeUpdate = categoriaPublicacionRepository.findAll().size();
        categoriaPublicacion.setId(count.incrementAndGet());

        // Create the CategoriaPublicacion
        CategoriaPublicacionDTO categoriaPublicacionDTO = categoriaPublicacionMapper.toDto(categoriaPublicacion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategoriaPublicacionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(categoriaPublicacionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CategoriaPublicacion in the database
        List<CategoriaPublicacion> categoriaPublicacionList = categoriaPublicacionRepository.findAll();
        assertThat(categoriaPublicacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCategoriaPublicacion() throws Exception {
        // Initialize the database
        categoriaPublicacionRepository.saveAndFlush(categoriaPublicacion);

        int databaseSizeBeforeDelete = categoriaPublicacionRepository.findAll().size();

        // Delete the categoriaPublicacion
        restCategoriaPublicacionMockMvc
            .perform(delete(ENTITY_API_URL_ID, categoriaPublicacion.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CategoriaPublicacion> categoriaPublicacionList = categoriaPublicacionRepository.findAll();
        assertThat(categoriaPublicacionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
