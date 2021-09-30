export interface IProveedor {
  id?: number;
  nit?: string | null;
  nombre?: string | null;
  contacto?: string | null;
  direccion?: string | null;
}

export class Proveedor implements IProveedor {
  constructor(
    public id?: number,
    public nit?: string | null,
    public nombre?: string | null,
    public contacto?: string | null,
    public direccion?: string | null
  ) {}
}

export function getProveedorIdentifier(proveedor: IProveedor): number | undefined {
  return proveedor.id;
}
