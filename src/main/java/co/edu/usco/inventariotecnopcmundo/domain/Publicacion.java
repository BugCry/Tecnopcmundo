package co.edu.usco.inventariotecnopcmundo.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;

/**
 * A Publicacion.
 */
@Entity
@Table(name = "publicacion")
public class Publicacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titulo")
    private String titulo;

    @Lob
    @Column(name = "descripcion")
    private String descripcion;

    @Lob
    @Column(name = "imagen")
    private byte[] imagen;

    @Column(name = "imagen_content_type")
    private String imagenContentType;

    @Column(name = "create_at")
    private Instant createAt;

    @ManyToOne
    private CategoriaPublicacion categoriaPublicacion;

    @ManyToOne
    private User user;

    @ManyToOne
    private EstadoPublicacion estado;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Publicacion id(Long id) {
        this.id = id;
        return this;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public Publicacion titulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public Publicacion descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public byte[] getImagen() {
        return this.imagen;
    }

    public Publicacion imagen(byte[] imagen) {
        this.imagen = imagen;
        return this;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public String getImagenContentType() {
        return this.imagenContentType;
    }

    public Publicacion imagenContentType(String imagenContentType) {
        this.imagenContentType = imagenContentType;
        return this;
    }

    public void setImagenContentType(String imagenContentType) {
        this.imagenContentType = imagenContentType;
    }

    public Instant getCreateAt() {
        return this.createAt;
    }

    public Publicacion createAt(Instant createAt) {
        this.createAt = createAt;
        return this;
    }

    public void setCreateAt(Instant createAt) {
        this.createAt = createAt;
    }

    public CategoriaPublicacion getCategoriaPublicacion() {
        return this.categoriaPublicacion;
    }

    public Publicacion categoriaPublicacion(CategoriaPublicacion categoriaPublicacion) {
        this.setCategoriaPublicacion(categoriaPublicacion);
        return this;
    }

    public void setCategoriaPublicacion(CategoriaPublicacion categoriaPublicacion) {
        this.categoriaPublicacion = categoriaPublicacion;
    }

    public User getUser() {
        return this.user;
    }

    public Publicacion user(User user) {
        this.setUser(user);
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public EstadoPublicacion getEstado() {
        return this.estado;
    }

    public Publicacion estado(EstadoPublicacion estadoPublicacion) {
        this.setEstado(estadoPublicacion);
        return this;
    }

    public void setEstado(EstadoPublicacion estadoPublicacion) {
        this.estado = estadoPublicacion;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and
    // setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Publicacion)) {
            return false;
        }
        return id != null && id.equals(((Publicacion) o).id);
    }

    @Override
    public int hashCode() {
        // see
        // https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Publicacion{" + "id=" + getId() + ", titulo='" + getTitulo() + "'" + ", descripcion='"
                + getDescripcion() + "'" + ", imagen='" + getImagen() + "'" + ", imagenContentType='"
                + getImagenContentType() + "'" + ", createAt='" + getCreateAt() + "'" + "}";
    }
}
