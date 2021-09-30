package co.edu.usco.inventariotecnopcmundo.service.mapper;

import co.edu.usco.inventariotecnopcmundo.domain.*;
import co.edu.usco.inventariotecnopcmundo.service.dto.ProductoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Producto} and its DTO {@link ProductoDTO}.
 */
@Mapper(componentModel = "spring", uses = { PrecioMapper.class, CategoriaMapper.class, ProveedorMapper.class, EstadoMapper.class })
public interface ProductoMapper extends EntityMapper<ProductoDTO, Producto> {
    @Mapping(target = "precio", source = "precio", qualifiedByName = "precioVenta")
    @Mapping(target = "categoria", source = "categoria", qualifiedByName = "nombre")
    @Mapping(target = "proveedor", source = "proveedor", qualifiedByName = "nombre")
    @Mapping(target = "estado", source = "estado", qualifiedByName = "nombre")
    ProductoDTO toDto(Producto s);

    @Named("nombre")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombre", source = "nombre")
    ProductoDTO toDtoNombre(Producto producto);
}
