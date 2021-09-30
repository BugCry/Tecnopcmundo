import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IPublicacion, Publicacion } from '../publicacion.model';
import { PublicacionService } from '../service/publicacion.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { ICategoriaPublicacion } from 'app/entities/categoria-publicacion/categoria-publicacion.model';
import { CategoriaPublicacionService } from 'app/entities/categoria-publicacion/service/categoria-publicacion.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { IEstadoPublicacion } from 'app/entities/estado-publicacion/estado-publicacion.model';
import { EstadoPublicacionService } from 'app/entities/estado-publicacion/service/estado-publicacion.service';

@Component({
  selector: 'jhi-publicacion-update',
  templateUrl: './publicacion-update.component.html',
})
export class PublicacionUpdateComponent implements OnInit {
  isSaving = false;

  categoriaPublicacionsSharedCollection: ICategoriaPublicacion[] = [];
  usersSharedCollection: IUser[] = [];
  estadoPublicacionsSharedCollection: IEstadoPublicacion[] = [];

  editForm = this.fb.group({
    id: [],
    titulo: [],
    descripcion: [],
    imagen: [],
    imagenContentType: [],
    createAt: [],
    categoriaPublicacion: [],
    user: [],
    estado: [],
  });

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected publicacionService: PublicacionService,
    protected categoriaPublicacionService: CategoriaPublicacionService,
    protected userService: UserService,
    protected estadoPublicacionService: EstadoPublicacionService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ publicacion }) => {
      if (publicacion.id === undefined) {
        const today = dayjs().startOf('seconds');
        publicacion.createAt = today;
      }

      this.updateForm(publicacion);

      this.loadRelationshipsOptions();
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

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const publicacion = this.createFromForm();
    if (publicacion.id !== undefined) {
      this.subscribeToSaveResponse(this.publicacionService.update(publicacion));
    } else {
      this.subscribeToSaveResponse(this.publicacionService.create(publicacion));
    }
  }

  trackCategoriaPublicacionById(index: number, item: ICategoriaPublicacion): number {
    return item.id!;
  }

  trackUserById(index: number, item: IUser): number {
    return item.id!;
  }

  trackEstadoPublicacionById(index: number, item: IEstadoPublicacion): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPublicacion>>): void {
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

  protected updateForm(publicacion: IPublicacion): void {
    this.editForm.patchValue({
      id: publicacion.id,
      titulo: publicacion.titulo,
      descripcion: publicacion.descripcion,
      imagen: publicacion.imagen,
      imagenContentType: publicacion.imagenContentType,
      createAt: publicacion.createAt ? publicacion.createAt.format(DATE_TIME_FORMAT) : null,
      categoriaPublicacion: publicacion.categoriaPublicacion,
      user: publicacion.user,
      estado: publicacion.estado,
    });

    this.categoriaPublicacionsSharedCollection = this.categoriaPublicacionService.addCategoriaPublicacionToCollectionIfMissing(
      this.categoriaPublicacionsSharedCollection,
      publicacion.categoriaPublicacion
    );
    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing(this.usersSharedCollection, publicacion.user);
    this.estadoPublicacionsSharedCollection = this.estadoPublicacionService.addEstadoPublicacionToCollectionIfMissing(
      this.estadoPublicacionsSharedCollection,
      publicacion.estado
    );
  }

  protected loadRelationshipsOptions(): void {
    this.categoriaPublicacionService
      .query()
      .pipe(map((res: HttpResponse<ICategoriaPublicacion[]>) => res.body ?? []))
      .pipe(
        map((categoriaPublicacions: ICategoriaPublicacion[]) =>
          this.categoriaPublicacionService.addCategoriaPublicacionToCollectionIfMissing(
            categoriaPublicacions,
            this.editForm.get('categoriaPublicacion')!.value
          )
        )
      )
      .subscribe((categoriaPublicacions: ICategoriaPublicacion[]) => (this.categoriaPublicacionsSharedCollection = categoriaPublicacions));

    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing(users, this.editForm.get('user')!.value)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));

    this.estadoPublicacionService
      .query()
      .pipe(map((res: HttpResponse<IEstadoPublicacion[]>) => res.body ?? []))
      .pipe(
        map((estadoPublicacions: IEstadoPublicacion[]) =>
          this.estadoPublicacionService.addEstadoPublicacionToCollectionIfMissing(estadoPublicacions, this.editForm.get('estado')!.value)
        )
      )
      .subscribe((estadoPublicacions: IEstadoPublicacion[]) => (this.estadoPublicacionsSharedCollection = estadoPublicacions));
  }

  protected createFromForm(): IPublicacion {
    return {
      ...new Publicacion(),
      id: this.editForm.get(['id'])!.value,
      titulo: this.editForm.get(['titulo'])!.value,
      descripcion: this.editForm.get(['descripcion'])!.value,
      imagenContentType: this.editForm.get(['imagenContentType'])!.value,
      imagen: this.editForm.get(['imagen'])!.value,
      createAt: this.editForm.get(['createAt'])!.value ? dayjs(this.editForm.get(['createAt'])!.value, DATE_TIME_FORMAT) : undefined,
      categoriaPublicacion: this.editForm.get(['categoriaPublicacion'])!.value,
      user: this.editForm.get(['user'])!.value,
      estado: this.editForm.get(['estado'])!.value,
    };
  }
}
