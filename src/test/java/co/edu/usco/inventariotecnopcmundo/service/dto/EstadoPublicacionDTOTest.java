package co.edu.usco.inventariotecnopcmundo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.usco.inventariotecnopcmundo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EstadoPublicacionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EstadoPublicacionDTO.class);
        EstadoPublicacionDTO estadoPublicacionDTO1 = new EstadoPublicacionDTO();
        estadoPublicacionDTO1.setId(1L);
        EstadoPublicacionDTO estadoPublicacionDTO2 = new EstadoPublicacionDTO();
        assertThat(estadoPublicacionDTO1).isNotEqualTo(estadoPublicacionDTO2);
        estadoPublicacionDTO2.setId(estadoPublicacionDTO1.getId());
        assertThat(estadoPublicacionDTO1).isEqualTo(estadoPublicacionDTO2);
        estadoPublicacionDTO2.setId(2L);
        assertThat(estadoPublicacionDTO1).isNotEqualTo(estadoPublicacionDTO2);
        estadoPublicacionDTO1.setId(null);
        assertThat(estadoPublicacionDTO1).isNotEqualTo(estadoPublicacionDTO2);
    }
}
