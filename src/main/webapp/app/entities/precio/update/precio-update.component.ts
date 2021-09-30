import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IPrecio, Precio } from '../precio.model';
import { PrecioService } from '../service/precio.service';

@Component({
  selector: 'jhi-precio-update',
  templateUrl: './precio-update.component.html',
})
export class PrecioUpdateComponent implements OnInit {
  isSaving = false;

  precioVentaF = 0;

  editForm = this.fb.group({
    id: [],
    precioCompra: [],
    precioVenta: [],
    descuento: [],
    profit: [],
  });

  constructor(protected precioService: PrecioService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  changeFn(e: any): void {
    this.precioVentaF =
      Number(this.editForm.get(['precioCompra'])!.value) +
      Number(this.editForm.get(['precioCompra'])!.value) * (Number(this.editForm.get(['profit'])!.value) / 100);
    //this.precioVentaF = this.precioVentaF - (this.precioVentaF * Number(this.editForm.get(['descuento'])!.value) / 100);
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ precio }) => {
      this.updateForm(precio);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const precio = this.createFromForm();
    if (precio.id !== undefined) {
      this.subscribeToSaveResponse(this.precioService.update(precio));
    } else {
      this.subscribeToSaveResponse(this.precioService.create(precio));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPrecio>>): void {
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

  protected updateForm(precio: IPrecio): void {
    this.editForm.patchValue({
      id: precio.id,
      precioCompra: precio.precioCompra,
      precioVenta: precio.precioVenta,
      descuento: precio.descuento,
      profit: precio.profit,
    });
  }

  protected createFromForm(): IPrecio {
    return {
      ...new Precio(),
      id: this.editForm.get(['id'])!.value,
      precioCompra: this.editForm.get(['precioCompra'])!.value,
      precioVenta: this.precioVentaF,
      descuento: this.editForm.get(['descuento'])!.value,
      profit: this.editForm.get(['profit'])!.value,
    };
  }
}
