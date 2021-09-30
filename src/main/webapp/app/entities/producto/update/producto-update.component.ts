import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IProducto, Producto } from '../producto.model';
import { ProductoService } from '../service/producto.service';
import { IPrecio } from 'app/entities/precio/precio.model';
import { PrecioService } from 'app/entities/precio/service/precio.service';
import { ICategoria } from 'app/entities/categoria/categoria.model';
import { CategoriaService } from 'app/entities/categoria/service/categoria.service';
import { IProveedor } from 'app/entities/proveedor/proveedor.model';
import { ProveedorService } from 'app/entities/proveedor/service/proveedor.service';
import { IEstado } from 'app/entities/estado/estado.model';
import { EstadoService } from 'app/entities/estado/service/estado.service';

@Component({
  selector: 'jhi-producto-update',
  templateUrl: './producto-update.component.html',
})
export class ProductoUpdateComponent implements OnInit {
  isSaving = false;

  preciosCollection: IPrecio[] = [];
  categoriasSharedCollection: ICategoria[] = [];
  proveedorsSharedCollection: IProveedor[] = [];
  estadosSharedCollection: IEstado[] = [];

  editForm = this.fb.group({
    id: [],
    nombre: [],
    cantidad: [],
    precio: [],
    categoria: [],
    proveedor: [],
    estado: [],
  });

  constructor(
    protected productoService: ProductoService,
    protected precioService: PrecioService,
    protected categoriaService: CategoriaService,
    protected proveedorService: ProveedorService,
    protected estadoService: EstadoService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ producto }) => {
      this.updateForm(producto);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const producto = this.createFromForm();
    if (producto.id !== undefined) {
      this.subscribeToSaveResponse(this.productoService.update(producto));
    } else {
      this.subscribeToSaveResponse(this.productoService.create(producto));
    }
  }

  trackPrecioById(index: number, item: IPrecio): number {
    return item.id!;
  }

  trackCategoriaById(index: number, item: ICategoria): number {
    return item.id!;
  }

  trackProveedorById(index: number, item: IProveedor): number {
    return item.id!;
  }

  trackEstadoById(index: number, item: IEstado): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProducto>>): void {
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

  protected updateForm(producto: IProducto): void {
    this.editForm.patchValue({
      id: producto.id,
      nombre: producto.nombre,
      cantidad: producto.cantidad,
      precio: producto.precio,
      categoria: producto.categoria,
      proveedor: producto.proveedor,
      estado: producto.estado,
    });

    this.preciosCollection = this.precioService.addPrecioToCollectionIfMissing(this.preciosCollection, producto.precio);
    this.categoriasSharedCollection = this.categoriaService.addCategoriaToCollectionIfMissing(
      this.categoriasSharedCollection,
      producto.categoria
    );
    this.proveedorsSharedCollection = this.proveedorService.addProveedorToCollectionIfMissing(
      this.proveedorsSharedCollection,
      producto.proveedor
    );
    this.estadosSharedCollection = this.estadoService.addEstadoToCollectionIfMissing(this.estadosSharedCollection, producto.estado);
  }

  protected loadRelationshipsOptions(): void {
    this.precioService
      .query({ filter: 'producto-is-null' })
      .pipe(map((res: HttpResponse<IPrecio[]>) => res.body ?? []))
      .pipe(map((precios: IPrecio[]) => this.precioService.addPrecioToCollectionIfMissing(precios, this.editForm.get('precio')!.value)))
      .subscribe((precios: IPrecio[]) => (this.preciosCollection = precios));

    this.categoriaService
      .query()
      .pipe(map((res: HttpResponse<ICategoria[]>) => res.body ?? []))
      .pipe(
        map((categorias: ICategoria[]) =>
          this.categoriaService.addCategoriaToCollectionIfMissing(categorias, this.editForm.get('categoria')!.value)
        )
      )
      .subscribe((categorias: ICategoria[]) => (this.categoriasSharedCollection = categorias));

    this.proveedorService
      .query()
      .pipe(map((res: HttpResponse<IProveedor[]>) => res.body ?? []))
      .pipe(
        map((proveedors: IProveedor[]) =>
          this.proveedorService.addProveedorToCollectionIfMissing(proveedors, this.editForm.get('proveedor')!.value)
        )
      )
      .subscribe((proveedors: IProveedor[]) => (this.proveedorsSharedCollection = proveedors));

    this.estadoService
      .query()
      .pipe(map((res: HttpResponse<IEstado[]>) => res.body ?? []))
      .pipe(map((estados: IEstado[]) => this.estadoService.addEstadoToCollectionIfMissing(estados, this.editForm.get('estado')!.value)))
      .subscribe((estados: IEstado[]) => (this.estadosSharedCollection = estados));
  }

  protected createFromForm(): IProducto {
    return {
      ...new Producto(),
      id: this.editForm.get(['id'])!.value,
      nombre: this.editForm.get(['nombre'])!.value,
      precio: this.editForm.get(['precio'])!.value,
      categoria: this.editForm.get(['categoria'])!.value,
      proveedor: this.editForm.get(['proveedor'])!.value,
      estado: this.editForm.get(['estado'])!.value,
      cantidad: this.editForm.get(['estado'])!.value.nombre === 'EN EXISTENCIA' ? this.editForm.get(['cantidad'])!.value : 0,
    };
  }
}
