package co.edu.usco.inventariotecnopcmundo.service.mapper;

import co.edu.usco.inventariotecnopcmundo.domain.*;
import co.edu.usco.inventariotecnopcmundo.service.dto.PrecioDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Precio} and its DTO {@link PrecioDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PrecioMapper extends EntityMapper<PrecioDTO, Precio> {
    @Named("precioVenta")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "precioVenta", source = "precioVenta")
    @Mapping(target = "precioCompra", source = "precioCompra")
    PrecioDTO toDtoPrecioVenta(Precio precio);
}
