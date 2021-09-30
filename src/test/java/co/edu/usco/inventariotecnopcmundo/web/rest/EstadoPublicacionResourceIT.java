package co.edu.usco.inventariotecnopcmundo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.edu.usco.inventariotecnopcmundo.IntegrationTest;
import co.edu.usco.inventariotecnopcmundo.domain.EstadoPublicacion;
import co.edu.usco.inventariotecnopcmundo.repository.EstadoPublicacionRepository;
import co.edu.usco.inventariotecnopcmundo.service.dto.EstadoPublicacionDTO;
import co.edu.usco.inventariotecnopcmundo.service.mapper.EstadoPublicacionMapper;
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

/**
 * Integration tests for the {@link EstadoPublicacionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EstadoPublicacionResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/estado-publicacions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EstadoPublicacionRepository estadoPublicacionRepository;

    @Autowired
    private EstadoPublicacionMapper estadoPublicacionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEstadoPublicacionMockMvc;

    private EstadoPublicacion estadoPublicacion;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EstadoPublicacion createEntity(EntityManager em) {
        EstadoPublicacion estadoPublicacion = new EstadoPublicacion().nombre(DEFAULT_NOMBRE);
        return estadoPublicacion;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EstadoPublicacion createUpdatedEntity(EntityManager em) {
        EstadoPublicacion estadoPublicacion = new EstadoPublicacion().nombre(UPDATED_NOMBRE);
        return estadoPublicacion;
    }

    @BeforeEach
    public void initTest() {
        estadoPublicacion = createEntity(em);
    }

    @Test
    @Transactional
    void createEstadoPublicacion() throws Exception {
        int databaseSizeBeforeCreate = estadoPublicacionRepository.findAll().size();
        // Create the EstadoPublicacion
        EstadoPublicacionDTO estadoPublicacionDTO = estadoPublicacionMapper.toDto(estadoPublicacion);
        restEstadoPublicacionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(estadoPublicacionDTO))
            )
            .andExpect(status().isCreated());

        // Validate the EstadoPublicacion in the database
        List<EstadoPublicacion> estadoPublicacionList = estadoPublicacionRepository.findAll();
        assertThat(estadoPublicacionList).hasSize(databaseSizeBeforeCreate + 1);
        EstadoPublicacion testEstadoPublicacion = estadoPublicacionList.get(estadoPublicacionList.size() - 1);
        assertThat(testEstadoPublicacion.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    void createEstadoPublicacionWithExistingId() throws Exception {
        // Create the EstadoPublicacion with an existing ID
        estadoPublicacion.setId(1L);
        EstadoPublicacionDTO estadoPublicacionDTO = estadoPublicacionMapper.toDto(estadoPublicacion);

        int databaseSizeBeforeCreate = estadoPublicacionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEstadoPublicacionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(estadoPublicacionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EstadoPublicacion in the database
        List<EstadoPublicacion> estadoPublicacionList = estadoPublicacionRepository.findAll();
        assertThat(estadoPublicacionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEstadoPublicacions() throws Exception {
        // Initialize the database
        estadoPublicacionRepository.saveAndFlush(estadoPublicacion);

        // Get all the estadoPublicacionList
        restEstadoPublicacionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(estadoPublicacion.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)));
    }

    @Test
    @Transactional
    void getEstadoPublicacion() throws Exception {
        // Initialize the database
        estadoPublicacionRepository.saveAndFlush(estadoPublicacion);

        // Get the estadoPublicacion
        restEstadoPublicacionMockMvc
            .perform(get(ENTITY_API_URL_ID, estadoPublicacion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(estadoPublicacion.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE));
    }

    @Test
    @Transactional
    void getNonExistingEstadoPublicacion() throws Exception {
        // Get the estadoPublicacion
        restEstadoPublicacionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEstadoPublicacion() throws Exception {
        // Initialize the database
        estadoPublicacionRepository.saveAndFlush(estadoPublicacion);

        int databaseSizeBeforeUpdate = estadoPublicacionRepository.findAll().size();

        // Update the estadoPublicacion
        EstadoPublicacion updatedEstadoPublicacion = estadoPublicacionRepository.findById(estadoPublicacion.getId()).get();
        // Disconnect from session so that the updates on updatedEstadoPublicacion are not directly saved in db
        em.detach(updatedEstadoPublicacion);
        updatedEstadoPublicacion.nombre(UPDATED_NOMBRE);
        EstadoPublicacionDTO estadoPublicacionDTO = estadoPublicacionMapper.toDto(updatedEstadoPublicacion);

        restEstadoPublicacionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, estadoPublicacionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(estadoPublicacionDTO))
            )
            .andExpect(status().isOk());

        // Validate the EstadoPublicacion in the database
        List<EstadoPublicacion> estadoPublicacionList = estadoPublicacionRepository.findAll();
        assertThat(estadoPublicacionList).hasSize(databaseSizeBeforeUpdate);
        EstadoPublicacion testEstadoPublicacion = estadoPublicacionList.get(estadoPublicacionList.size() - 1);
        assertThat(testEstadoPublicacion.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void putNonExistingEstadoPublicacion() throws Exception {
        int databaseSizeBeforeUpdate = estadoPublicacionRepository.findAll().size();
        estadoPublicacion.setId(count.incrementAndGet());

        // Create the EstadoPublicacion
        EstadoPublicacionDTO estadoPublicacionDTO = estadoPublicacionMapper.toDto(estadoPublicacion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstadoPublicacionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, estadoPublicacionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(estadoPublicacionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EstadoPublicacion in the database
        List<EstadoPublicacion> estadoPublicacionList = estadoPublicacionRepository.findAll();
        assertThat(estadoPublicacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEstadoPublicacion() throws Exception {
        int databaseSizeBeforeUpdate = estadoPublicacionRepository.findAll().size();
        estadoPublicacion.setId(count.incrementAndGet());

        // Create the EstadoPublicacion
        EstadoPublicacionDTO estadoPublicacionDTO = estadoPublicacionMapper.toDto(estadoPublicacion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstadoPublicacionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(estadoPublicacionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EstadoPublicacion in the database
        List<EstadoPublicacion> estadoPublicacionList = estadoPublicacionRepository.findAll();
        assertThat(estadoPublicacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEstadoPublicacion() throws Exception {
        int databaseSizeBeforeUpdate = estadoPublicacionRepository.findAll().size();
        estadoPublicacion.setId(count.incrementAndGet());

        // Create the EstadoPublicacion
        EstadoPublicacionDTO estadoPublicacionDTO = estadoPublicacionMapper.toDto(estadoPublicacion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstadoPublicacionMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(estadoPublicacionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EstadoPublicacion in the database
        List<EstadoPublicacion> estadoPublicacionList = estadoPublicacionRepository.findAll();
        assertThat(estadoPublicacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEstadoPublicacionWithPatch() throws Exception {
        // Initialize the database
        estadoPublicacionRepository.saveAndFlush(estadoPublicacion);

        int databaseSizeBeforeUpdate = estadoPublicacionRepository.findAll().size();

        // Update the estadoPublicacion using partial update
        EstadoPublicacion partialUpdatedEstadoPublicacion = new EstadoPublicacion();
        partialUpdatedEstadoPublicacion.setId(estadoPublicacion.getId());

        restEstadoPublicacionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEstadoPublicacion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEstadoPublicacion))
            )
            .andExpect(status().isOk());

        // Validate the EstadoPublicacion in the database
        List<EstadoPublicacion> estadoPublicacionList = estadoPublicacionRepository.findAll();
        assertThat(estadoPublicacionList).hasSize(databaseSizeBeforeUpdate);
        EstadoPublicacion testEstadoPublicacion = estadoPublicacionList.get(estadoPublicacionList.size() - 1);
        assertThat(testEstadoPublicacion.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    void fullUpdateEstadoPublicacionWithPatch() throws Exception {
        // Initialize the database
        estadoPublicacionRepository.saveAndFlush(estadoPublicacion);

        int databaseSizeBeforeUpdate = estadoPublicacionRepository.findAll().size();

        // Update the estadoPublicacion using partial update
        EstadoPublicacion partialUpdatedEstadoPublicacion = new EstadoPublicacion();
        partialUpdatedEstadoPublicacion.setId(estadoPublicacion.getId());

        partialUpdatedEstadoPublicacion.nombre(UPDATED_NOMBRE);

        restEstadoPublicacionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEstadoPublicacion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEstadoPublicacion))
            )
            .andExpect(status().isOk());

        // Validate the EstadoPublicacion in the database
        List<EstadoPublicacion> estadoPublicacionList = estadoPublicacionRepository.findAll();
        assertThat(estadoPublicacionList).hasSize(databaseSizeBeforeUpdate);
        EstadoPublicacion testEstadoPublicacion = estadoPublicacionList.get(estadoPublicacionList.size() - 1);
        assertThat(testEstadoPublicacion.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void patchNonExistingEstadoPublicacion() throws Exception {
        int databaseSizeBeforeUpdate = estadoPublicacionRepository.findAll().size();
        estadoPublicacion.setId(count.incrementAndGet());

        // Create the EstadoPublicacion
        EstadoPublicacionDTO estadoPublicacionDTO = estadoPublicacionMapper.toDto(estadoPublicacion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstadoPublicacionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, estadoPublicacionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(estadoPublicacionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EstadoPublicacion in the database
        List<EstadoPublicacion> estadoPublicacionList = estadoPublicacionRepository.findAll();
        assertThat(estadoPublicacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEstadoPublicacion() throws Exception {
        int databaseSizeBeforeUpdate = estadoPublicacionRepository.findAll().size();
        estadoPublicacion.setId(count.incrementAndGet());

        // Create the EstadoPublicacion
        EstadoPublicacionDTO estadoPublicacionDTO = estadoPublicacionMapper.toDto(estadoPublicacion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstadoPublicacionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(estadoPublicacionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EstadoPublicacion in the database
        List<EstadoPublicacion> estadoPublicacionList = estadoPublicacionRepository.findAll();
        assertThat(estadoPublicacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEstadoPublicacion() throws Exception {
        int databaseSizeBeforeUpdate = estadoPublicacionRepository.findAll().size();
        estadoPublicacion.setId(count.incrementAndGet());

        // Create the EstadoPublicacion
        EstadoPublicacionDTO estadoPublicacionDTO = estadoPublicacionMapper.toDto(estadoPublicacion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstadoPublicacionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(estadoPublicacionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EstadoPublicacion in the database
        List<EstadoPublicacion> estadoPublicacionList = estadoPublicacionRepository.findAll();
        assertThat(estadoPublicacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEstadoPublicacion() throws Exception {
        // Initialize the database
        estadoPublicacionRepository.saveAndFlush(estadoPublicacion);

        int databaseSizeBeforeDelete = estadoPublicacionRepository.findAll().size();

        // Delete the estadoPublicacion
        restEstadoPublicacionMockMvc
            .perform(delete(ENTITY_API_URL_ID, estadoPublicacion.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EstadoPublicacion> estadoPublicacionList = estadoPublicacionRepository.findAll();
        assertThat(estadoPublicacionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
