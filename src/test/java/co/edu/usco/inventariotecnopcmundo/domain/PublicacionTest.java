package co.edu.usco.inventariotecnopcmundo.domain;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.usco.inventariotecnopcmundo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PublicacionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Publicacion.class);
        Publicacion publicacion1 = new Publicacion();
        publicacion1.setId(1L);
        Publicacion publicacion2 = new Publicacion();
        publicacion2.setId(publicacion1.getId());
        assertThat(publicacion1).isEqualTo(publicacion2);
        publicacion2.setId(2L);
        assertThat(publicacion1).isNotEqualTo(publicacion2);
        publicacion1.setId(null);
        assertThat(publicacion1).isNotEqualTo(publicacion2);
    }
}
