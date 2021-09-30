jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ProductoService } from '../service/producto.service';
import { IProducto, Producto } from '../producto.model';
import { IPrecio } from 'app/entities/precio/precio.model';
import { PrecioService } from 'app/entities/precio/service/precio.service';
import { ICategoria } from 'app/entities/categoria/categoria.model';
import { CategoriaService } from 'app/entities/categoria/service/categoria.service';
import { IProveedor } from 'app/entities/proveedor/proveedor.model';
import { ProveedorService } from 'app/entities/proveedor/service/proveedor.service';
import { IEstado } from 'app/entities/estado/estado.model';
import { EstadoService } from 'app/entities/estado/service/estado.service';

import { ProductoUpdateComponent } from './producto-update.component';

describe('Component Tests', () => {
  describe('Producto Management Update Component', () => {
    let comp: ProductoUpdateComponent;
    let fixture: ComponentFixture<ProductoUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let productoService: ProductoService;
    let precioService: PrecioService;
    let categoriaService: CategoriaService;
    let proveedorService: ProveedorService;
    let estadoService: EstadoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ProductoUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(ProductoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProductoUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      productoService = TestBed.inject(ProductoService);
      precioService = TestBed.inject(PrecioService);
      categoriaService = TestBed.inject(CategoriaService);
      proveedorService = TestBed.inject(ProveedorService);
      estadoService = TestBed.inject(EstadoService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call precio query and add missing value', () => {
        const producto: IProducto = { id: 456 };
        const precio: IPrecio = { id: 13869 };
        producto.precio = precio;

        const precioCollection: IPrecio[] = [{ id: 23258 }];
        jest.spyOn(precioService, 'query').mockReturnValue(of(new HttpResponse({ body: precioCollection })));
        const expectedCollection: IPrecio[] = [precio, ...precioCollection];
        jest.spyOn(precioService, 'addPrecioToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ producto });
        comp.ngOnInit();

        expect(precioService.query).toHaveBeenCalled();
        expect(precioService.addPrecioToCollectionIfMissing).toHaveBeenCalledWith(precioCollection, precio);
        expect(comp.preciosCollection).toEqual(expectedCollection);
      });

      it('Should call Categoria query and add missing value', () => {
        const producto: IProducto = { id: 456 };
        const categoria: ICategoria = { id: 42528 };
        producto.categoria = categoria;

        const categoriaCollection: ICategoria[] = [{ id: 80339 }];
        jest.spyOn(categoriaService, 'query').mockReturnValue(of(new HttpResponse({ body: categoriaCollection })));
        const additionalCategorias = [categoria];
        const expectedCollection: ICategoria[] = [...additionalCategorias, ...categoriaCollection];
        jest.spyOn(categoriaService, 'addCategoriaToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ producto });
        comp.ngOnInit();

        expect(categoriaService.query).toHaveBeenCalled();
        expect(categoriaService.addCategoriaToCollectionIfMissing).toHaveBeenCalledWith(categoriaCollection, ...additionalCategorias);
        expect(comp.categoriasSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Proveedor query and add missing value', () => {
        const producto: IProducto = { id: 456 };
        const proveedor: IProveedor = { id: 1713 };
        producto.proveedor = proveedor;

        const proveedorCollection: IProveedor[] = [{ id: 89565 }];
        jest.spyOn(proveedorService, 'query').mockReturnValue(of(new HttpResponse({ body: proveedorCollection })));
        const additionalProveedors = [proveedor];
        const expectedCollection: IProveedor[] = [...additionalProveedors, ...proveedorCollection];
        jest.spyOn(proveedorService, 'addProveedorToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ producto });
        comp.ngOnInit();

        expect(proveedorService.query).toHaveBeenCalled();
        expect(proveedorService.addProveedorToCollectionIfMissing).toHaveBeenCalledWith(proveedorCollection, ...additionalProveedors);
        expect(comp.proveedorsSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Estado query and add missing value', () => {
        const producto: IProducto = { id: 456 };
        const estado: IEstado = { id: 65211 };
        producto.estado = estado;

        const estadoCollection: IEstado[] = [{ id: 37315 }];
        jest.spyOn(estadoService, 'query').mockReturnValue(of(new HttpResponse({ body: estadoCollection })));
        const additionalEstados = [estado];
        const expectedCollection: IEstado[] = [...additionalEstados, ...estadoCollection];
        jest.spyOn(estadoService, 'addEstadoToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ producto });
        comp.ngOnInit();

        expect(estadoService.query).toHaveBeenCalled();
        expect(estadoService.addEstadoToCollectionIfMissing).toHaveBeenCalledWith(estadoCollection, ...additionalEstados);
        expect(comp.estadosSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const producto: IProducto = { id: 456 };
        const precio: IPrecio = { id: 29053 };
        producto.precio = precio;
        const categoria: ICategoria = { id: 71648 };
        producto.categoria = categoria;
        const proveedor: IProveedor = { id: 21877 };
        producto.proveedor = proveedor;
        const estado: IEstado = { id: 79364 };
        producto.estado = estado;

        activatedRoute.data = of({ producto });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(producto));
        expect(comp.preciosCollection).toContain(precio);
        expect(comp.categoriasSharedCollection).toContain(categoria);
        expect(comp.proveedorsSharedCollection).toContain(proveedor);
        expect(comp.estadosSharedCollection).toContain(estado);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Producto>>();
        const producto = { id: 123 };
        jest.spyOn(productoService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ producto });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: producto }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(productoService.update).toHaveBeenCalledWith(producto);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Producto>>();
        const producto = new Producto();
        jest.spyOn(productoService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ producto });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: producto }));
        saveSubject.complete();

        // THEN
        expect(productoService.create).toHaveBeenCalledWith(producto);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Producto>>();
        const producto = { id: 123 };
        jest.spyOn(productoService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ producto });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(productoService.update).toHaveBeenCalledWith(producto);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackPrecioById', () => {
        it('Should return tracked Precio primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackPrecioById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackCategoriaById', () => {
        it('Should return tracked Categoria primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackCategoriaById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackProveedorById', () => {
        it('Should return tracked Proveedor primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackProveedorById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackEstadoById', () => {
        it('Should return tracked Estado primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackEstadoById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
