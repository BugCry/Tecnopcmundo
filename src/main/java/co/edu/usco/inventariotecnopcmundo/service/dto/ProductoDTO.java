package co.edu.usco.inventariotecnopcmundo.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link co.edu.usco.inventariotecnopcmundo.domain.Producto}
 * entity.
 */
public class ProductoDTO implements Serializable {

    private Long id;

    private String nombre;

    private Long cantidad;

    private PrecioDTO precio;

    private CategoriaDTO categoria;

    private ProveedorDTO proveedor;

    private EstadoDTO estado;

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

    public Long getCantidad() {
        return cantidad;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }

    public PrecioDTO getPrecio() {
        return precio;
    }

    public void setPrecio(PrecioDTO precio) {
        this.precio = precio;
    }

    public CategoriaDTO getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaDTO categoria) {
        this.categoria = categoria;
    }

    public ProveedorDTO getProveedor() {
        return proveedor;
    }

    public void setProveedor(ProveedorDTO proveedor) {
        this.proveedor = proveedor;
    }

    public EstadoDTO getEstado() {
        return estado;
    }

    public void setEstado(EstadoDTO estado) {
        this.estado = estado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductoDTO)) {
            return false;
        }

        ProductoDTO productoDTO = (ProductoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, productoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductoDTO{" + "id=" + getId() + ", nombre='" + getNombre() + "'" + ", cantidad=" + getCantidad()
                + ", precio=" + getPrecio() + ", categoria=" + getCategoria() + ", proveedor=" + getProveedor()
                + ", estado=" + getEstado() + "}";
    }

    public int getCantidadInventario() {
        return 0;
    }

    public int getCantidadVentas() {
        return 0;
    }
}
