package co.edu.usco.inventariotecnopcmundo.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link co.edu.usco.inventariotecnopcmundo.domain.Precio}
 * entity.
 */
public class PrecioDTO implements Serializable {

    private Long id;

    private BigDecimal precioCompra;

    private BigDecimal precioVenta;

    private Integer descuento;

    private Integer profit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(BigDecimal precioCompra) {
        this.precioCompra = precioCompra;
    }

    public BigDecimal getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(BigDecimal precioVenta) {
        this.precioVenta = precioVenta;
    }

    public Integer getDescuento() {
        return descuento;
    }

    public void setDescuento(Integer descuento) {
        this.descuento = descuento;
    }

    public Integer getProfit() {
        return profit;
    }

    public void setProfit(Integer profit) {
        this.profit = profit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PrecioDTO)) {
            return false;
        }

        PrecioDTO precioDTO = (PrecioDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, precioDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PrecioDTO{" + "id=" + getId() + ", precioCompra=" + getPrecioCompra() + ", precioVenta="
                + getPrecioVenta() + ", descuento=" + getDescuento() + ", profit=" + getProfit() + "}";
    }
}
