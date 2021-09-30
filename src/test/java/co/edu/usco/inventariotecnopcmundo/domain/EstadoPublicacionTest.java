package co.edu.usco.inventariotecnopcmundo.domain;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.usco.inventariotecnopcmundo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EstadoPublicacionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EstadoPublicacion.class);
        EstadoPublicacion estadoPublicacion1 = new EstadoPublicacion();
        estadoPublicacion1.setId(1L);
        EstadoPublicacion estadoPublicacion2 = new EstadoPublicacion();
        estadoPublicacion2.setId(estadoPublicacion1.getId());
        assertThat(estadoPublicacion1).isEqualTo(estadoPublicacion2);
        estadoPublicacion2.setId(2L);
        assertThat(estadoPublicacion1).isNotEqualTo(estadoPublicacion2);
        estadoPublicacion1.setId(null);
        assertThat(estadoPublicacion1).isNotEqualTo(estadoPublicacion2);
    }
}
