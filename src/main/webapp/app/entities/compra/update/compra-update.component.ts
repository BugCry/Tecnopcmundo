import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ICompra, Compra } from '../compra.model';
import { CompraService } from '../service/compra.service';
import { IDetallesUsuario } from 'app/entities/detalles-usuario/detalles-usuario.model';
import { DetallesUsuarioService } from 'app/entities/detalles-usuario/service/detalles-usuario.service';

@Component({
  selector: 'jhi-compra-update',
  templateUrl: './compra-update.component.html',
})
export class CompraUpdateComponent implements OnInit {
  isSaving = false;

  detallesUsuariosSharedCollection: IDetallesUsuario[] = [];

  editForm = this.fb.group({
    id: [],
    total: [],
    createAt: [],
    user: [],
  });

  constructor(
    protected compraService: CompraService,
    protected detallesUsuarioService: DetallesUsuarioService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ compra }) => {
      if (compra.id === undefined) {
        const today = dayjs().startOf('day');
        compra.createAt = today;
      }

      this.updateForm(compra);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const compra = this.createFromForm();
    if (compra.id !== undefined) {
      this.subscribeToSaveResponse(this.compraService.update(compra));
    } else {
      this.subscribeToSaveResponse(this.compraService.create(compra));
    }
  }

  trackDetallesUsuarioById(index: number, item: IDetallesUsuario): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICompra>>): void {
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

  protected updateForm(compra: ICompra): void {
    this.editForm.patchValue({
      id: compra.id,
      total: compra.total,
      createAt: compra.createAt ? compra.createAt.format(DATE_TIME_FORMAT) : null,
      user: compra.user,
    });

    this.detallesUsuariosSharedCollection = this.detallesUsuarioService.addDetallesUsuarioToCollectionIfMissing(
      this.detallesUsuariosSharedCollection,
      compra.user
    );
  }

  protected loadRelationshipsOptions(): void {
    this.detallesUsuarioService
      .query()
      .pipe(map((res: HttpResponse<IDetallesUsuario[]>) => res.body ?? []))
      .pipe(
        map((detallesUsuarios: IDetallesUsuario[]) =>
          this.detallesUsuarioService.addDetallesUsuarioToCollectionIfMissing(detallesUsuarios, this.editForm.get('user')!.value)
        )
      )
      .subscribe((detallesUsuarios: IDetallesUsuario[]) => (this.detallesUsuariosSharedCollection = detallesUsuarios));
  }

  protected createFromForm(): ICompra {
    return {
      ...new Compra(),
      id: this.editForm.get(['id'])!.value,
      total: this.editForm.get(['total'])!.value,
      createAt: this.editForm.get(['createAt'])!.value ? dayjs(this.editForm.get(['createAt'])!.value, DATE_TIME_FORMAT) : undefined,
      user: this.editForm.get(['user'])!.value,
    };
  }
}
