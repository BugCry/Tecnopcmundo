package co.edu.usco.inventariotecnopcmundo.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;

/**
 * A CategoriaPublicacion.
 */
@Entity
@Table(name = "categoria_publicacion")
public class CategoriaPublicacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titulo")
    private String titulo;

    @Lob
    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "create_at")
    private Instant createAt;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CategoriaPublicacion id(Long id) {
        this.id = id;
        return this;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public CategoriaPublicacion titulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public CategoriaPublicacion descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Instant getCreateAt() {
        return this.createAt;
    }

    public CategoriaPublicacion createAt(Instant createAt) {
        this.createAt = createAt;
        return this;
    }

    public void setCreateAt(Instant createAt) {
        this.createAt = createAt;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and
    // setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CategoriaPublicacion)) {
            return false;
        }
        return id != null && id.equals(((CategoriaPublicacion) o).id);
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
        return "CategoriaPublicacion{" + "id=" + getId() + ", titulo='" + getTitulo() + "'" + ", descripcion='"
                + getDescripcion() + "'" + ", createAt='" + getCreateAt() + "'" + "}";
    }
}
