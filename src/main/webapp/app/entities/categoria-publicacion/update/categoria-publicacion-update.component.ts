import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ICategoriaPublicacion, CategoriaPublicacion } from '../categoria-publicacion.model';
import { CategoriaPublicacionService } from '../service/categoria-publicacion.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-categoria-publicacion-update',
  templateUrl: './categoria-publicacion-update.component.html',
})
export class CategoriaPublicacionUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    titulo: [],
    descripcion: [],
    createAt: [],
  });

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected categoriaPublicacionService: CategoriaPublicacionService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ categoriaPublicacion }) => {
      if (categoriaPublicacion.id === undefined) {
        const today = dayjs().startOf('seconds');
        categoriaPublicacion.createAt = today;
      }

      this.updateForm(categoriaPublicacion);
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(
          new EventWithContent<AlertError>('inventarioTecnoPcMundoApp.error', { ...err, key: 'error.file.' + err.key })
        ),
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const categoriaPublicacion = this.createFromForm();
    if (categoriaPublicacion.id !== undefined) {
      this.subscribeToSaveResponse(this.categoriaPublicacionService.update(categoriaPublicacion));
    } else {
      this.subscribeToSaveResponse(this.categoriaPublicacionService.create(categoriaPublicacion));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICategoriaPublicacion>>): void {
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

  protected updateForm(categoriaPublicacion: ICategoriaPublicacion): void {
    this.editForm.patchValue({
      id: categoriaPublicacion.id,
      titulo: categoriaPublicacion.titulo,
      descripcion: categoriaPublicacion.descripcion,
      createAt: categoriaPublicacion.createAt ? categoriaPublicacion.createAt.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): ICategoriaPublicacion {
    return {
      ...new CategoriaPublicacion(),
      id: this.editForm.get(['id'])!.value,
      titulo: this.editForm.get(['titulo'])!.value,
      descripcion: this.editForm.get(['descripcion'])!.value,
      createAt: this.editForm.get(['createAt'])!.value ? dayjs(this.editForm.get(['createAt'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }
}
