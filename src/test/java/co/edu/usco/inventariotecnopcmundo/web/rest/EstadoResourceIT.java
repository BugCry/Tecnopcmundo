package co.edu.usco.inventariotecnopcmundo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.edu.usco.inventariotecnopcmundo.IntegrationTest;
import co.edu.usco.inventariotecnopcmundo.domain.Estado;
import co.edu.usco.inventariotecnopcmundo.repository.EstadoRepository;
import co.edu.usco.inventariotecnopcmundo.service.dto.EstadoDTO;
import co.edu.usco.inventariotecnopcmundo.service.mapper.EstadoMapper;
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
 * Integration tests for the {@link EstadoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EstadoResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/estados";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private EstadoMapper estadoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEstadoMockMvc;

    private Estado estado;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Estado createEntity(EntityManager em) {
        Estado estado = new Estado().nombre(DEFAULT_NOMBRE);
        return estado;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Estado createUpdatedEntity(EntityManager em) {
        Estado estado = new Estado().nombre(UPDATED_NOMBRE);
        return estado;
    }

    @BeforeEach
    public void initTest() {
        estado = createEntity(em);
    }

    @Test
    @Transactional
    void createEstado() throws Exception {
        int databaseSizeBeforeCreate = estadoRepository.findAll().size();
        // Create the Estado
        EstadoDTO estadoDTO = estadoMapper.toDto(estado);
        restEstadoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(estadoDTO)))
            .andExpect(status().isCreated());

        // Validate the Estado in the database
        List<Estado> estadoList = estadoRepository.findAll();
        assertThat(estadoList).hasSize(databaseSizeBeforeCreate + 1);
        Estado testEstado = estadoList.get(estadoList.size() - 1);
        assertThat(testEstado.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    void createEstadoWithExistingId() throws Exception {
        // Create the Estado with an existing ID
        estado.setId(1L);
        EstadoDTO estadoDTO = estadoMapper.toDto(estado);

        int databaseSizeBeforeCreate = estadoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEstadoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(estadoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Estado in the database
        List<Estado> estadoList = estadoRepository.findAll();
        assertThat(estadoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEstados() throws Exception {
        // Initialize the database
        estadoRepository.saveAndFlush(estado);

        // Get all the estadoList
        restEstadoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(estado.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)));
    }

    @Test
    @Transactional
    void getEstado() throws Exception {
        // Initialize the database
        estadoRepository.saveAndFlush(estado);

        // Get the estado
        restEstadoMockMvc
            .perform(get(ENTITY_API_URL_ID, estado.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(estado.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE));
    }

    @Test
    @Transactional
    void getNonExistingEstado() throws Exception {
        // Get the estado
        restEstadoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEstado() throws Exception {
        // Initialize the database
        estadoRepository.saveAndFlush(estado);

        int databaseSizeBeforeUpdate = estadoRepository.findAll().size();

        // Update the estado
        Estado updatedEstado = estadoRepository.findById(estado.getId()).get();
        // Disconnect from session so that the updates on updatedEstado are not directly saved in db
        em.detach(updatedEstado);
        updatedEstado.nombre(UPDATED_NOMBRE);
        EstadoDTO estadoDTO = estadoMapper.toDto(updatedEstado);

        restEstadoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, estadoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(estadoDTO))
            )
            .andExpect(status().isOk());

        // Validate the Estado in the database
        List<Estado> estadoList = estadoRepository.findAll();
        assertThat(estadoList).hasSize(databaseSizeBeforeUpdate);
        Estado testEstado = estadoList.get(estadoList.size() - 1);
        assertThat(testEstado.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void putNonExistingEstado() throws Exception {
        int databaseSizeBeforeUpdate = estadoRepository.findAll().size();
        estado.setId(count.incrementAndGet());

        // Create the Estado
        EstadoDTO estadoDTO = estadoMapper.toDto(estado);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstadoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, estadoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(estadoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Estado in the database
        List<Estado> estadoList = estadoRepository.findAll();
        assertThat(estadoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEstado() throws Exception {
        int databaseSizeBeforeUpdate = estadoRepository.findAll().size();
        estado.setId(count.incrementAndGet());

        // Create the Estado
        EstadoDTO estadoDTO = estadoMapper.toDto(estado);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstadoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(estadoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Estado in the database
        List<Estado> estadoList = estadoRepository.findAll();
        assertThat(estadoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEstado() throws Exception {
        int databaseSizeBeforeUpdate = estadoRepository.findAll().size();
        estado.setId(count.incrementAndGet());

        // Create the Estado
        EstadoDTO estadoDTO = estadoMapper.toDto(estado);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstadoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(estadoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Estado in the database
        List<Estado> estadoList = estadoRepository.findAll();
        assertThat(estadoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEstadoWithPatch() throws Exception {
        // Initialize the database
        estadoRepository.saveAndFlush(estado);

        int databaseSizeBeforeUpdate = estadoRepository.findAll().size();

        // Update the estado using partial update
        Estado partialUpdatedEstado = new Estado();
        partialUpdatedEstado.setId(estado.getId());

        restEstadoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEstado.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEstado))
            )
            .andExpect(status().isOk());

        // Validate the Estado in the database
        List<Estado> estadoList = estadoRepository.findAll();
        assertThat(estadoList).hasSize(databaseSizeBeforeUpdate);
        Estado testEstado = estadoList.get(estadoList.size() - 1);
        assertThat(testEstado.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    void fullUpdateEstadoWithPatch() throws Exception {
        // Initialize the database
        estadoRepository.saveAndFlush(estado);

        int databaseSizeBeforeUpdate = estadoRepository.findAll().size();

        // Update the estado using partial update
        Estado partialUpdatedEstado = new Estado();
        partialUpdatedEstado.setId(estado.getId());

        partialUpdatedEstado.nombre(UPDATED_NOMBRE);

        restEstadoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEstado.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEstado))
            )
            .andExpect(status().isOk());

        // Validate the Estado in the database
        List<Estado> estadoList = estadoRepository.findAll();
        assertThat(estadoList).hasSize(databaseSizeBeforeUpdate);
        Estado testEstado = estadoList.get(estadoList.size() - 1);
        assertThat(testEstado.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void patchNonExistingEstado() throws Exception {
        int databaseSizeBeforeUpdate = estadoRepository.findAll().size();
        estado.setId(count.incrementAndGet());

        // Create the Estado
        EstadoDTO estadoDTO = estadoMapper.toDto(estado);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstadoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, estadoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(estadoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Estado in the database
        List<Estado> estadoList = estadoRepository.findAll();
        assertThat(estadoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEstado() throws Exception {
        int databaseSizeBeforeUpdate = estadoRepository.findAll().size();
        estado.setId(count.incrementAndGet());

        // Create the Estado
        EstadoDTO estadoDTO = estadoMapper.toDto(estado);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstadoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(estadoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Estado in the database
        List<Estado> estadoList = estadoRepository.findAll();
        assertThat(estadoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEstado() throws Exception {
        int databaseSizeBeforeUpdate = estadoRepository.findAll().size();
        estado.setId(count.incrementAndGet());

        // Create the Estado
        EstadoDTO estadoDTO = estadoMapper.toDto(estado);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstadoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(estadoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Estado in the database
        List<Estado> estadoList = estadoRepository.findAll();
        assertThat(estadoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEstado() throws Exception {
        // Initialize the database
        estadoRepository.saveAndFlush(estado);

        int databaseSizeBeforeDelete = estadoRepository.findAll().size();

        // Delete the estado
        restEstadoMockMvc
            .perform(delete(ENTITY_API_URL_ID, estado.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Estado> estadoList = estadoRepository.findAll();
        assertThat(estadoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
