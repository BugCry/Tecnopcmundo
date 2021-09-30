package co.edu.usco.inventariotecnopcmundo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.edu.usco.inventariotecnopcmundo.IntegrationTest;
import co.edu.usco.inventariotecnopcmundo.domain.DetallesUsuario;
import co.edu.usco.inventariotecnopcmundo.repository.DetallesUsuarioRepository;
import co.edu.usco.inventariotecnopcmundo.service.dto.DetallesUsuarioDTO;
import co.edu.usco.inventariotecnopcmundo.service.mapper.DetallesUsuarioMapper;
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
 * Integration tests for the {@link DetallesUsuarioResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DetallesUsuarioResourceIT {

    private static final String DEFAULT_TELEFONO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO = "BBBBBBBBBB";

    private static final String DEFAULT_IDENTIFICACION = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFICACION = "BBBBBBBBBB";

    private static final String DEFAULT_CIUDAD = "AAAAAAAAAA";
    private static final String UPDATED_CIUDAD = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/detalles-usuarios";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DetallesUsuarioRepository detallesUsuarioRepository;

    @Autowired
    private DetallesUsuarioMapper detallesUsuarioMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDetallesUsuarioMockMvc;

    private DetallesUsuario detallesUsuario;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DetallesUsuario createEntity(EntityManager em) {
        DetallesUsuario detallesUsuario = new DetallesUsuario()
            .telefono(DEFAULT_TELEFONO)
            .identificacion(DEFAULT_IDENTIFICACION)
            .ciudad(DEFAULT_CIUDAD);
        return detallesUsuario;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DetallesUsuario createUpdatedEntity(EntityManager em) {
        DetallesUsuario detallesUsuario = new DetallesUsuario()
            .telefono(UPDATED_TELEFONO)
            .identificacion(UPDATED_IDENTIFICACION)
            .ciudad(UPDATED_CIUDAD);
        return detallesUsuario;
    }

    @BeforeEach
    public void initTest() {
        detallesUsuario = createEntity(em);
    }

    @Test
    @Transactional
    void createDetallesUsuario() throws Exception {
        int databaseSizeBeforeCreate = detallesUsuarioRepository.findAll().size();
        // Create the DetallesUsuario
        DetallesUsuarioDTO detallesUsuarioDTO = detallesUsuarioMapper.toDto(detallesUsuario);
        restDetallesUsuarioMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(detallesUsuarioDTO))
            )
            .andExpect(status().isCreated());

        // Validate the DetallesUsuario in the database
        List<DetallesUsuario> detallesUsuarioList = detallesUsuarioRepository.findAll();
        assertThat(detallesUsuarioList).hasSize(databaseSizeBeforeCreate + 1);
        DetallesUsuario testDetallesUsuario = detallesUsuarioList.get(detallesUsuarioList.size() - 1);
        assertThat(testDetallesUsuario.getTelefono()).isEqualTo(DEFAULT_TELEFONO);
        assertThat(testDetallesUsuario.getIdentificacion()).isEqualTo(DEFAULT_IDENTIFICACION);
        assertThat(testDetallesUsuario.getCiudad()).isEqualTo(DEFAULT_CIUDAD);
    }

    @Test
    @Transactional
    void createDetallesUsuarioWithExistingId() throws Exception {
        // Create the DetallesUsuario with an existing ID
        detallesUsuario.setId(1L);
        DetallesUsuarioDTO detallesUsuarioDTO = detallesUsuarioMapper.toDto(detallesUsuario);

        int databaseSizeBeforeCreate = detallesUsuarioRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDetallesUsuarioMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(detallesUsuarioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DetallesUsuario in the database
        List<DetallesUsuario> detallesUsuarioList = detallesUsuarioRepository.findAll();
        assertThat(detallesUsuarioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDetallesUsuarios() throws Exception {
        // Initialize the database
        detallesUsuarioRepository.saveAndFlush(detallesUsuario);

        // Get all the detallesUsuarioList
        restDetallesUsuarioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(detallesUsuario.getId().intValue())))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO)))
            .andExpect(jsonPath("$.[*].identificacion").value(hasItem(DEFAULT_IDENTIFICACION)))
            .andExpect(jsonPath("$.[*].ciudad").value(hasItem(DEFAULT_CIUDAD)));
    }

    @Test
    @Transactional
    void getDetallesUsuario() throws Exception {
        // Initialize the database
        detallesUsuarioRepository.saveAndFlush(detallesUsuario);

        // Get the detallesUsuario
        restDetallesUsuarioMockMvc
            .perform(get(ENTITY_API_URL_ID, detallesUsuario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(detallesUsuario.getId().intValue()))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO))
            .andExpect(jsonPath("$.identificacion").value(DEFAULT_IDENTIFICACION))
            .andExpect(jsonPath("$.ciudad").value(DEFAULT_CIUDAD));
    }

    @Test
    @Transactional
    void getNonExistingDetallesUsuario() throws Exception {
        // Get the detallesUsuario
        restDetallesUsuarioMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDetallesUsuario() throws Exception {
        // Initialize the database
        detallesUsuarioRepository.saveAndFlush(detallesUsuario);

        int databaseSizeBeforeUpdate = detallesUsuarioRepository.findAll().size();

        // Update the detallesUsuario
        DetallesUsuario updatedDetallesUsuario = detallesUsuarioRepository.findById(detallesUsuario.getId()).get();
        // Disconnect from session so that the updates on updatedDetallesUsuario are not directly saved in db
        em.detach(updatedDetallesUsuario);
        updatedDetallesUsuario.telefono(UPDATED_TELEFONO).identificacion(UPDATED_IDENTIFICACION).ciudad(UPDATED_CIUDAD);
        DetallesUsuarioDTO detallesUsuarioDTO = detallesUsuarioMapper.toDto(updatedDetallesUsuario);

        restDetallesUsuarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, detallesUsuarioDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(detallesUsuarioDTO))
            )
            .andExpect(status().isOk());

        // Validate the DetallesUsuario in the database
        List<DetallesUsuario> detallesUsuarioList = detallesUsuarioRepository.findAll();
        assertThat(detallesUsuarioList).hasSize(databaseSizeBeforeUpdate);
        DetallesUsuario testDetallesUsuario = detallesUsuarioList.get(detallesUsuarioList.size() - 1);
        assertThat(testDetallesUsuario.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testDetallesUsuario.getIdentificacion()).isEqualTo(UPDATED_IDENTIFICACION);
        assertThat(testDetallesUsuario.getCiudad()).isEqualTo(UPDATED_CIUDAD);
    }

    @Test
    @Transactional
    void putNonExistingDetallesUsuario() throws Exception {
        int databaseSizeBeforeUpdate = detallesUsuarioRepository.findAll().size();
        detallesUsuario.setId(count.incrementAndGet());

        // Create the DetallesUsuario
        DetallesUsuarioDTO detallesUsuarioDTO = detallesUsuarioMapper.toDto(detallesUsuario);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDetallesUsuarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, detallesUsuarioDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(detallesUsuarioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DetallesUsuario in the database
        List<DetallesUsuario> detallesUsuarioList = detallesUsuarioRepository.findAll();
        assertThat(detallesUsuarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDetallesUsuario() throws Exception {
        int databaseSizeBeforeUpdate = detallesUsuarioRepository.findAll().size();
        detallesUsuario.setId(count.incrementAndGet());

        // Create the DetallesUsuario
        DetallesUsuarioDTO detallesUsuarioDTO = detallesUsuarioMapper.toDto(detallesUsuario);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDetallesUsuarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(detallesUsuarioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DetallesUsuario in the database
        List<DetallesUsuario> detallesUsuarioList = detallesUsuarioRepository.findAll();
        assertThat(detallesUsuarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDetallesUsuario() throws Exception {
        int databaseSizeBeforeUpdate = detallesUsuarioRepository.findAll().size();
        detallesUsuario.setId(count.incrementAndGet());

        // Create the DetallesUsuario
        DetallesUsuarioDTO detallesUsuarioDTO = detallesUsuarioMapper.toDto(detallesUsuario);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDetallesUsuarioMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(detallesUsuarioDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DetallesUsuario in the database
        List<DetallesUsuario> detallesUsuarioList = detallesUsuarioRepository.findAll();
        assertThat(detallesUsuarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDetallesUsuarioWithPatch() throws Exception {
        // Initialize the database
        detallesUsuarioRepository.saveAndFlush(detallesUsuario);

        int databaseSizeBeforeUpdate = detallesUsuarioRepository.findAll().size();

        // Update the detallesUsuario using partial update
        DetallesUsuario partialUpdatedDetallesUsuario = new DetallesUsuario();
        partialUpdatedDetallesUsuario.setId(detallesUsuario.getId());

        restDetallesUsuarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDetallesUsuario.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDetallesUsuario))
            )
            .andExpect(status().isOk());

        // Validate the DetallesUsuario in the database
        List<DetallesUsuario> detallesUsuarioList = detallesUsuarioRepository.findAll();
        assertThat(detallesUsuarioList).hasSize(databaseSizeBeforeUpdate);
        DetallesUsuario testDetallesUsuario = detallesUsuarioList.get(detallesUsuarioList.size() - 1);
        assertThat(testDetallesUsuario.getTelefono()).isEqualTo(DEFAULT_TELEFONO);
        assertThat(testDetallesUsuario.getIdentificacion()).isEqualTo(DEFAULT_IDENTIFICACION);
        assertThat(testDetallesUsuario.getCiudad()).isEqualTo(DEFAULT_CIUDAD);
    }

    @Test
    @Transactional
    void fullUpdateDetallesUsuarioWithPatch() throws Exception {
        // Initialize the database
        detallesUsuarioRepository.saveAndFlush(detallesUsuario);

        int databaseSizeBeforeUpdate = detallesUsuarioRepository.findAll().size();

        // Update the detallesUsuario using partial update
        DetallesUsuario partialUpdatedDetallesUsuario = new DetallesUsuario();
        partialUpdatedDetallesUsuario.setId(detallesUsuario.getId());

        partialUpdatedDetallesUsuario.telefono(UPDATED_TELEFONO).identificacion(UPDATED_IDENTIFICACION).ciudad(UPDATED_CIUDAD);

        restDetallesUsuarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDetallesUsuario.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDetallesUsuario))
            )
            .andExpect(status().isOk());

        // Validate the DetallesUsuario in the database
        List<DetallesUsuario> detallesUsuarioList = detallesUsuarioRepository.findAll();
        assertThat(detallesUsuarioList).hasSize(databaseSizeBeforeUpdate);
        DetallesUsuario testDetallesUsuario = detallesUsuarioList.get(detallesUsuarioList.size() - 1);
        assertThat(testDetallesUsuario.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testDetallesUsuario.getIdentificacion()).isEqualTo(UPDATED_IDENTIFICACION);
        assertThat(testDetallesUsuario.getCiudad()).isEqualTo(UPDATED_CIUDAD);
    }

    @Test
    @Transactional
    void patchNonExistingDetallesUsuario() throws Exception {
        int databaseSizeBeforeUpdate = detallesUsuarioRepository.findAll().size();
        detallesUsuario.setId(count.incrementAndGet());

        // Create the DetallesUsuario
        DetallesUsuarioDTO detallesUsuarioDTO = detallesUsuarioMapper.toDto(detallesUsuario);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDetallesUsuarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, detallesUsuarioDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(detallesUsuarioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DetallesUsuario in the database
        List<DetallesUsuario> detallesUsuarioList = detallesUsuarioRepository.findAll();
        assertThat(detallesUsuarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDetallesUsuario() throws Exception {
        int databaseSizeBeforeUpdate = detallesUsuarioRepository.findAll().size();
        detallesUsuario.setId(count.incrementAndGet());

        // Create the DetallesUsuario
        DetallesUsuarioDTO detallesUsuarioDTO = detallesUsuarioMapper.toDto(detallesUsuario);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDetallesUsuarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(detallesUsuarioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DetallesUsuario in the database
        List<DetallesUsuario> detallesUsuarioList = detallesUsuarioRepository.findAll();
        assertThat(detallesUsuarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDetallesUsuario() throws Exception {
        int databaseSizeBeforeUpdate = detallesUsuarioRepository.findAll().size();
        detallesUsuario.setId(count.incrementAndGet());

        // Create the DetallesUsuario
        DetallesUsuarioDTO detallesUsuarioDTO = detallesUsuarioMapper.toDto(detallesUsuario);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDetallesUsuarioMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(detallesUsuarioDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DetallesUsuario in the database
        List<DetallesUsuario> detallesUsuarioList = detallesUsuarioRepository.findAll();
        assertThat(detallesUsuarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDetallesUsuario() throws Exception {
        // Initialize the database
        detallesUsuarioRepository.saveAndFlush(detallesUsuario);

        int databaseSizeBeforeDelete = detallesUsuarioRepository.findAll().size();

        // Delete the detallesUsuario
        restDetallesUsuarioMockMvc
            .perform(delete(ENTITY_API_URL_ID, detallesUsuario.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DetallesUsuario> detallesUsuarioList = detallesUsuarioRepository.findAll();
        assertThat(detallesUsuarioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
