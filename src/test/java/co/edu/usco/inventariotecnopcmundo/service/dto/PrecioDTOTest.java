package co.edu.usco.inventariotecnopcmundo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.usco.inventariotecnopcmundo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PrecioDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrecioDTO.class);
        PrecioDTO precioDTO1 = new PrecioDTO();
        precioDTO1.setId(1L);
        PrecioDTO precioDTO2 = new PrecioDTO();
        assertThat(precioDTO1).isNotEqualTo(precioDTO2);
        precioDTO2.setId(precioDTO1.getId());
        assertThat(precioDTO1).isEqualTo(precioDTO2);
        precioDTO2.setId(2L);
        assertThat(precioDTO1).isNotEqualTo(precioDTO2);
        precioDTO1.setId(null);
        assertThat(precioDTO1).isNotEqualTo(precioDTO2);
    }
}
