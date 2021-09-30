import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IDetallesUsuario, DetallesUsuario } from '../detalles-usuario.model';
import { DetallesUsuarioService } from '../service/detalles-usuario.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';

@Component({
  selector: 'jhi-detalles-usuario-update',
  templateUrl: './detalles-usuario-update.component.html',
})
export class DetallesUsuarioUpdateComponent implements OnInit {
  isSaving = false;

  usersSharedCollection: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    telefono: [],
    identificacion: [],
    ciudad: [],
    user: [],
  });

  constructor(
    protected detallesUsuarioService: DetallesUsuarioService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ detallesUsuario }) => {
      this.updateForm(detallesUsuario);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const detallesUsuario = this.createFromForm();
    if (detallesUsuario.id !== undefined) {
      this.subscribeToSaveResponse(this.detallesUsuarioService.update(detallesUsuario));
    } else {
      this.subscribeToSaveResponse(this.detallesUsuarioService.create(detallesUsuario));
    }
  }

  trackUserById(index: number, item: IUser): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDetallesUsuario>>): void {
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

  protected updateForm(detallesUsuario: IDetallesUsuario): void {
    this.editForm.patchValue({
      id: detallesUsuario.id,
      telefono: detallesUsuario.telefono,
      identificacion: detallesUsuario.identificacion,
      ciudad: detallesUsuario.ciudad,
      user: detallesUsuario.user,
    });

    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing(this.usersSharedCollection, detallesUsuario.user);
  }

  protected loadRelationshipsOptions(): void {
    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing(users, this.editForm.get('user')!.value)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));
  }

  protected createFromForm(): IDetallesUsuario {
    return {
      ...new DetallesUsuario(),
      id: this.editForm.get(['id'])!.value,
      telefono: this.editForm.get(['telefono'])!.value,
      identificacion: this.editForm.get(['identificacion'])!.value,
      ciudad: this.editForm.get(['ciudad'])!.value,
      user: this.editForm.get(['user'])!.value,
    };
  }
}
