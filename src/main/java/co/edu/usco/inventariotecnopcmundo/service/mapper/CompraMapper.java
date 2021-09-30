package co.edu.usco.inventariotecnopcmundo.service.mapper;

import co.edu.usco.inventariotecnopcmundo.domain.*;
import co.edu.usco.inventariotecnopcmundo.service.dto.CompraDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Compra} and its DTO {@link CompraDTO}.
 */
@Mapper(componentModel = "spring", uses = { DetallesUsuarioMapper.class })
public interface CompraMapper extends EntityMapper<CompraDTO, Compra> {
    @Mapping(target = "user", source = "user", qualifiedByName = "id")
    CompraDTO toDto(Compra s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CompraDTO toDtoId(Compra compra);
}
