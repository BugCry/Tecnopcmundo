jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ICategoriaPublicacion, CategoriaPublicacion } from '../categoria-publicacion.model';
import { CategoriaPublicacionService } from '../service/categoria-publicacion.service';

import { CategoriaPublicacionRoutingResolveService } from './categoria-publicacion-routing-resolve.service';

describe('Service Tests', () => {
  describe('CategoriaPublicacion routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: CategoriaPublicacionRoutingResolveService;
    let service: CategoriaPublicacionService;
    let resultCategoriaPublicacion: ICategoriaPublicacion | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(CategoriaPublicacionRoutingResolveService);
      service = TestBed.inject(CategoriaPublicacionService);
      resultCategoriaPublicacion = undefined;
    });

    describe('resolve', () => {
      it('should return ICategoriaPublicacion returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCategoriaPublicacion = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultCategoriaPublicacion).toEqual({ id: 123 });
      });

      it('should return new ICategoriaPublicacion if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCategoriaPublicacion = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultCategoriaPublicacion).toEqual(new CategoriaPublicacion());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as CategoriaPublicacion })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCategoriaPublicacion = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultCategoriaPublicacion).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
