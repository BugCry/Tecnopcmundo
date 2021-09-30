import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IEstadoPublicacion, EstadoPublicacion } from '../estado-publicacion.model';
import { EstadoPublicacionService } from '../service/estado-publicacion.service';

@Component({
  selector: 'jhi-estado-publicacion-update',
  templateUrl: './estado-publicacion-update.component.html',
})
export class EstadoPublicacionUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nombre: [],
  });

  constructor(
    protected estadoPublicacionService: EstadoPublicacionService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ estadoPublicacion }) => {
      this.updateForm(estadoPublicacion);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const estadoPublicacion = this.createFromForm();
    if (estadoPublicacion.id !== undefined) {
      this.subscribeToSaveResponse(this.estadoPublicacionService.update(estadoPublicacion));
    } else {
      this.subscribeToSaveResponse(this.estadoPublicacionService.create(estadoPublicacion));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEstadoPublicacion>>): void {
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

  protected updateForm(estadoPublicacion: IEstadoPublicacion): void {
    this.editForm.patchValue({
      id: estadoPublicacion.id,
      nombre: estadoPublicacion.nombre,
    });
  }

  protected createFromForm(): IEstadoPublicacion {
    return {
      ...new EstadoPublicacion(),
      id: this.editForm.get(['id'])!.value,
      nombre: this.editForm.get(['nombre'])!.value,
    };
  }
}
