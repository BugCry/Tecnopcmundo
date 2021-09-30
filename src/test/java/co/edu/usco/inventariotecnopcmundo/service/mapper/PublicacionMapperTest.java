package co.edu.usco.inventariotecnopcmundo.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PublicacionMapperTest {

    private PublicacionMapper publicacionMapper;

    @BeforeEach
    public void setUp() {
        publicacionMapper = new PublicacionMapperImpl();
    }
}
