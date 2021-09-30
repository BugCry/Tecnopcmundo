package co.edu.usco.inventariotecnopcmundo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.usco.inventariotecnopcmundo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DetallesUsuarioDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DetallesUsuarioDTO.class);
        DetallesUsuarioDTO detallesUsuarioDTO1 = new DetallesUsuarioDTO();
        detallesUsuarioDTO1.setId(1L);
        DetallesUsuarioDTO detallesUsuarioDTO2 = new DetallesUsuarioDTO();
        assertThat(detallesUsuarioDTO1).isNotEqualTo(detallesUsuarioDTO2);
        detallesUsuarioDTO2.setId(detallesUsuarioDTO1.getId());
        assertThat(detallesUsuarioDTO1).isEqualTo(detallesUsuarioDTO2);
        detallesUsuarioDTO2.setId(2L);
        assertThat(detallesUsuarioDTO1).isNotEqualTo(detallesUsuarioDTO2);
        detallesUsuarioDTO1.setId(null);
        assertThat(detallesUsuarioDTO1).isNotEqualTo(detallesUsuarioDTO2);
    }
}
