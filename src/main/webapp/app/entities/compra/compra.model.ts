import * as dayjs from 'dayjs';
import { IPedido } from 'app/entities/pedido/pedido.model';
import { IDetallesUsuario } from 'app/entities/detalles-usuario/detalles-usuario.model';

export interface ICompra {
  id?: number;
  total?: number | null;
  createAt?: dayjs.Dayjs | null;
  pedidos?: IPedido[] | null;
  user?: IDetallesUsuario | null;
}

export class Compra implements ICompra {
  constructor(
    public id?: number,
    public total?: number | null,
    public createAt?: dayjs.Dayjs | null,
    public pedidos?: IPedido[] | null,
    public user?: IDetallesUsuario | null
  ) {}
}

export function getCompraIdentifier(compra: ICompra): number | undefined {
  return compra.id;
}
