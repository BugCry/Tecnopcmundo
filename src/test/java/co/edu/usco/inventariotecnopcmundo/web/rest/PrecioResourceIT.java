package co.edu.usco.inventariotecnopcmundo.web.rest;

import static co.edu.usco.inventariotecnopcmundo.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.edu.usco.inventariotecnopcmundo.IntegrationTest;
import co.edu.usco.inventariotecnopcmundo.domain.Precio;
import co.edu.usco.inventariotecnopcmundo.repository.PrecioRepository;
import co.edu.usco.inventariotecnopcmundo.service.dto.PrecioDTO;
import co.edu.usco.inventariotecnopcmundo.service.mapper.PrecioMapper;
import java.math.BigDecimal;
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
 * Integration tests for the {@link PrecioResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PrecioResourceIT {

    private static final BigDecimal DEFAULT_PRECIO_COMPRA = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRECIO_COMPRA = new BigDecimal(2);

    private static final BigDecimal DEFAULT_PRECIO_VENTA = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRECIO_VENTA = new BigDecimal(2);

    private static final Integer DEFAULT_DESCUENTO = 1;
    private static final Integer UPDATED_DESCUENTO = 2;

    private static final Integer DEFAULT_PROFIT = 1;
    private static final Integer UPDATED_PROFIT = 2;

    private static final String ENTITY_API_URL = "/api/precios";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PrecioRepository precioRepository;

    @Autowired
    private PrecioMapper precioMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPrecioMockMvc;

    private Precio precio;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Precio createEntity(EntityManager em) {
        Precio precio = new Precio()
            .precioCompra(DEFAULT_PRECIO_COMPRA)
            .precioVenta(DEFAULT_PRECIO_VENTA)
            .descuento(DEFAULT_DESCUENTO)
            .profit(DEFAULT_PROFIT);
        return precio;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Precio createUpdatedEntity(EntityManager em) {
        Precio precio = new Precio()
            .precioCompra(UPDATED_PRECIO_COMPRA)
            .precioVenta(UPDATED_PRECIO_VENTA)
            .descuento(UPDATED_DESCUENTO)
            .profit(UPDATED_PROFIT);
        return precio;
    }

    @BeforeEach
    public void initTest() {
        precio = createEntity(em);
    }

    @Test
    @Transactional
    void createPrecio() throws Exception {
        int databaseSizeBeforeCreate = precioRepository.findAll().size();
        // Create the Precio
        PrecioDTO precioDTO = precioMapper.toDto(precio);
        restPrecioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(precioDTO)))
            .andExpect(status().isCreated());

        // Validate the Precio in the database
        List<Precio> precioList = precioRepository.findAll();
        assertThat(precioList).hasSize(databaseSizeBeforeCreate + 1);
        Precio testPrecio = precioList.get(precioList.size() - 1);
        assertThat(testPrecio.getPrecioCompra()).isEqualByComparingTo(DEFAULT_PRECIO_COMPRA);
        assertThat(testPrecio.getPrecioVenta()).isEqualByComparingTo(DEFAULT_PRECIO_VENTA);
        assertThat(testPrecio.getDescuento()).isEqualTo(DEFAULT_DESCUENTO);
        assertThat(testPrecio.getProfit()).isEqualTo(DEFAULT_PROFIT);
    }

    @Test
    @Transactional
    void createPrecioWithExistingId() throws Exception {
        // Create the Precio with an existing ID
        precio.setId(1L);
        PrecioDTO precioDTO = precioMapper.toDto(precio);

        int databaseSizeBeforeCreate = precioRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrecioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(precioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Precio in the database
        List<Precio> precioList = precioRepository.findAll();
        assertThat(precioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPrecios() throws Exception {
        // Initialize the database
        precioRepository.saveAndFlush(precio);

        // Get all the precioList
        restPrecioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(precio.getId().intValue())))
            .andExpect(jsonPath("$.[*].precioCompra").value(hasItem(sameNumber(DEFAULT_PRECIO_COMPRA))))
            .andExpect(jsonPath("$.[*].precioVenta").value(hasItem(sameNumber(DEFAULT_PRECIO_VENTA))))
            .andExpect(jsonPath("$.[*].descuento").value(hasItem(DEFAULT_DESCUENTO)))
            .andExpect(jsonPath("$.[*].profit").value(hasItem(DEFAULT_PROFIT)));
    }

    @Test
    @Transactional
    void getPrecio() throws Exception {
        // Initialize the database
        precioRepository.saveAndFlush(precio);

        // Get the precio
        restPrecioMockMvc
            .perform(get(ENTITY_API_URL_ID, precio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(precio.getId().intValue()))
            .andExpect(jsonPath("$.precioCompra").value(sameNumber(DEFAULT_PRECIO_COMPRA)))
            .andExpect(jsonPath("$.precioVenta").value(sameNumber(DEFAULT_PRECIO_VENTA)))
            .andExpect(jsonPath("$.descuento").value(DEFAULT_DESCUENTO))
            .andExpect(jsonPath("$.profit").value(DEFAULT_PROFIT));
    }

    @Test
    @Transactional
    void getNonExistingPrecio() throws Exception {
        // Get the precio
        restPrecioMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPrecio() throws Exception {
        // Initialize the database
        precioRepository.saveAndFlush(precio);

        int databaseSizeBeforeUpdate = precioRepository.findAll().size();

        // Update the precio
        Precio updatedPrecio = precioRepository.findById(precio.getId()).get();
        // Disconnect from session so that the updates on updatedPrecio are not directly saved in db
        em.detach(updatedPrecio);
        updatedPrecio
            .precioCompra(UPDATED_PRECIO_COMPRA)
            .precioVenta(UPDATED_PRECIO_VENTA)
            .descuento(UPDATED_DESCUENTO)
            .profit(UPDATED_PROFIT);
        PrecioDTO precioDTO = precioMapper.toDto(updatedPrecio);

        restPrecioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, precioDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(precioDTO))
            )
            .andExpect(status().isOk());

        // Validate the Precio in the database
        List<Precio> precioList = precioRepository.findAll();
        assertThat(precioList).hasSize(databaseSizeBeforeUpdate);
        Precio testPrecio = precioList.get(precioList.size() - 1);
        assertThat(testPrecio.getPrecioCompra()).isEqualTo(UPDATED_PRECIO_COMPRA);
        assertThat(testPrecio.getPrecioVenta()).isEqualTo(UPDATED_PRECIO_VENTA);
        assertThat(testPrecio.getDescuento()).isEqualTo(UPDATED_DESCUENTO);
        assertThat(testPrecio.getProfit()).isEqualTo(UPDATED_PROFIT);
    }

    @Test
    @Transactional
    void putNonExistingPrecio() throws Exception {
        int databaseSizeBeforeUpdate = precioRepository.findAll().size();
        precio.setId(count.incrementAndGet());

        // Create the Precio
        PrecioDTO precioDTO = precioMapper.toDto(precio);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrecioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, precioDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(precioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Precio in the database
        List<Precio> precioList = precioRepository.findAll();
        assertThat(precioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPrecio() throws Exception {
        int databaseSizeBeforeUpdate = precioRepository.findAll().size();
        precio.setId(count.incrementAndGet());

        // Create the Precio
        PrecioDTO precioDTO = precioMapper.toDto(precio);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrecioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(precioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Precio in the database
        List<Precio> precioList = precioRepository.findAll();
        assertThat(precioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPrecio() throws Exception {
        int databaseSizeBeforeUpdate = precioRepository.findAll().size();
        precio.setId(count.incrementAndGet());

        // Create the Precio
        PrecioDTO precioDTO = precioMapper.toDto(precio);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrecioMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(precioDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Precio in the database
        List<Precio> precioList = precioRepository.findAll();
        assertThat(precioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePrecioWithPatch() throws Exception {
        // Initialize the database
        precioRepository.saveAndFlush(precio);

        int databaseSizeBeforeUpdate = precioRepository.findAll().size();

        // Update the precio using partial update
        Precio partialUpdatedPrecio = new Precio();
        partialUpdatedPrecio.setId(precio.getId());

        partialUpdatedPrecio.precioCompra(UPDATED_PRECIO_COMPRA);

        restPrecioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPrecio.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPrecio))
            )
            .andExpect(status().isOk());

        // Validate the Precio in the database
        List<Precio> precioList = precioRepository.findAll();
        assertThat(precioList).hasSize(databaseSizeBeforeUpdate);
        Precio testPrecio = precioList.get(precioList.size() - 1);
        assertThat(testPrecio.getPrecioCompra()).isEqualByComparingTo(UPDATED_PRECIO_COMPRA);
        assertThat(testPrecio.getPrecioVenta()).isEqualByComparingTo(DEFAULT_PRECIO_VENTA);
        assertThat(testPrecio.getDescuento()).isEqualTo(DEFAULT_DESCUENTO);
        assertThat(testPrecio.getProfit()).isEqualTo(DEFAULT_PROFIT);
    }

    @Test
    @Transactional
    void fullUpdatePrecioWithPatch() throws Exception {
        // Initialize the database
        precioRepository.saveAndFlush(precio);

        int databaseSizeBeforeUpdate = precioRepository.findAll().size();

        // Update the precio using partial update
        Precio partialUpdatedPrecio = new Precio();
        partialUpdatedPrecio.setId(precio.getId());

        partialUpdatedPrecio
            .precioCompra(UPDATED_PRECIO_COMPRA)
            .precioVenta(UPDATED_PRECIO_VENTA)
            .descuento(UPDATED_DESCUENTO)
            .profit(UPDATED_PROFIT);

        restPrecioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPrecio.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPrecio))
            )
            .andExpect(status().isOk());

        // Validate the Precio in the database
        List<Precio> precioList = precioRepository.findAll();
        assertThat(precioList).hasSize(databaseSizeBeforeUpdate);
        Precio testPrecio = precioList.get(precioList.size() - 1);
        assertThat(testPrecio.getPrecioCompra()).isEqualByComparingTo(UPDATED_PRECIO_COMPRA);
        assertThat(testPrecio.getPrecioVenta()).isEqualByComparingTo(UPDATED_PRECIO_VENTA);
        assertThat(testPrecio.getDescuento()).isEqualTo(UPDATED_DESCUENTO);
        assertThat(testPrecio.getProfit()).isEqualTo(UPDATED_PROFIT);
    }

    @Test
    @Transactional
    void patchNonExistingPrecio() throws Exception {
        int databaseSizeBeforeUpdate = precioRepository.findAll().size();
        precio.setId(count.incrementAndGet());

        // Create the Precio
        PrecioDTO precioDTO = precioMapper.toDto(precio);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrecioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, precioDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(precioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Precio in the database
        List<Precio> precioList = precioRepository.findAll();
        assertThat(precioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPrecio() throws Exception {
        int databaseSizeBeforeUpdate = precioRepository.findAll().size();
        precio.setId(count.incrementAndGet());

        // Create the Precio
        PrecioDTO precioDTO = precioMapper.toDto(precio);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrecioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(precioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Precio in the database
        List<Precio> precioList = precioRepository.findAll();
        assertThat(precioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPrecio() throws Exception {
        int databaseSizeBeforeUpdate = precioRepository.findAll().size();
        precio.setId(count.incrementAndGet());

        // Create the Precio
        PrecioDTO precioDTO = precioMapper.toDto(precio);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrecioMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(precioDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Precio in the database
        List<Precio> precioList = precioRepository.findAll();
        assertThat(precioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePrecio() throws Exception {
        // Initialize the database
        precioRepository.saveAndFlush(precio);

        int databaseSizeBeforeDelete = precioRepository.findAll().size();

        // Delete the precio
        restPrecioMockMvc
            .perform(delete(ENTITY_API_URL_ID, precio.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Precio> precioList = precioRepository.findAll();
        assertThat(precioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
