package co.edu.usco.inventariotecnopcmundo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.usco.inventariotecnopcmundo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CompraDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompraDTO.class);
        CompraDTO compraDTO1 = new CompraDTO();
        compraDTO1.setId(1L);
        CompraDTO compraDTO2 = new CompraDTO();
        assertThat(compraDTO1).isNotEqualTo(compraDTO2);
        compraDTO2.setId(compraDTO1.getId());
        assertThat(compraDTO1).isEqualTo(compraDTO2);
        compraDTO2.setId(2L);
        assertThat(compraDTO1).isNotEqualTo(compraDTO2);
        compraDTO1.setId(null);
        assertThat(compraDTO1).isNotEqualTo(compraDTO2);
    }
}
