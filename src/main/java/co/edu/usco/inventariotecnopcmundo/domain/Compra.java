package co.edu.usco.inventariotecnopcmundo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Compra.
 */
@Entity
@Table(name = "compra")
public class Compra implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total", precision = 21, scale = 2)
    private BigDecimal total;

    @Column(name = "create_at")
    private Instant createAt;

    @OneToMany(mappedBy = "compra")
    @JsonIgnoreProperties(value = { "producto", "compra" }, allowSetters = true)
    private Set<Pedido> pedidos = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "user", "compras" }, allowSetters = true)
    private DetallesUsuario user;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Compra id(Long id) {
        this.id = id;
        return this;
    }

    public BigDecimal getTotal() {
        return this.total;
    }

    public Compra total(BigDecimal total) {
        this.total = total;
        return this;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Instant getCreateAt() {
        return this.createAt;
    }

    public Compra createAt(Instant createAt) {
        this.createAt = createAt;
        return this;
    }

    public void setCreateAt(Instant createAt) {
        this.createAt = createAt;
    }

    public Set<Pedido> getPedidos() {
        return this.pedidos;
    }

    public Compra pedidos(Set<Pedido> pedidos) {
        this.setPedidos(pedidos);
        return this;
    }

    public Compra addPedido(Pedido pedido) {
        this.pedidos.add(pedido);
        pedido.setCompra(this);
        return this;
    }

    public Compra removePedido(Pedido pedido) {
        this.pedidos.remove(pedido);
        pedido.setCompra(null);
        return this;
    }

    public void setPedidos(Set<Pedido> pedidos) {
        if (this.pedidos != null) {
            this.pedidos.forEach(i -> i.setCompra(null));
        }
        if (pedidos != null) {
            pedidos.forEach(i -> i.setCompra(this));
        }
        this.pedidos = pedidos;
    }

    public DetallesUsuario getUser() {
        return this.user;
    }

    public Compra user(DetallesUsuario detallesUsuario) {
        this.setUser(detallesUsuario);
        return this;
    }

    public void setUser(DetallesUsuario detallesUsuario) {
        this.user = detallesUsuario;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and
    // setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Compra)) {
            return false;
        }
        return id != null && id.equals(((Compra) o).id);
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
        return "Compra{" + "id=" + getId() + ", total=" + getTotal() + ", createAt='" + getCreateAt() + "'" + "}";
    }
}
