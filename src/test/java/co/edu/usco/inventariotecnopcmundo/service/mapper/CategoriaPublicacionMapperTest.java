package co.edu.usco.inventariotecnopcmundo.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CategoriaPublicacionMapperTest {

    private CategoriaPublicacionMapper categoriaPublicacionMapper;

    @BeforeEach
    public void setUp() {
        categoriaPublicacionMapper = new CategoriaPublicacionMapperImpl();
    }
}
