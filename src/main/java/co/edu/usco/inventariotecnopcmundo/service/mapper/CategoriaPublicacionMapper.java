package co.edu.usco.inventariotecnopcmundo.service.mapper;

import co.edu.usco.inventariotecnopcmundo.domain.*;
import co.edu.usco.inventariotecnopcmundo.service.dto.CategoriaPublicacionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CategoriaPublicacion} and its DTO
 * {@link CategoriaPublicacionDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CategoriaPublicacionMapper extends EntityMapper<CategoriaPublicacionDTO, CategoriaPublicacion> {
    @Named("titulo")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "titulo", source = "titulo")
    CategoriaPublicacionDTO toDtoTitulo(CategoriaPublicacion categoriaPublicacion);
}
