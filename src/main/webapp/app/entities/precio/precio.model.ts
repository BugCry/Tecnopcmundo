import { IProducto } from 'app/entities/producto/producto.model';

export interface IPrecio {
  id?: number;
  precioCompra?: number | null;
  precioVenta?: number | null;
  descuento?: number | null;
  profit?: number | null;
  producto?: IProducto | null;
}

export class Precio implements IPrecio {
  constructor(
    public id?: number,
    public precioCompra?: number | null,
    public precioVenta?: number | null,
    public descuento?: number | null,
    public profit?: number | null,
    public producto?: IProducto | null
  ) {}
}

export function getPrecioIdentifier(precio: IPrecio): number | undefined {
  return precio.id;
}
