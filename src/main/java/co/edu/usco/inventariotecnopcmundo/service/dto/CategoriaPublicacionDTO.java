package co.edu.usco.inventariotecnopcmundo.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link co.edu.usco.inventariotecnopcmundo.domain.CategoriaPublicacion} entity.
 */
public class CategoriaPublicacionDTO implements Serializable {

    private Long id;

    private String titulo;

    @Lob
    private String descripcion;

    private Instant createAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Instant getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Instant createAt) {
        this.createAt = createAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CategoriaPublicacionDTO)) {
            return false;
        }

        CategoriaPublicacionDTO categoriaPublicacionDTO = (CategoriaPublicacionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, categoriaPublicacionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CategoriaPublicacionDTO{" +
            "id=" + getId() +
            ", titulo='" + getTitulo() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", createAt='" + getCreateAt() + "'" +
            "}";
    }
}
