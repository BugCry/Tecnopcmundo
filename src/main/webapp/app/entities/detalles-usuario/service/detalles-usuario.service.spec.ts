import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IDetallesUsuario, DetallesUsuario } from '../detalles-usuario.model';

import { DetallesUsuarioService } from './detalles-usuario.service';

describe('Service Tests', () => {
  describe('DetallesUsuario Service', () => {
    let service: DetallesUsuarioService;
    let httpMock: HttpTestingController;
    let elemDefault: IDetallesUsuario;
    let expectedResult: IDetallesUsuario | IDetallesUsuario[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(DetallesUsuarioService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        telefono: 'AAAAAAA',
        identificacion: 'AAAAAAA',
        ciudad: 'AAAAAAA',
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

      it('should create a DetallesUsuario', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new DetallesUsuario()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a DetallesUsuario', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            telefono: 'BBBBBB',
            identificacion: 'BBBBBB',
            ciudad: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a DetallesUsuario', () => {
        const patchObject = Object.assign(
          {
            identificacion: 'BBBBBB',
            ciudad: 'BBBBBB',
          },
          new DetallesUsuario()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of DetallesUsuario', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            telefono: 'BBBBBB',
            identificacion: 'BBBBBB',
            ciudad: 'BBBBBB',
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

      it('should delete a DetallesUsuario', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addDetallesUsuarioToCollectionIfMissing', () => {
        it('should add a DetallesUsuario to an empty array', () => {
          const detallesUsuario: IDetallesUsuario = { id: 123 };
          expectedResult = service.addDetallesUsuarioToCollectionIfMissing([], detallesUsuario);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(detallesUsuario);
        });

        it('should not add a DetallesUsuario to an array that contains it', () => {
          const detallesUsuario: IDetallesUsuario = { id: 123 };
          const detallesUsuarioCollection: IDetallesUsuario[] = [
            {
              ...detallesUsuario,
            },
            { id: 456 },
          ];
          expectedResult = service.addDetallesUsuarioToCollectionIfMissing(detallesUsuarioCollection, detallesUsuario);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a DetallesUsuario to an array that doesn't contain it", () => {
          const detallesUsuario: IDetallesUsuario = { id: 123 };
          const detallesUsuarioCollection: IDetallesUsuario[] = [{ id: 456 }];
          expectedResult = service.addDetallesUsuarioToCollectionIfMissing(detallesUsuarioCollection, detallesUsuario);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(detallesUsuario);
        });

        it('should add only unique DetallesUsuario to an array', () => {
          const detallesUsuarioArray: IDetallesUsuario[] = [{ id: 123 }, { id: 456 }, { id: 23316 }];
          const detallesUsuarioCollection: IDetallesUsuario[] = [{ id: 123 }];
          expectedResult = service.addDetallesUsuarioToCollectionIfMissing(detallesUsuarioCollection, ...detallesUsuarioArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const detallesUsuario: IDetallesUsuario = { id: 123 };
          const detallesUsuario2: IDetallesUsuario = { id: 456 };
          expectedResult = service.addDetallesUsuarioToCollectionIfMissing([], detallesUsuario, detallesUsuario2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(detallesUsuario);
          expect(expectedResult).toContain(detallesUsuario2);
        });

        it('should accept null and undefined values', () => {
          const detallesUsuario: IDetallesUsuario = { id: 123 };
          expectedResult = service.addDetallesUsuarioToCollectionIfMissing([], null, detallesUsuario, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(detallesUsuario);
        });

        it('should return initial array if no DetallesUsuario is added', () => {
          const detallesUsuarioCollection: IDetallesUsuario[] = [{ id: 123 }];
          expectedResult = service.addDetallesUsuarioToCollectionIfMissing(detallesUsuarioCollection, undefined, null);
          expect(expectedResult).toEqual(detallesUsuarioCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
