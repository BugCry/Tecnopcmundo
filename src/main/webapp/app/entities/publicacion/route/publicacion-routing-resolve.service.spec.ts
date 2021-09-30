jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IPublicacion, Publicacion } from '../publicacion.model';
import { PublicacionService } from '../service/publicacion.service';

import { PublicacionRoutingResolveService } from './publicacion-routing-resolve.service';

describe('Service Tests', () => {
  describe('Publicacion routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: PublicacionRoutingResolveService;
    let service: PublicacionService;
    let resultPublicacion: IPublicacion | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(PublicacionRoutingResolveService);
      service = TestBed.inject(PublicacionService);
      resultPublicacion = undefined;
    });

    describe('resolve', () => {
      it('should return IPublicacion returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultPublicacion = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultPublicacion).toEqual({ id: 123 });
      });

      it('should return new IPublicacion if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultPublicacion = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultPublicacion).toEqual(new Publicacion());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as Publicacion })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultPublicacion = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultPublicacion).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
