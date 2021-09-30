package co.edu.usco.inventariotecnopcmundo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.edu.usco.inventariotecnopcmundo.IntegrationTest;
import co.edu.usco.inventariotecnopcmundo.domain.Publicacion;
import co.edu.usco.inventariotecnopcmundo.repository.PublicacionRepository;
import co.edu.usco.inventariotecnopcmundo.service.dto.PublicacionDTO;
import co.edu.usco.inventariotecnopcmundo.service.mapper.PublicacionMapper;
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
 * Integration tests for the {@link PublicacionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PublicacionResourceIT {

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGEN = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGEN = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGEN_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEN_CONTENT_TYPE = "image/png";

    private static final Instant DEFAULT_CREATE_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATE_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/publicacions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PublicacionRepository publicacionRepository;

    @Autowired
    private PublicacionMapper publicacionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPublicacionMockMvc;

    private Publicacion publicacion;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Publicacion createEntity(EntityManager em) {
        Publicacion publicacion = new Publicacion()
            .titulo(DEFAULT_TITULO)
            .descripcion(DEFAULT_DESCRIPCION)
            .imagen(DEFAULT_IMAGEN)
            .imagenContentType(DEFAULT_IMAGEN_CONTENT_TYPE)
            .createAt(DEFAULT_CREATE_AT);
        return publicacion;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Publicacion createUpdatedEntity(EntityManager em) {
        Publicacion publicacion = new Publicacion()
            .titulo(UPDATED_TITULO)
            .descripcion(UPDATED_DESCRIPCION)
            .imagen(UPDATED_IMAGEN)
            .imagenContentType(UPDATED_IMAGEN_CONTENT_TYPE)
            .createAt(UPDATED_CREATE_AT);
        return publicacion;
    }

    @BeforeEach
    public void initTest() {
        publicacion = createEntity(em);
    }

    @Test
    @Transactional
    void createPublicacion() throws Exception {
        int databaseSizeBeforeCreate = publicacionRepository.findAll().size();
        // Create the Publicacion
        PublicacionDTO publicacionDTO = publicacionMapper.toDto(publicacion);
        restPublicacionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(publicacionDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Publicacion in the database
        List<Publicacion> publicacionList = publicacionRepository.findAll();
        assertThat(publicacionList).hasSize(databaseSizeBeforeCreate + 1);
        Publicacion testPublicacion = publicacionList.get(publicacionList.size() - 1);
        assertThat(testPublicacion.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testPublicacion.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testPublicacion.getImagen()).isEqualTo(DEFAULT_IMAGEN);
        assertThat(testPublicacion.getImagenContentType()).isEqualTo(DEFAULT_IMAGEN_CONTENT_TYPE);
        assertThat(testPublicacion.getCreateAt()).isEqualTo(DEFAULT_CREATE_AT);
    }

    @Test
    @Transactional
    void createPublicacionWithExistingId() throws Exception {
        // Create the Publicacion with an existing ID
        publicacion.setId(1L);
        PublicacionDTO publicacionDTO = publicacionMapper.toDto(publicacion);

        int databaseSizeBeforeCreate = publicacionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPublicacionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(publicacionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Publicacion in the database
        List<Publicacion> publicacionList = publicacionRepository.findAll();
        assertThat(publicacionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPublicacions() throws Exception {
        // Initialize the database
        publicacionRepository.saveAndFlush(publicacion);

        // Get all the publicacionList
        restPublicacionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(publicacion.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].imagenContentType").value(hasItem(DEFAULT_IMAGEN_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN))))
            .andExpect(jsonPath("$.[*].createAt").value(hasItem(DEFAULT_CREATE_AT.toString())));
    }

    @Test
    @Transactional
    void getPublicacion() throws Exception {
        // Initialize the database
        publicacionRepository.saveAndFlush(publicacion);

        // Get the publicacion
        restPublicacionMockMvc
            .perform(get(ENTITY_API_URL_ID, publicacion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(publicacion.getId().intValue()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.imagenContentType").value(DEFAULT_IMAGEN_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagen").value(Base64Utils.encodeToString(DEFAULT_IMAGEN)))
            .andExpect(jsonPath("$.createAt").value(DEFAULT_CREATE_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingPublicacion() throws Exception {
        // Get the publicacion
        restPublicacionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPublicacion() throws Exception {
        // Initialize the database
        publicacionRepository.saveAndFlush(publicacion);

        int databaseSizeBeforeUpdate = publicacionRepository.findAll().size();

        // Update the publicacion
        Publicacion updatedPublicacion = publicacionRepository.findById(publicacion.getId()).get();
        // Disconnect from session so that the updates on updatedPublicacion are not directly saved in db
        em.detach(updatedPublicacion);
        updatedPublicacion
            .titulo(UPDATED_TITULO)
            .descripcion(UPDATED_DESCRIPCION)
            .imagen(UPDATED_IMAGEN)
            .imagenContentType(UPDATED_IMAGEN_CONTENT_TYPE)
            .createAt(UPDATED_CREATE_AT);
        PublicacionDTO publicacionDTO = publicacionMapper.toDto(updatedPublicacion);

        restPublicacionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, publicacionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(publicacionDTO))
            )
            .andExpect(status().isOk());

        // Validate the Publicacion in the database
        List<Publicacion> publicacionList = publicacionRepository.findAll();
        assertThat(publicacionList).hasSize(databaseSizeBeforeUpdate);
        Publicacion testPublicacion = publicacionList.get(publicacionList.size() - 1);
        assertThat(testPublicacion.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testPublicacion.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testPublicacion.getImagen()).isEqualTo(UPDATED_IMAGEN);
        assertThat(testPublicacion.getImagenContentType()).isEqualTo(UPDATED_IMAGEN_CONTENT_TYPE);
        assertThat(testPublicacion.getCreateAt()).isEqualTo(UPDATED_CREATE_AT);
    }

    @Test
    @Transactional
    void putNonExistingPublicacion() throws Exception {
        int databaseSizeBeforeUpdate = publicacionRepository.findAll().size();
        publicacion.setId(count.incrementAndGet());

        // Create the Publicacion
        PublicacionDTO publicacionDTO = publicacionMapper.toDto(publicacion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPublicacionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, publicacionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(publicacionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Publicacion in the database
        List<Publicacion> publicacionList = publicacionRepository.findAll();
        assertThat(publicacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPublicacion() throws Exception {
        int databaseSizeBeforeUpdate = publicacionRepository.findAll().size();
        publicacion.setId(count.incrementAndGet());

        // Create the Publicacion
        PublicacionDTO publicacionDTO = publicacionMapper.toDto(publicacion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPublicacionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(publicacionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Publicacion in the database
        List<Publicacion> publicacionList = publicacionRepository.findAll();
        assertThat(publicacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPublicacion() throws Exception {
        int databaseSizeBeforeUpdate = publicacionRepository.findAll().size();
        publicacion.setId(count.incrementAndGet());

        // Create the Publicacion
        PublicacionDTO publicacionDTO = publicacionMapper.toDto(publicacion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPublicacionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(publicacionDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Publicacion in the database
        List<Publicacion> publicacionList = publicacionRepository.findAll();
        assertThat(publicacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePublicacionWithPatch() throws Exception {
        // Initialize the database
        publicacionRepository.saveAndFlush(publicacion);

        int databaseSizeBeforeUpdate = publicacionRepository.findAll().size();

        // Update the publicacion using partial update
        Publicacion partialUpdatedPublicacion = new Publicacion();
        partialUpdatedPublicacion.setId(publicacion.getId());

        partialUpdatedPublicacion.createAt(UPDATED_CREATE_AT);

        restPublicacionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPublicacion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPublicacion))
            )
            .andExpect(status().isOk());

        // Validate the Publicacion in the database
        List<Publicacion> publicacionList = publicacionRepository.findAll();
        assertThat(publicacionList).hasSize(databaseSizeBeforeUpdate);
        Publicacion testPublicacion = publicacionList.get(publicacionList.size() - 1);
        assertThat(testPublicacion.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testPublicacion.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testPublicacion.getImagen()).isEqualTo(DEFAULT_IMAGEN);
        assertThat(testPublicacion.getImagenContentType()).isEqualTo(DEFAULT_IMAGEN_CONTENT_TYPE);
        assertThat(testPublicacion.getCreateAt()).isEqualTo(UPDATED_CREATE_AT);
    }

    @Test
    @Transactional
    void fullUpdatePublicacionWithPatch() throws Exception {
        // Initialize the database
        publicacionRepository.saveAndFlush(publicacion);

        int databaseSizeBeforeUpdate = publicacionRepository.findAll().size();

        // Update the publicacion using partial update
        Publicacion partialUpdatedPublicacion = new Publicacion();
        partialUpdatedPublicacion.setId(publicacion.getId());

        partialUpdatedPublicacion
            .titulo(UPDATED_TITULO)
            .descripcion(UPDATED_DESCRIPCION)
            .imagen(UPDATED_IMAGEN)
            .imagenContentType(UPDATED_IMAGEN_CONTENT_TYPE)
            .createAt(UPDATED_CREATE_AT);

        restPublicacionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPublicacion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPublicacion))
            )
            .andExpect(status().isOk());

        // Validate the Publicacion in the database
        List<Publicacion> publicacionList = publicacionRepository.findAll();
        assertThat(publicacionList).hasSize(databaseSizeBeforeUpdate);
        Publicacion testPublicacion = publicacionList.get(publicacionList.size() - 1);
        assertThat(testPublicacion.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testPublicacion.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testPublicacion.getImagen()).isEqualTo(UPDATED_IMAGEN);
        assertThat(testPublicacion.getImagenContentType()).isEqualTo(UPDATED_IMAGEN_CONTENT_TYPE);
        assertThat(testPublicacion.getCreateAt()).isEqualTo(UPDATED_CREATE_AT);
    }

    @Test
    @Transactional
    void patchNonExistingPublicacion() throws Exception {
        int databaseSizeBeforeUpdate = publicacionRepository.findAll().size();
        publicacion.setId(count.incrementAndGet());

        // Create the Publicacion
        PublicacionDTO publicacionDTO = publicacionMapper.toDto(publicacion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPublicacionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, publicacionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(publicacionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Publicacion in the database
        List<Publicacion> publicacionList = publicacionRepository.findAll();
        assertThat(publicacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPublicacion() throws Exception {
        int databaseSizeBeforeUpdate = publicacionRepository.findAll().size();
        publicacion.setId(count.incrementAndGet());

        // Create the Publicacion
        PublicacionDTO publicacionDTO = publicacionMapper.toDto(publicacion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPublicacionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(publicacionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Publicacion in the database
        List<Publicacion> publicacionList = publicacionRepository.findAll();
        assertThat(publicacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPublicacion() throws Exception {
        int databaseSizeBeforeUpdate = publicacionRepository.findAll().size();
        publicacion.setId(count.incrementAndGet());

        // Create the Publicacion
        PublicacionDTO publicacionDTO = publicacionMapper.toDto(publicacion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPublicacionMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(publicacionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Publicacion in the database
        List<Publicacion> publicacionList = publicacionRepository.findAll();
        assertThat(publicacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePublicacion() throws Exception {
        // Initialize the database
        publicacionRepository.saveAndFlush(publicacion);

        int databaseSizeBeforeDelete = publicacionRepository.findAll().size();

        // Delete the publicacion
        restPublicacionMockMvc
            .perform(delete(ENTITY_API_URL_ID, publicacion.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Publicacion> publicacionList = publicacionRepository.findAll();
        assertThat(publicacionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
