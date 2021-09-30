package co.edu.usco.inventariotecnopcmundo.domain;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.usco.inventariotecnopcmundo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CategoriaPublicacionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategoriaPublicacion.class);
        CategoriaPublicacion categoriaPublicacion1 = new CategoriaPublicacion();
        categoriaPublicacion1.setId(1L);
        CategoriaPublicacion categoriaPublicacion2 = new CategoriaPublicacion();
        categoriaPublicacion2.setId(categoriaPublicacion1.getId());
        assertThat(categoriaPublicacion1).isEqualTo(categoriaPublicacion2);
        categoriaPublicacion2.setId(2L);
        assertThat(categoriaPublicacion1).isNotEqualTo(categoriaPublicacion2);
        categoriaPublicacion1.setId(null);
        assertThat(categoriaPublicacion1).isNotEqualTo(categoriaPublicacion2);
    }
}
