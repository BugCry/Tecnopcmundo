package co.edu.usco.inventariotecnopcmundo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.usco.inventariotecnopcmundo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CategoriaPublicacionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategoriaPublicacionDTO.class);
        CategoriaPublicacionDTO categoriaPublicacionDTO1 = new CategoriaPublicacionDTO();
        categoriaPublicacionDTO1.setId(1L);
        CategoriaPublicacionDTO categoriaPublicacionDTO2 = new CategoriaPublicacionDTO();
        assertThat(categoriaPublicacionDTO1).isNotEqualTo(categoriaPublicacionDTO2);
        categoriaPublicacionDTO2.setId(categoriaPublicacionDTO1.getId());
        assertThat(categoriaPublicacionDTO1).isEqualTo(categoriaPublicacionDTO2);
        categoriaPublicacionDTO2.setId(2L);
        assertThat(categoriaPublicacionDTO1).isNotEqualTo(categoriaPublicacionDTO2);
        categoriaPublicacionDTO1.setId(null);
        assertThat(categoriaPublicacionDTO1).isNotEqualTo(categoriaPublicacionDTO2);
    }
}
