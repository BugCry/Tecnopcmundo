jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { CompraService } from '../service/compra.service';
import { ICompra, Compra } from '../compra.model';
import { IDetallesUsuario } from 'app/entities/detalles-usuario/detalles-usuario.model';
import { DetallesUsuarioService } from 'app/entities/detalles-usuario/service/detalles-usuario.service';

import { CompraUpdateComponent } from './compra-update.component';

describe('Component Tests', () => {
  describe('Compra Management Update Component', () => {
    let comp: CompraUpdateComponent;
    let fixture: ComponentFixture<CompraUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let compraService: CompraService;
    let detallesUsuarioService: DetallesUsuarioService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [CompraUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(CompraUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CompraUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      compraService = TestBed.inject(CompraService);
      detallesUsuarioService = TestBed.inject(DetallesUsuarioService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call DetallesUsuario query and add missing value', () => {
        const compra: ICompra = { id: 456 };
        const user: IDetallesUsuario = { id: 37297 };
        compra.user = user;

        const detallesUsuarioCollection: IDetallesUsuario[] = [{ id: 74990 }];
        jest.spyOn(detallesUsuarioService, 'query').mockReturnValue(of(new HttpResponse({ body: detallesUsuarioCollection })));
        const additionalDetallesUsuarios = [user];
        const expectedCollection: IDetallesUsuario[] = [...additionalDetallesUsuarios, ...detallesUsuarioCollection];
        jest.spyOn(detallesUsuarioService, 'addDetallesUsuarioToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ compra });
        comp.ngOnInit();

        expect(detallesUsuarioService.query).toHaveBeenCalled();
        expect(detallesUsuarioService.addDetallesUsuarioToCollectionIfMissing).toHaveBeenCalledWith(
          detallesUsuarioCollection,
          ...additionalDetallesUsuarios
        );
        expect(comp.detallesUsuariosSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const compra: ICompra = { id: 456 };
        const user: IDetallesUsuario = { id: 44384 };
        compra.user = user;

        activatedRoute.data = of({ compra });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(compra));
        expect(comp.detallesUsuariosSharedCollection).toContain(user);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Compra>>();
        const compra = { id: 123 };
        jest.spyOn(compraService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ compra });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: compra }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(compraService.update).toHaveBeenCalledWith(compra);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Compra>>();
        const compra = new Compra();
        jest.spyOn(compraService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ compra });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: compra }));
        saveSubject.complete();

        // THEN
        expect(compraService.create).toHaveBeenCalledWith(compra);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Compra>>();
        const compra = { id: 123 };
        jest.spyOn(compraService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ compra });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(compraService.update).toHaveBeenCalledWith(compra);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackDetallesUsuarioById', () => {
        it('Should return tracked DetallesUsuario primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackDetallesUsuarioById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
