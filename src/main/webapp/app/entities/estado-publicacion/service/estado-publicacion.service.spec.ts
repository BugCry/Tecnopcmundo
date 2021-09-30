import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IEstadoPublicacion, EstadoPublicacion } from '../estado-publicacion.model';

import { EstadoPublicacionService } from './estado-publicacion.service';

describe('Service Tests', () => {
  describe('EstadoPublicacion Service', () => {
    let service: EstadoPublicacionService;
    let httpMock: HttpTestingController;
    let elemDefault: IEstadoPublicacion;
    let expectedResult: IEstadoPublicacion | IEstadoPublicacion[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(EstadoPublicacionService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        nombre: 'AAAAAAA',
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a EstadoPublicacion', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new EstadoPublicacion()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a EstadoPublicacion', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            nombre: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a EstadoPublicacion', () => {
        const patchObject = Object.assign({}, new EstadoPublicacion());

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of EstadoPublicacion', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            nombre: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a EstadoPublicacion', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addEstadoPublicacionToCollectionIfMissing', () => {
        it('should add a EstadoPublicacion to an empty array', () => {
          const estadoPublicacion: IEstadoPublicacion = { id: 123 };
          expectedResult = service.addEstadoPublicacionToCollectionIfMissing([], estadoPublicacion);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(estadoPublicacion);
        });

        it('should not add a EstadoPublicacion to an array that contains it', () => {
          const estadoPublicacion: IEstadoPublicacion = { id: 123 };
          const estadoPublicacionCollection: IEstadoPublicacion[] = [
            {
              ...estadoPublicacion,
            },
            { id: 456 },
          ];
          expectedResult = service.addEstadoPublicacionToCollectionIfMissing(estadoPublicacionCollection, estadoPublicacion);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a EstadoPublicacion to an array that doesn't contain it", () => {
          const estadoPublicacion: IEstadoPublicacion = { id: 123 };
          const estadoPublicacionCollection: IEstadoPublicacion[] = [{ id: 456 }];
          expectedResult = service.addEstadoPublicacionToCollectionIfMissing(estadoPublicacionCollection, estadoPublicacion);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(estadoPublicacion);
        });

        it('should add only unique EstadoPublicacion to an array', () => {
          const estadoPublicacionArray: IEstadoPublicacion[] = [{ id: 123 }, { id: 456 }, { id: 23856 }];
          const estadoPublicacionCollection: IEstadoPublicacion[] = [{ id: 123 }];
          expectedResult = service.addEstadoPublicacionToCollectionIfMissing(estadoPublicacionCollection, ...estadoPublicacionArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const estadoPublicacion: IEstadoPublicacion = { id: 123 };
          const estadoPublicacion2: IEstadoPublicacion = { id: 456 };
          expectedResult = service.addEstadoPublicacionToCollectionIfMissing([], estadoPublicacion, estadoPublicacion2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(estadoPublicacion);
          expect(expectedResult).toContain(estadoPublicacion2);
        });

        it('should accept null and undefined values', () => {
          const estadoPublicacion: IEstadoPublicacion = { id: 123 };
          expectedResult = service.addEstadoPublicacionToCollectionIfMissing([], null, estadoPublicacion, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(estadoPublicacion);
        });

        it('should return initial array if no EstadoPublicacion is added', () => {
          const estadoPublicacionCollection: IEstadoPublicacion[] = [{ id: 123 }];
          expectedResult = service.addEstadoPublicacionToCollectionIfMissing(estadoPublicacionCollection, undefined, null);
          expect(expectedResult).toEqual(estadoPublicacionCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
