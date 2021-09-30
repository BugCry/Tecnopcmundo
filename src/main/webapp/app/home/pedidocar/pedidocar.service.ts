import { Injectable } from '@angular/core';
import { IPedido, Pedido } from 'app/entities/pedido/pedido.model';
import { IProducto } from 'app/entities/producto/producto.model';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class PedidocarService {
  pedidos: IPedido[] = [];
  userCart = new BehaviorSubject<IPedido[]>([]);

  userCart$ = this.userCart.asObservable();

  constructor() {
    console.error('Hola');
  }

  addPedidosCart(producto: IProducto): void {
    console.warn(producto);

    if (!this.pedidos.find(pd => pd.producto!.id === producto.id)) {
      const pedido = new Pedido();
      pedido.producto = producto;
      pedido.cantidad = 1;
      this.pedidos = [...this.pedidos, pedido];
      this.userCart.next(this.pedidos);
    }
  }

  spliceProductCart(index: number): void {
    this.pedidos = [...this.pedidos];
    this.pedidos.splice(index, 1);
    this.userCart.next(this.pedidos);
  }

  removeAllPoductsInUserCart(): void {
    this.pedidos = [];
    this.userCart.next(this.pedidos);
  }
}
