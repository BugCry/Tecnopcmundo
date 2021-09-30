import { IUser } from 'app/entities/user/user.model';
import { ICompra } from 'app/entities/compra/compra.model';

export interface IDetallesUsuario {
  id?: number;
  telefono?: string | null;
  identificacion?: string | null;
  ciudad?: string | null;
  user?: IUser | null;
  compras?: ICompra[] | null;
}

export class DetallesUsuario implements IDetallesUsuario {
  constructor(
    public id?: number,
    public telefono?: string | null,
    public identificacion?: string | null,
    public ciudad?: string | null,
    public user?: IUser | null,
    public compras?: ICompra[] | null
  ) {}
}

export function getDetallesUsuarioIdentifier(detallesUsuario: IDetallesUsuario): number | undefined {
  return detallesUsuario.id;
}
