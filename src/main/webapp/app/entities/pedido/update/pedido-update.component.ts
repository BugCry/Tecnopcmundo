import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IPedido, Pedido } from '../pedido.model';
import { PedidoService } from '../service/pedido.service';
import { IProducto } from 'app/entities/producto/producto.model';
import { ProductoService } from 'app/entities/producto/service/producto.service';
import { ICompra } from 'app/entities/compra/compra.model';
import { CompraService } from 'app/entities/compra/service/compra.service';

@Component({
  selector: 'jhi-pedido-update',
  templateUrl: './pedido-update.component.html',
})
export class PedidoUpdateComponent implements OnInit {
  isSaving = false;

  productosSharedCollection: IProducto[] = [];
  comprasSharedCollection: ICompra[] = [];

  editForm = this.fb.group({
    id: [],
    cantidad: [],
    producto: [],
    compra: [],
  });

  constructor(
    protected pedidoService: PedidoService,
    protected productoService: ProductoService,
    protected compraService: CompraService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pedido }) => {
      this.updateForm(pedido);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pedido = this.createFromForm();
    if (pedido.id !== undefined) {
      this.subscribeToSaveResponse(this.pedidoService.update(pedido));
    } else {
      this.subscribeToSaveResponse(this.pedidoService.create(pedido));
    }
  }

  trackProductoById(index: number, item: IProducto): number {
    return item.id!;
  }

  trackCompraById(index: number, item: ICompra): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPedido>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(pedido: IPedido): void {
    this.editForm.patchValue({
      id: pedido.id,
      cantidad: pedido.cantidad,
      producto: pedido.producto,
      compra: pedido.compra,
    });

    this.productosSharedCollection = this.productoService.addProductoToCollectionIfMissing(this.productosSharedCollection, pedido.producto);
    this.comprasSharedCollection = this.compraService.addCompraToCollectionIfMissing(this.comprasSharedCollection, pedido.compra);
  }

  protected loadRelationshipsOptions(): void {
    this.productoService
      .query()
      .pipe(map((res: HttpResponse<IProducto[]>) => res.body ?? []))
      .pipe(
        map((productos: IProducto[]) =>
          this.productoService.addProductoToCollectionIfMissing(productos, this.editForm.get('producto')!.value)
        )
      )
      .subscribe((productos: IProducto[]) => (this.productosSharedCollection = productos));

    this.compraService
      .query()
      .pipe(map((res: HttpResponse<ICompra[]>) => res.body ?? []))
      .pipe(map((compras: ICompra[]) => this.compraService.addCompraToCollectionIfMissing(compras, this.editForm.get('compra')!.value)))
      .subscribe((compras: ICompra[]) => (this.comprasSharedCollection = compras));
  }

  protected createFromForm(): IPedido {
    return {
      ...new Pedido(),
      id: this.editForm.get(['id'])!.value,
      cantidad: this.editForm.get(['cantidad'])!.value,
      producto: this.editForm.get(['producto'])!.value,
      compra: this.editForm.get(['compra'])!.value,
    };
  }
}
