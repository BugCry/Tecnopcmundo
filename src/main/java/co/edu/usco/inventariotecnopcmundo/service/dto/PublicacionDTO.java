package co.edu.usco.inventariotecnopcmundo.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link co.edu.usco.inventariotecnopcmundo.domain.Publicacion}
 * entity.
 */
public class PublicacionDTO implements Serializable {

    private Long id;

    private String titulo;

    @Lob
    private String descripcion;

    @Lob
    private byte[] imagen;

    private String imagenContentType;
    private Instant createAt;

    private CategoriaPublicacionDTO categoriaPublicacion;

    private UserDTO user;

    private EstadoPublicacionDTO estado;

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

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public String getImagenContentType() {
        return imagenContentType;
    }

    public void setImagenContentType(String imagenContentType) {
        this.imagenContentType = imagenContentType;
    }

    public Instant getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Instant createAt) {
        this.createAt = createAt;
    }

    public CategoriaPublicacionDTO getCategoriaPublicacion() {
        return categoriaPublicacion;
    }

    public void setCategoriaPublicacion(CategoriaPublicacionDTO categoriaPublicacion) {
        this.categoriaPublicacion = categoriaPublicacion;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public EstadoPublicacionDTO getEstado() {
        return estado;
    }

    public void setEstado(EstadoPublicacionDTO estado) {
        this.estado = estado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PublicacionDTO)) {
            return false;
        }

        PublicacionDTO publicacionDTO = (PublicacionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, publicacionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PublicacionDTO{" + "id=" + getId() + ", titulo='" + getTitulo() + "'" + ", descripcion='"
                + getDescripcion() + "'" + ", imagen='" + getImagen() + "'" + ", createAt='" + getCreateAt() + "'"
                + ", categoriaPublicacion=" + getCategoriaPublicacion() + ", user=" + getUser() + ", estado="
                + getEstado() + "}";
    }
}
