package co.edu.usco.inventariotecnopcmundo.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the
 * {@link co.edu.usco.inventariotecnopcmundo.domain.EstadoPublicacion} entity.
 */
public class EstadoPublicacionDTO implements Serializable {

    private Long id;

    private String nombre;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EstadoPublicacionDTO)) {
            return false;
        }

        EstadoPublicacionDTO estadoPublicacionDTO = (EstadoPublicacionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, estadoPublicacionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EstadoPublicacionDTO{" + "id=" + getId() + ", nombre='" + getNombre() + "'" + "}";
    }
}
