import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IPublicacion, Publicacion } from '../publicacion.model';

import { PublicacionService } from './publicacion.service';

describe('Service Tests', () => {
  describe('Publicacion Service', () => {
    let service: PublicacionService;
    let httpMock: HttpTestingController;
    let elemDefault: IPublicacion;
    let expectedResult: IPublicacion | IPublicacion[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(PublicacionService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        titulo: 'AAAAAAA',
        descripcion: 'AAAAAAA',
        imagenContentType: 'image/png',
        imagen: 'AAAAAAA',
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

      it('should create a Publicacion', () => {
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

        service.create(new Publicacion()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Publicacion', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            titulo: 'BBBBBB',
            descripcion: 'BBBBBB',
            imagen: 'BBBBBB',
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

      it('should partial update a Publicacion', () => {
        const patchObject = Object.assign(
          {
            imagen: 'BBBBBB',
            createAt: currentDate.format(DATE_TIME_FORMAT),
          },
          new Publicacion()
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

      it('should return a list of Publicacion', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            titulo: 'BBBBBB',
            descripcion: 'BBBBBB',
            imagen: 'BBBBBB',
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

      it('should delete a Publicacion', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addPublicacionToCollectionIfMissing', () => {
        it('should add a Publicacion to an empty array', () => {
          const publicacion: IPublicacion = { id: 123 };
          expectedResult = service.addPublicacionToCollectionIfMissing([], publicacion);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(publicacion);
        });

        it('should not add a Publicacion to an array that contains it', () => {
          const publicacion: IPublicacion = { id: 123 };
          const publicacionCollection: IPublicacion[] = [
            {
              ...publicacion,
            },
            { id: 456 },
          ];
          expectedResult = service.addPublicacionToCollectionIfMissing(publicacionCollection, publicacion);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Publicacion to an array that doesn't contain it", () => {
          const publicacion: IPublicacion = { id: 123 };
          const publicacionCollection: IPublicacion[] = [{ id: 456 }];
          expectedResult = service.addPublicacionToCollectionIfMissing(publicacionCollection, publicacion);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(publicacion);
        });

        it('should add only unique Publicacion to an array', () => {
          const publicacionArray: IPublicacion[] = [{ id: 123 }, { id: 456 }, { id: 73112 }];
          const publicacionCollection: IPublicacion[] = [{ id: 123 }];
          expectedResult = service.addPublicacionToCollectionIfMissing(publicacionCollection, ...publicacionArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const publicacion: IPublicacion = { id: 123 };
          const publicacion2: IPublicacion = { id: 456 };
          expectedResult = service.addPublicacionToCollectionIfMissing([], publicacion, publicacion2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(publicacion);
          expect(expectedResult).toContain(publicacion2);
        });

        it('should accept null and undefined values', () => {
          const publicacion: IPublicacion = { id: 123 };
          expectedResult = service.addPublicacionToCollectionIfMissing([], null, publicacion, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(publicacion);
        });

        it('should return initial array if no Publicacion is added', () => {
          const publicacionCollection: IPublicacion[] = [{ id: 123 }];
          expectedResult = service.addPublicacionToCollectionIfMissing(publicacionCollection, undefined, null);
          expect(expectedResult).toEqual(publicacionCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
