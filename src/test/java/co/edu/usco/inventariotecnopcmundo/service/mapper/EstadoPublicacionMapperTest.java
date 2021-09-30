package co.edu.usco.inventariotecnopcmundo.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EstadoPublicacionMapperTest {

    private EstadoPublicacionMapper estadoPublicacionMapper;

    @BeforeEach
    public void setUp() {
        estadoPublicacionMapper = new EstadoPublicacionMapperImpl();
    }
}
