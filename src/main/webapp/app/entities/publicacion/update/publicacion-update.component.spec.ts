jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { PublicacionService } from '../service/publicacion.service';
import { IPublicacion, Publicacion } from '../publicacion.model';
import { ICategoriaPublicacion } from 'app/entities/categoria-publicacion/categoria-publicacion.model';
import { CategoriaPublicacionService } from 'app/entities/categoria-publicacion/service/categoria-publicacion.service';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { IEstadoPublicacion } from 'app/entities/estado-publicacion/estado-publicacion.model';
import { EstadoPublicacionService } from 'app/entities/estado-publicacion/service/estado-publicacion.service';

import { PublicacionUpdateComponent } from './publicacion-update.component';

describe('Component Tests', () => {
  describe('Publicacion Management Update Component', () => {
    let comp: PublicacionUpdateComponent;
    let fixture: ComponentFixture<PublicacionUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let publicacionService: PublicacionService;
    let categoriaPublicacionService: CategoriaPublicacionService;
    let userService: UserService;
    let estadoPublicacionService: EstadoPublicacionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [PublicacionUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(PublicacionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PublicacionUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      publicacionService = TestBed.inject(PublicacionService);
      categoriaPublicacionService = TestBed.inject(CategoriaPublicacionService);
      userService = TestBed.inject(UserService);
      estadoPublicacionService = TestBed.inject(EstadoPublicacionService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call CategoriaPublicacion query and add missing value', () => {
        const publicacion: IPublicacion = { id: 456 };
        const categoriaPublicacion: ICategoriaPublicacion = { id: 87320 };
        publicacion.categoriaPublicacion = categoriaPublicacion;

        const categoriaPublicacionCollection: ICategoriaPublicacion[] = [{ id: 24183 }];
        jest.spyOn(categoriaPublicacionService, 'query').mockReturnValue(of(new HttpResponse({ body: categoriaPublicacionCollection })));
        const additionalCategoriaPublicacions = [categoriaPublicacion];
        const expectedCollection: ICategoriaPublicacion[] = [...additionalCategoriaPublicacions, ...categoriaPublicacionCollection];
        jest.spyOn(categoriaPublicacionService, 'addCategoriaPublicacionToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ publicacion });
        comp.ngOnInit();

        expect(categoriaPublicacionService.query).toHaveBeenCalled();
        expect(categoriaPublicacionService.addCategoriaPublicacionToCollectionIfMissing).toHaveBeenCalledWith(
          categoriaPublicacionCollection,
          ...additionalCategoriaPublicacions
        );
        expect(comp.categoriaPublicacionsSharedCollection).toEqual(expectedCollection);
      });

      it('Should call User query and add missing value', () => {
        const publicacion: IPublicacion = { id: 456 };
        const user: IUser = { id: 83099 };
        publicacion.user = user;

        const userCollection: IUser[] = [{ id: 61294 }];
        jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
        const additionalUsers = [user];
        const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
        jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ publicacion });
        comp.ngOnInit();

        expect(userService.query).toHaveBeenCalled();
        expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(userCollection, ...additionalUsers);
        expect(comp.usersSharedCollection).toEqual(expectedCollection);
      });

      it('Should call EstadoPublicacion query and add missing value', () => {
        const publicacion: IPublicacion = { id: 456 };
        const estado: IEstadoPublicacion = { id: 36094 };
        publicacion.estado = estado;

        const estadoPublicacionCollection: IEstadoPublicacion[] = [{ id: 15262 }];
        jest.spyOn(estadoPublicacionService, 'query').mockReturnValue(of(new HttpResponse({ body: estadoPublicacionCollection })));
        const additionalEstadoPublicacions = [estado];
        const expectedCollection: IEstadoPublicacion[] = [...additionalEstadoPublicacions, ...estadoPublicacionCollection];
        jest.spyOn(estadoPublicacionService, 'addEstadoPublicacionToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ publicacion });
        comp.ngOnInit();

        expect(estadoPublicacionService.query).toHaveBeenCalled();
        expect(estadoPublicacionService.addEstadoPublicacionToCollectionIfMissing).toHaveBeenCalledWith(
          estadoPublicacionCollection,
          ...additionalEstadoPublicacions
        );
        expect(comp.estadoPublicacionsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const publicacion: IPublicacion = { id: 456 };
        const categoriaPublicacion: ICategoriaPublicacion = { id: 16150 };
        publicacion.categoriaPublicacion = categoriaPublicacion;
        const user: IUser = { id: 76554 };
        publicacion.user = user;
        const estado: IEstadoPublicacion = { id: 61890 };
        publicacion.estado = estado;

        activatedRoute.data = of({ publicacion });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(publicacion));
        expect(comp.categoriaPublicacionsSharedCollection).toContain(categoriaPublicacion);
        expect(comp.usersSharedCollection).toContain(user);
        expect(comp.estadoPublicacionsSharedCollection).toContain(estado);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Publicacion>>();
        const publicacion = { id: 123 };
        jest.spyOn(publicacionService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ publicacion });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: publicacion }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(publicacionService.update).toHaveBeenCalledWith(publicacion);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Publicacion>>();
        const publicacion = new Publicacion();
        jest.spyOn(publicacionService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ publicacion });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: publicacion }));
        saveSubject.complete();

        // THEN
        expect(publicacionService.create).toHaveBeenCalledWith(publicacion);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Publicacion>>();
        const publicacion = { id: 123 };
        jest.spyOn(publicacionService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ publicacion });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(publicacionService.update).toHaveBeenCalledWith(publicacion);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackCategoriaPublicacionById', () => {
        it('Should return tracked CategoriaPublicacion primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackCategoriaPublicacionById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackUserById', () => {
        it('Should return tracked User primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackUserById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackEstadoPublicacionById', () => {
        it('Should return tracked EstadoPublicacion primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackEstadoPublicacionById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
