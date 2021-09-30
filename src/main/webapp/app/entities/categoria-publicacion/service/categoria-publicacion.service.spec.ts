import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ICategoriaPublicacion, CategoriaPublicacion } from '../categoria-publicacion.model';

import { CategoriaPublicacionService } from './categoria-publicacion.service';

describe('Service Tests', () => {
  describe('CategoriaPublicacion Service', () => {
    let service: CategoriaPublicacionService;
    let httpMock: HttpTestingController;
    let elemDefault: ICategoriaPublicacion;
    let expectedResult: ICategoriaPublicacion | ICategoriaPublicacion[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(CategoriaPublicacionService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        titulo: 'AAAAAAA',
        descripcion: 'AAAAAAA',
        createAt: currentDate,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            createAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a CategoriaPublicacion', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            createAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            createAt: currentDate,
          },
          returnedFromService
        );

        service.create(new CategoriaPublicacion()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a CategoriaPublicacion', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            titulo: 'BBBBBB',
            descripcion: 'BBBBBB',
            createAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            createAt: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a CategoriaPublicacion', () => {
        const patchObject = Object.assign(
          {
            descripcion: 'BBBBBB',
            createAt: currentDate.format(DATE_TIME_FORMAT),
          },
          new CategoriaPublicacion()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            createAt: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of CategoriaPublicacion', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            titulo: 'BBBBBB',
            descripcion: 'BBBBBB',
            createAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            createAt: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a CategoriaPublicacion', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addCategoriaPublicacionToCollectionIfMissing', () => {
        it('should add a CategoriaPublicacion to an empty array', () => {
          const categoriaPublicacion: ICategoriaPublicacion = { id: 123 };
          expectedResult = service.addCategoriaPublicacionToCollectionIfMissing([], categoriaPublicacion);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(categoriaPublicacion);
        });

        it('should not add a CategoriaPublicacion to an array that contains it', () => {
          const categoriaPublicacion: ICategoriaPublicacion = { id: 123 };
          const categoriaPublicacionCollection: ICategoriaPublicacion[] = [
            {
              ...categoriaPublicacion,
            },
            { id: 456 },
          ];
          expectedResult = service.addCategoriaPublicacionToCollectionIfMissing(categoriaPublicacionCollection, categoriaPublicacion);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a CategoriaPublicacion to an array that doesn't contain it", () => {
          const categoriaPublicacion: ICategoriaPublicacion = { id: 123 };
          const categoriaPublicacionCollection: ICategoriaPublicacion[] = [{ id: 456 }];
          expectedResult = service.addCategoriaPublicacionToCollectionIfMissing(categoriaPublicacionCollection, categoriaPublicacion);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(categoriaPublicacion);
        });

        it('should add only unique CategoriaPublicacion to an array', () => {
          const categoriaPublicacionArray: ICategoriaPublicacion[] = [{ id: 123 }, { id: 456 }, { id: 93675 }];
          const categoriaPublicacionCollection: ICategoriaPublicacion[] = [{ id: 123 }];
          expectedResult = service.addCategoriaPublicacionToCollectionIfMissing(
            categoriaPublicacionCollection,
            ...categoriaPublicacionArray
          );
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const categoriaPublicacion: ICategoriaPublicacion = { id: 123 };
          const categoriaPublicacion2: ICategoriaPublicacion = { id: 456 };
          expectedResult = service.addCategoriaPublicacionToCollectionIfMissing([], categoriaPublicacion, categoriaPublicacion2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(categoriaPublicacion);
          expect(expectedResult).toContain(categoriaPublicacion2);
        });

        it('should accept null and undefined values', () => {
          const categoriaPublicacion: ICategoriaPublicacion = { id: 123 };
          expectedResult = service.addCategoriaPublicacionToCollectionIfMissing([], null, categoriaPublicacion, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(categoriaPublicacion);
        });

        it('should return initial array if no CategoriaPublicacion is added', () => {
          const categoriaPublicacionCollection: ICategoriaPublicacion[] = [{ id: 123 }];
          expectedResult = service.addCategoriaPublicacionToCollectionIfMissing(categoriaPublicacionCollection, undefined, null);
          expect(expectedResult).toEqual(categoriaPublicacionCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
