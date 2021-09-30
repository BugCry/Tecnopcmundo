import { IProducto } from 'app/entities/producto/producto.model';
import { ICompra } from 'app/entities/compra/compra.model';

export interface IPedido {
  id?: number;
  cantidad?: number | null;
  producto?: IProducto | null;
  compra?: ICompra | null;
}

export class Pedido implements IPedido {
  constructor(public id?: number, public cantidad?: number | null, public producto?: IProducto | null, public compra?: ICompra | null) {}
}

export function getPedidoIdentifier(pedido: IPedido): number | undefined {
  return pedido.id;
}
