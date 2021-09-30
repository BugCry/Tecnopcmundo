package co.edu.usco.inventariotecnopcmundo.domain;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.usco.inventariotecnopcmundo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DetallesUsuarioTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DetallesUsuario.class);
        DetallesUsuario detallesUsuario1 = new DetallesUsuario();
        detallesUsuario1.setId(1L);
        DetallesUsuario detallesUsuario2 = new DetallesUsuario();
        detallesUsuario2.setId(detallesUsuario1.getId());
        assertThat(detallesUsuario1).isEqualTo(detallesUsuario2);
        detallesUsuario2.setId(2L);
        assertThat(detallesUsuario1).isNotEqualTo(detallesUsuario2);
        detallesUsuario1.setId(null);
        assertThat(detallesUsuario1).isNotEqualTo(detallesUsuario2);
    }
}
