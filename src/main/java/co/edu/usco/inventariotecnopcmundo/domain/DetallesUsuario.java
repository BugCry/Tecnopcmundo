package co.edu.usco.inventariotecnopcmundo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A DetallesUsuario.
 */
@Entity
@Table(name = "detalles_usuario")
public class DetallesUsuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "identificacion")
    private String identificacion;

    @Column(name = "ciudad")
    private String ciudad;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @OneToMany(mappedBy = "user")
    @JsonIgnoreProperties(value = { "pedidos", "user" }, allowSetters = true)
    private Set<Compra> compras = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DetallesUsuario id(Long id) {
        this.id = id;
        return this;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public DetallesUsuario telefono(String telefono) {
        this.telefono = telefono;
        return this;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getIdentificacion() {
        return this.identificacion;
    }

    public DetallesUsuario identificacion(String identificacion) {
        this.identificacion = identificacion;
        return this;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getCiudad() {
        return this.ciudad;
    }

    public DetallesUsuario ciudad(String ciudad) {
        this.ciudad = ciudad;
        return this;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public User getUser() {
        return this.user;
    }

    public DetallesUsuario user(User user) {
        this.setUser(user);
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Compra> getCompras() {
        return this.compras;
    }

    public DetallesUsuario compras(Set<Compra> compras) {
        this.setCompras(compras);
        return this;
    }

    public DetallesUsuario addCompra(Compra compra) {
        this.compras.add(compra);
        compra.setUser(this);
        return this;
    }

    public DetallesUsuario removeCompra(Compra compra) {
        this.compras.remove(compra);
        compra.setUser(null);
        return this;
    }

    public void setCompras(Set<Compra> compras) {
        if (this.compras != null) {
            this.compras.forEach(i -> i.setUser(null));
        }
        if (compras != null) {
            compras.forEach(i -> i.setUser(this));
        }
        this.compras = compras;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and
    // setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DetallesUsuario)) {
            return false;
        }
        return id != null && id.equals(((DetallesUsuario) o).id);
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
        return "DetallesUsuario{" + "id=" + getId() + ", telefono='" + getTelefono() + "'" + ", identificacion='"
                + getIdentificacion() + "'" + ", ciudad='" + getCiudad() + "'" + "}";
    }
}
