jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { CategoriaPublicacionService } from '../service/categoria-publicacion.service';
import { ICategoriaPublicacion, CategoriaPublicacion } from '../categoria-publicacion.model';

import { CategoriaPublicacionUpdateComponent } from './categoria-publicacion-update.component';

describe('Component Tests', () => {
  describe('CategoriaPublicacion Management Update Component', () => {
    let comp: CategoriaPublicacionUpdateComponent;
    let fixture: ComponentFixture<CategoriaPublicacionUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let categoriaPublicacionService: CategoriaPublicacionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [CategoriaPublicacionUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(CategoriaPublicacionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CategoriaPublicacionUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      categoriaPublicacionService = TestBed.inject(CategoriaPublicacionService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const categoriaPublicacion: ICategoriaPublicacion = { id: 456 };

        activatedRoute.data = of({ categoriaPublicacion });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(categoriaPublicacion));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<CategoriaPublicacion>>();
        const categoriaPublicacion = { id: 123 };
        jest.spyOn(categoriaPublicacionService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ categoriaPublicacion });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: categoriaPublicacion }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(categoriaPublicacionService.update).toHaveBeenCalledWith(categoriaPublicacion);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<CategoriaPublicacion>>();
        const categoriaPublicacion = new CategoriaPublicacion();
        jest.spyOn(categoriaPublicacionService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ categoriaPublicacion });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: categoriaPublicacion }));
        saveSubject.complete();

        // THEN
        expect(categoriaPublicacionService.create).toHaveBeenCalledWith(categoriaPublicacion);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<CategoriaPublicacion>>();
        const categoriaPublicacion = { id: 123 };
        jest.spyOn(categoriaPublicacionService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ categoriaPublicacion });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(categoriaPublicacionService.update).toHaveBeenCalledWith(categoriaPublicacion);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
