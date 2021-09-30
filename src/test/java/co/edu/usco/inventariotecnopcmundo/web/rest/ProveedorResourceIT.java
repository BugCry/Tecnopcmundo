package co.edu.usco.inventariotecnopcmundo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.edu.usco.inventariotecnopcmundo.IntegrationTest;
import co.edu.usco.inventariotecnopcmundo.domain.Proveedor;
import co.edu.usco.inventariotecnopcmundo.repository.ProveedorRepository;
import co.edu.usco.inventariotecnopcmundo.service.dto.ProveedorDTO;
import co.edu.usco.inventariotecnopcmundo.service.mapper.ProveedorMapper;
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
 * Integration tests for the {@link ProveedorResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProveedorResourceIT {

    private static final String DEFAULT_NIT = "AAAAAAAAAA";
    private static final String UPDATED_NIT = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACTO = "AAAAAAAAAA";
    private static final String UPDATED_CONTACTO = "BBBBBBBBBB";

    private static final String DEFAULT_DIRECCION = "AAAAAAAAAA";
    private static final String UPDATED_DIRECCION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/proveedors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Autowired
    private ProveedorMapper proveedorMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProveedorMockMvc;

    private Proveedor proveedor;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Proveedor createEntity(EntityManager em) {
        Proveedor proveedor = new Proveedor()
            .nit(DEFAULT_NIT)
            .nombre(DEFAULT_NOMBRE)
            .contacto(DEFAULT_CONTACTO)
            .direccion(DEFAULT_DIRECCION);
        return proveedor;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Proveedor createUpdatedEntity(EntityManager em) {
        Proveedor proveedor = new Proveedor()
            .nit(UPDATED_NIT)
            .nombre(UPDATED_NOMBRE)
            .contacto(UPDATED_CONTACTO)
            .direccion(UPDATED_DIRECCION);
        return proveedor;
    }

    @BeforeEach
    public void initTest() {
        proveedor = createEntity(em);
    }

    @Test
    @Transactional
    void createProveedor() throws Exception {
        int databaseSizeBeforeCreate = proveedorRepository.findAll().size();
        // Create the Proveedor
        ProveedorDTO proveedorDTO = proveedorMapper.toDto(proveedor);
        restProveedorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(proveedorDTO)))
            .andExpect(status().isCreated());

        // Validate the Proveedor in the database
        List<Proveedor> proveedorList = proveedorRepository.findAll();
        assertThat(proveedorList).hasSize(databaseSizeBeforeCreate + 1);
        Proveedor testProveedor = proveedorList.get(proveedorList.size() - 1);
        assertThat(testProveedor.getNit()).isEqualTo(DEFAULT_NIT);
        assertThat(testProveedor.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testProveedor.getContacto()).isEqualTo(DEFAULT_CONTACTO);
        assertThat(testProveedor.getDireccion()).isEqualTo(DEFAULT_DIRECCION);
    }

    @Test
    @Transactional
    void createProveedorWithExistingId() throws Exception {
        // Create the Proveedor with an existing ID
        proveedor.setId(1L);
        ProveedorDTO proveedorDTO = proveedorMapper.toDto(proveedor);

        int databaseSizeBeforeCreate = proveedorRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProveedorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(proveedorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Proveedor in the database
        List<Proveedor> proveedorList = proveedorRepository.findAll();
        assertThat(proveedorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProveedors() throws Exception {
        // Initialize the database
        proveedorRepository.saveAndFlush(proveedor);

        // Get all the proveedorList
        restProveedorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(proveedor.getId().intValue())))
            .andExpect(jsonPath("$.[*].nit").value(hasItem(DEFAULT_NIT)))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].contacto").value(hasItem(DEFAULT_CONTACTO)))
            .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION)));
    }

    @Test
    @Transactional
    void getProveedor() throws Exception {
        // Initialize the database
        proveedorRepository.saveAndFlush(proveedor);

        // Get the proveedor
        restProveedorMockMvc
            .perform(get(ENTITY_API_URL_ID, proveedor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(proveedor.getId().intValue()))
            .andExpect(jsonPath("$.nit").value(DEFAULT_NIT))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.contacto").value(DEFAULT_CONTACTO))
            .andExpect(jsonPath("$.direccion").value(DEFAULT_DIRECCION));
    }

    @Test
    @Transactional
    void getNonExistingProveedor() throws Exception {
        // Get the proveedor
        restProveedorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProveedor() throws Exception {
        // Initialize the database
        proveedorRepository.saveAndFlush(proveedor);

        int databaseSizeBeforeUpdate = proveedorRepository.findAll().size();

        // Update the proveedor
        Proveedor updatedProveedor = proveedorRepository.findById(proveedor.getId()).get();
        // Disconnect from session so that the updates on updatedProveedor are not directly saved in db
        em.detach(updatedProveedor);
        updatedProveedor.nit(UPDATED_NIT).nombre(UPDATED_NOMBRE).contacto(UPDATED_CONTACTO).direccion(UPDATED_DIRECCION);
        ProveedorDTO proveedorDTO = proveedorMapper.toDto(updatedProveedor);

        restProveedorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, proveedorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(proveedorDTO))
            )
            .andExpect(status().isOk());

        // Validate the Proveedor in the database
        List<Proveedor> proveedorList = proveedorRepository.findAll();
        assertThat(proveedorList).hasSize(databaseSizeBeforeUpdate);
        Proveedor testProveedor = proveedorList.get(proveedorList.size() - 1);
        assertThat(testProveedor.getNit()).isEqualTo(UPDATED_NIT);
        assertThat(testProveedor.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testProveedor.getContacto()).isEqualTo(UPDATED_CONTACTO);
        assertThat(testProveedor.getDireccion()).isEqualTo(UPDATED_DIRECCION);
    }

    @Test
    @Transactional
    void putNonExistingProveedor() throws Exception {
        int databaseSizeBeforeUpdate = proveedorRepository.findAll().size();
        proveedor.setId(count.incrementAndGet());

        // Create the Proveedor
        ProveedorDTO proveedorDTO = proveedorMapper.toDto(proveedor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProveedorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, proveedorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(proveedorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Proveedor in the database
        List<Proveedor> proveedorList = proveedorRepository.findAll();
        assertThat(proveedorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProveedor() throws Exception {
        int databaseSizeBeforeUpdate = proveedorRepository.findAll().size();
        proveedor.setId(count.incrementAndGet());

        // Create the Proveedor
        ProveedorDTO proveedorDTO = proveedorMapper.toDto(proveedor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProveedorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(proveedorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Proveedor in the database
        List<Proveedor> proveedorList = proveedorRepository.findAll();
        assertThat(proveedorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProveedor() throws Exception {
        int databaseSizeBeforeUpdate = proveedorRepository.findAll().size();
        proveedor.setId(count.incrementAndGet());

        // Create the Proveedor
        ProveedorDTO proveedorDTO = proveedorMapper.toDto(proveedor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProveedorMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(proveedorDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Proveedor in the database
        List<Proveedor> proveedorList = proveedorRepository.findAll();
        assertThat(proveedorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProveedorWithPatch() throws Exception {
        // Initialize the database
        proveedorRepository.saveAndFlush(proveedor);

        int databaseSizeBeforeUpdate = proveedorRepository.findAll().size();

        // Update the proveedor using partial update
        Proveedor partialUpdatedProveedor = new Proveedor();
        partialUpdatedProveedor.setId(proveedor.getId());

        partialUpdatedProveedor.nit(UPDATED_NIT).nombre(UPDATED_NOMBRE);

        restProveedorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProveedor.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProveedor))
            )
            .andExpect(status().isOk());

        // Validate the Proveedor in the database
        List<Proveedor> proveedorList = proveedorRepository.findAll();
        assertThat(proveedorList).hasSize(databaseSizeBeforeUpdate);
        Proveedor testProveedor = proveedorList.get(proveedorList.size() - 1);
        assertThat(testProveedor.getNit()).isEqualTo(UPDATED_NIT);
        assertThat(testProveedor.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testProveedor.getContacto()).isEqualTo(DEFAULT_CONTACTO);
        assertThat(testProveedor.getDireccion()).isEqualTo(DEFAULT_DIRECCION);
    }

    @Test
    @Transactional
    void fullUpdateProveedorWithPatch() throws Exception {
        // Initialize the database
        proveedorRepository.saveAndFlush(proveedor);

        int databaseSizeBeforeUpdate = proveedorRepository.findAll().size();

        // Update the proveedor using partial update
        Proveedor partialUpdatedProveedor = new Proveedor();
        partialUpdatedProveedor.setId(proveedor.getId());

        partialUpdatedProveedor.nit(UPDATED_NIT).nombre(UPDATED_NOMBRE).contacto(UPDATED_CONTACTO).direccion(UPDATED_DIRECCION);

        restProveedorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProveedor.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProveedor))
            )
            .andExpect(status().isOk());

        // Validate the Proveedor in the database
        List<Proveedor> proveedorList = proveedorRepository.findAll();
        assertThat(proveedorList).hasSize(databaseSizeBeforeUpdate);
        Proveedor testProveedor = proveedorList.get(proveedorList.size() - 1);
        assertThat(testProveedor.getNit()).isEqualTo(UPDATED_NIT);
        assertThat(testProveedor.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testProveedor.getContacto()).isEqualTo(UPDATED_CONTACTO);
        assertThat(testProveedor.getDireccion()).isEqualTo(UPDATED_DIRECCION);
    }

    @Test
    @Transactional
    void patchNonExistingProveedor() throws Exception {
        int databaseSizeBeforeUpdate = proveedorRepository.findAll().size();
        proveedor.setId(count.incrementAndGet());

        // Create the Proveedor
        ProveedorDTO proveedorDTO = proveedorMapper.toDto(proveedor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProveedorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, proveedorDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(proveedorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Proveedor in the database
        List<Proveedor> proveedorList = proveedorRepository.findAll();
        assertThat(proveedorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProveedor() throws Exception {
        int databaseSizeBeforeUpdate = proveedorRepository.findAll().size();
        proveedor.setId(count.incrementAndGet());

        // Create the Proveedor
        ProveedorDTO proveedorDTO = proveedorMapper.toDto(proveedor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProveedorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(proveedorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Proveedor in the database
        List<Proveedor> proveedorList = proveedorRepository.findAll();
        assertThat(proveedorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProveedor() throws Exception {
        int databaseSizeBeforeUpdate = proveedorRepository.findAll().size();
        proveedor.setId(count.incrementAndGet());

        // Create the Proveedor
        ProveedorDTO proveedorDTO = proveedorMapper.toDto(proveedor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProveedorMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(proveedorDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Proveedor in the database
        List<Proveedor> proveedorList = proveedorRepository.findAll();
        assertThat(proveedorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProveedor() throws Exception {
        // Initialize the database
        proveedorRepository.saveAndFlush(proveedor);

        int databaseSizeBeforeDelete = proveedorRepository.findAll().size();

        // Delete the proveedor
        restProveedorMockMvc
            .perform(delete(ENTITY_API_URL_ID, proveedor.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Proveedor> proveedorList = proveedorRepository.findAll();
        assertThat(proveedorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
