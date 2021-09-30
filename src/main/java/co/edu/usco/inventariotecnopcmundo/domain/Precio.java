package co.edu.usco.inventariotecnopcmundo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

/**
 * A Precio.
 */
@Entity
@Table(name = "precio")
public class Precio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "precio_compra", precision = 21, scale = 2)
    private BigDecimal precioCompra;

    @Column(name = "precio_venta", precision = 21, scale = 2)
    private BigDecimal precioVenta;

    @Column(name = "descuento")
    private Integer descuento;

    @Column(name = "profit")
    private Integer profit;

    @JsonIgnoreProperties(value = { "precio", "categoria", "proveedor", "estado" }, allowSetters = true)
    @OneToOne(mappedBy = "precio")
    private Producto producto;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Precio id(Long id) {
        this.id = id;
        return this;
    }

    public BigDecimal getPrecioCompra() {
        return this.precioCompra;
    }

    public Precio precioCompra(BigDecimal precioCompra) {
        this.precioCompra = precioCompra;
        return this;
    }

    public void setPrecioCompra(BigDecimal precioCompra) {
        this.precioCompra = precioCompra;
    }

    public BigDecimal getPrecioVenta() {
        return this.precioVenta;
    }

    public Precio precioVenta(BigDecimal precioVenta) {
        this.precioVenta = precioVenta;
        return this;
    }

    public void setPrecioVenta(BigDecimal precioVenta) {
        this.precioVenta = precioVenta;
    }

    public Integer getDescuento() {
        return this.descuento;
    }

    public Precio descuento(Integer descuento) {
        this.descuento = descuento;
        return this;
    }

    public void setDescuento(Integer descuento) {
        this.descuento = descuento;
    }

    public Integer getProfit() {
        return this.profit;
    }

    public Precio profit(Integer profit) {
        this.profit = profit;
        return this;
    }

    public void setProfit(Integer profit) {
        this.profit = profit;
    }

    public Producto getProducto() {
        return this.producto;
    }

    public Precio producto(Producto producto) {
        this.setProducto(producto);
        return this;
    }

    public void setProducto(Producto producto) {
        if (this.producto != null) {
            this.producto.setPrecio(null);
        }
        if (producto != null) {
            producto.setPrecio(this);
        }
        this.producto = producto;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and
    // setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Precio)) {
            return false;
        }
        return id != null && id.equals(((Precio) o).id);
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
        return "Precio{" + "id=" + getId() + ", precioCompra=" + getPrecioCompra() + ", precioVenta=" + getPrecioVenta()
                + ", descuento=" + getDescuento() + ", profit=" + getProfit() + "}";
    }
}
