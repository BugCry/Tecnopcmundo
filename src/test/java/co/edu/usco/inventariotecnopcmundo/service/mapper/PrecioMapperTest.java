package co.edu.usco.inventariotecnopcmundo.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PrecioMapperTest {

    private PrecioMapper precioMapper;

    @BeforeEach
    public void setUp() {
        precioMapper = new PrecioMapperImpl();
    }
}
