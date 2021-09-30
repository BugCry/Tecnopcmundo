package co.edu.usco.inventariotecnopcmundo.service.mapper;

import co.edu.usco.inventariotecnopcmundo.domain.*;
import co.edu.usco.inventariotecnopcmundo.service.dto.PedidoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Pedido} and its DTO {@link PedidoDTO}.
 */
@Mapper(componentModel = "spring", uses = { ProductoMapper.class, CompraMapper.class })
public interface PedidoMapper extends EntityMapper<PedidoDTO, Pedido> {
    @Mapping(target = "producto", source = "producto", qualifiedByName = "nombre")
    @Mapping(target = "compra", source = "compra", qualifiedByName = "id")
    PedidoDTO toDto(Pedido s);
}
