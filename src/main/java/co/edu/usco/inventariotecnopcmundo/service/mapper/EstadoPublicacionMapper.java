package co.edu.usco.inventariotecnopcmundo.service.mapper;

import co.edu.usco.inventariotecnopcmundo.domain.*;
import co.edu.usco.inventariotecnopcmundo.service.dto.EstadoPublicacionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EstadoPublicacion} and its DTO
 * {@link EstadoPublicacionDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EstadoPublicacionMapper extends EntityMapper<EstadoPublicacionDTO, EstadoPublicacion> {
    @Named("nombre")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombre", source = "nombre")
    EstadoPublicacionDTO toDtoNombre(EstadoPublicacion estadoPublicacion);
}
