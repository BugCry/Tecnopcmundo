package co.edu.usco.inventariotecnopcmundo.service.mapper;

import co.edu.usco.inventariotecnopcmundo.domain.*;
import co.edu.usco.inventariotecnopcmundo.service.dto.DetallesUsuarioDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DetallesUsuario} and its DTO
 * {@link DetallesUsuarioDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface DetallesUsuarioMapper extends EntityMapper<DetallesUsuarioDTO, DetallesUsuario> {
    @Mapping(target = "user", source = "user", qualifiedByName = "login")
    DetallesUsuarioDTO toDto(DetallesUsuario s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DetallesUsuarioDTO toDtoId(DetallesUsuario detallesUsuario);
}
