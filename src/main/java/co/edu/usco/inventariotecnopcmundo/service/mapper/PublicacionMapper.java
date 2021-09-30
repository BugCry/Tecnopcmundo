package co.edu.usco.inventariotecnopcmundo.service.mapper;

import co.edu.usco.inventariotecnopcmundo.domain.*;
import co.edu.usco.inventariotecnopcmundo.service.dto.PublicacionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Publicacion} and its DTO {@link PublicacionDTO}.
 */
@Mapper(componentModel = "spring", uses = { CategoriaPublicacionMapper.class, UserMapper.class, EstadoPublicacionMapper.class })
public interface PublicacionMapper extends EntityMapper<PublicacionDTO, Publicacion> {
    @Mapping(target = "categoriaPublicacion", source = "categoriaPublicacion", qualifiedByName = "titulo")
    @Mapping(target = "user", source = "user", qualifiedByName = "login")
    @Mapping(target = "estado", source = "estado", qualifiedByName = "nombre")
    PublicacionDTO toDto(Publicacion s);
}
