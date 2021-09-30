package co.edu.usco.inventariotecnopcmundo.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link co.edu.usco.inventariotecnopcmundo.domain.Estado}
 * entity.
 */
public class EstadoDTO implements Serializable {

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
        if (!(o instanceof EstadoDTO)) {
            return false;
        }

        EstadoDTO estadoDTO = (EstadoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, estadoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EstadoDTO{" + "id=" + getId() + ", nombre='" + getNombre() + "'" + "}";
    }
}
