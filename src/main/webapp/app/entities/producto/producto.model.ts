import { IPrecio } from 'app/entities/precio/precio.model';
import { ICategoria } from 'app/entities/categoria/categoria.model';
import { IProveedor } from 'app/entities/proveedor/proveedor.model';
import { IEstado } from 'app/entities/estado/estado.model';

export interface IProducto {
  id?: number;
  nombre?: string | null;
  cantidad?: number | null;
  precio?: IPrecio | null;
  categoria?: ICategoria | null;
  proveedor?: IProveedor | null;
  estado?: IEstado | null;
}

export class Producto implements IProducto {
  constructor(
    public id?: number,
    public nombre?: string | null,
    public cantidad?: number | null,
    public precio?: IPrecio | null,
    public categoria?: ICategoria | null,
    public proveedor?: IProveedor | null,
    public estado?: IEstado | null
  ) {}
}

export function getProductoIdentifier(producto: IProducto): number | undefined {
  return producto.id;
}
