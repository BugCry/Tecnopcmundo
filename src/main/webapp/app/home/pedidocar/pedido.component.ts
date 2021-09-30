import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AccountService } from 'app/core/auth/account.service';
import { ICompra } from 'app/entities/compra/compra.model';
import { CompraService } from 'app/entities/compra/service/compra.service';
import { IDetallesUsuario } from 'app/entities/detalles-usuario/detalles-usuario.model';
import { DetallesUsuarioService } from 'app/entities/detalles-usuario/service/detalles-usuario.service';
import { IPedido } from 'app/entities/pedido/pedido.model';
import { IPrecio } from 'app/entities/precio/precio.model';
import { IProducto } from 'app/entities/producto/producto.model';
import * as dayjs from 'dayjs';
import { PedidocarService } from './pedidocar.service';

@Component({
  selector: 'jhi-pedido',
  templateUrl: './pedido.component.html',
  styleUrls: ['./pedido.component.scss'],
})
export class PedidocarComponent implements OnInit {
  form!: FormGroup;

  pedidos: IPedido[] = [];

  totalFactura = 0;
  userLogin!: string;
  detallesUsuario!: IDetallesUsuario;

  constructor(
    private formBuilder: FormBuilder,
    private accountService: AccountService,
    private compraService: CompraService,
    private detallesUsuarioService: DetallesUsuarioService,
    private pedidocarService: PedidocarService,
    private router: Router
  ) {
    this.form = this.formBuilder.group({});
  }

  ngOnInit(): void {
    this.accountService.getAuthenticationState().subscribe(account => {
      this.userLogin = account!.login;

      this.detallesUsuarioService.findByUserLogin(this.userLogin).subscribe(detallesUsuario => {
        this.detallesUsuario = detallesUsuario;
      });

      console.warn(this.userLogin);
    });

    this.pedidocarService.userCart$.subscribe(pedidos => {
      this.pedidos = [...pedidos];
      this.pedidos.forEach(pedido => this.addPedidoControl(pedido));
      this.getTotalFactura();
    });
  }

  getReactiveControl(controlName: string): AbstractControl | null {
    return this.form.get(controlName);
  }

  hacerPedido(): void {
    console.warn(this.pedidos);

    const compra: ICompra = {
      createAt: dayjs().startOf('seconds'),
      total: this.totalFactura,
      pedidos: this.pedidos,
      user: this.detallesUsuario,
    };

    this.compraService.create(compra).subscribe(() => {
      this.pedidocarService.removeAllPoductsInUserCart();
      this.router.navigate(['/']);
    });
  }

  // Events

  onInputFieldAmount(pedido: IPedido, control: AbstractControl | null): void {
    const amountFieldValue = control!.value;
    pedido.cantidad = isNaN(amountFieldValue) || amountFieldValue < 1 ? 1 : amountFieldValue;
    this.getTotalFactura();
  }

  onClickSpliceBillProduct(index: number, controlName: string): void {
    this.pedidocarService.spliceProductCart(index);
    this.form.removeControl(controlName);
    this.getTotalFactura();
  }

  onProductEmited(product: IProducto): void {
    this.pedidocarService.addPedidosCart(product);
    this.getTotalFactura();
  }

  // Utils

  getTotalFactura(): number {
    this.totalFactura = 0;
    this.pedidos.forEach(pedido => (this.totalFactura += pedido.cantidad! * pedido.producto!.precio!.precioCompra!));
    return this.totalFactura;
  }

  // Private Methods

  private addPedidoControl(pedido: IPedido): void {
    this.form.addControl(
      pedido.producto!.nombre!,
      new FormControl(
        pedido.cantidad,
        Validators.compose([Validators.required, Validators.min(1), Validators.max(pedido.producto!.cantidad!)])
      )
    );
  }
}
