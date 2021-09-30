package co.edu.usco.inventariotecnopcmundo.service.mapper;

import co.edu.usco.inventariotecnopcmundo.domain.*;
import co.edu.usco.inventariotecnopcmundo.service.dto.EstadoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Estado} and its DTO {@link EstadoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EstadoMapper extends EntityMapper<EstadoDTO, Estado> {
    @Named("nombre")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombre", source = "nombre")
    EstadoDTO toDtoNombre(Estado estado);
}
