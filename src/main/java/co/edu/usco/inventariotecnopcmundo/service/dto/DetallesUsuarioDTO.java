package co.edu.usco.inventariotecnopcmundo.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the
 * {@link co.edu.usco.inventariotecnopcmundo.domain.DetallesUsuario} entity.
 */
public class DetallesUsuarioDTO implements Serializable {

    private Long id;

    private String telefono;

    private String identificacion;

    private String ciudad;

    private UserDTO user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DetallesUsuarioDTO)) {
            return false;
        }

        DetallesUsuarioDTO detallesUsuarioDTO = (DetallesUsuarioDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, detallesUsuarioDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DetallesUsuarioDTO{" + "id=" + getId() + ", telefono='" + getTelefono() + "'" + ", identificacion='"
                + getIdentificacion() + "'" + ", ciudad='" + getCiudad() + "'" + ", user=" + getUser() + "}";
    }
}
