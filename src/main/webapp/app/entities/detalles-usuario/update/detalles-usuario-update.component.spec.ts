jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { DetallesUsuarioService } from '../service/detalles-usuario.service';
import { IDetallesUsuario, DetallesUsuario } from '../detalles-usuario.model';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';

import { DetallesUsuarioUpdateComponent } from './detalles-usuario-update.component';

describe('Component Tests', () => {
  describe('DetallesUsuario Management Update Component', () => {
    let comp: DetallesUsuarioUpdateComponent;
    let fixture: ComponentFixture<DetallesUsuarioUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let detallesUsuarioService: DetallesUsuarioService;
    let userService: UserService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [DetallesUsuarioUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(DetallesUsuarioUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DetallesUsuarioUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      detallesUsuarioService = TestBed.inject(DetallesUsuarioService);
      userService = TestBed.inject(UserService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call User query and add missing value', () => {
        const detallesUsuario: IDetallesUsuario = { id: 456 };
        const user: IUser = { id: 83947 };
        detallesUsuario.user = user;

        const userCollection: IUser[] = [{ id: 86638 }];
        jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
        const additionalUsers = [user];
        const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
        jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ detallesUsuario });
        comp.ngOnInit();

        expect(userService.query).toHaveBeenCalled();
        expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(userCollection, ...additionalUsers);
        expect(comp.usersSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const detallesUsuario: IDetallesUsuario = { id: 456 };
        const user: IUser = { id: 88742 };
        detallesUsuario.user = user;

        activatedRoute.data = of({ detallesUsuario });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(detallesUsuario));
        expect(comp.usersSharedCollection).toContain(user);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<DetallesUsuario>>();
        const detallesUsuario = { id: 123 };
        jest.spyOn(detallesUsuarioService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ detallesUsuario });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: detallesUsuario }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(detallesUsuarioService.update).toHaveBeenCalledWith(detallesUsuario);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<DetallesUsuario>>();
        const detallesUsuario = new DetallesUsuario();
        jest.spyOn(detallesUsuarioService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ detallesUsuario });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: detallesUsuario }));
        saveSubject.complete();

        // THEN
        expect(detallesUsuarioService.create).toHaveBeenCalledWith(detallesUsuario);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<DetallesUsuario>>();
        const detallesUsuario = { id: 123 };
        jest.spyOn(detallesUsuarioService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ detallesUsuario });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(detallesUsuarioService.update).toHaveBeenCalledWith(detallesUsuario);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackUserById', () => {
        it('Should return tracked User primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackUserById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
