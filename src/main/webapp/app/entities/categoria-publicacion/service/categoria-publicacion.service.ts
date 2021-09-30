import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICategoriaPublicacion, getCategoriaPublicacionIdentifier } from '../categoria-publicacion.model';

export type EntityResponseType = HttpResponse<ICategoriaPublicacion>;
export type EntityArrayResponseType = HttpResponse<ICategoriaPublicacion[]>;

@Injectable({ providedIn: 'root' })
export class CategoriaPublicacionService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/categoria-publicacions');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(categoriaPublicacion: ICategoriaPublicacion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(categoriaPublicacion);
    return this.http
      .post<ICategoriaPublicacion>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(categoriaPublicacion: ICategoriaPublicacion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(categoriaPublicacion);
    return this.http
      .put<ICategoriaPublicacion>(`${this.resourceUrl}/${getCategoriaPublicacionIdentifier(categoriaPublicacion) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(categoriaPublicacion: ICategoriaPublicacion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(categoriaPublicacion);
    return this.http
      .patch<ICategoriaPublicacion>(`${this.resourceUrl}/${getCategoriaPublicacionIdentifier(categoriaPublicacion) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICategoriaPublicacion>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICategoriaPublicacion[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addCategoriaPublicacionToCollectionIfMissing(
    categoriaPublicacionCollection: ICategoriaPublicacion[],
    ...categoriaPublicacionsToCheck: (ICategoriaPublicacion | null | undefined)[]
  ): ICategoriaPublicacion[] {
    const categoriaPublicacions: ICategoriaPublicacion[] = categoriaPublicacionsToCheck.filter(isPresent);
    if (categoriaPublicacions.length > 0) {
      const categoriaPublicacionCollectionIdentifiers = categoriaPublicacionCollection.map(
        categoriaPublicacionItem => getCategoriaPublicacionIdentifier(categoriaPublicacionItem)!
      );
      const categoriaPublicacionsToAdd = categoriaPublicacions.filter(categoriaPublicacionItem => {
        const categoriaPublicacionIdentifier = getCategoriaPublicacionIdentifier(categoriaPublicacionItem);
        if (categoriaPublicacionIdentifier == null || categoriaPublicacionCollectionIdentifiers.includes(categoriaPublicacionIdentifier)) {
          return false;
        }
        categoriaPublicacionCollectionIdentifiers.push(categoriaPublicacionIdentifier);
        return true;
      });
      return [...categoriaPublicacionsToAdd, ...categoriaPublicacionCollection];
    }
    return categoriaPublicacionCollection;
  }

  protected convertDateFromClient(categoriaPublicacion: ICategoriaPublicacion): ICategoriaPublicacion {
    return Object.assign({}, categoriaPublicacion, {
      createAt: categoriaPublicacion.createAt?.isValid() ? categoriaPublicacion.createAt.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createAt = res.body.createAt ? dayjs(res.body.createAt) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((categoriaPublicacion: ICategoriaPublicacion) => {
        categoriaPublicacion.createAt = categoriaPublicacion.createAt ? dayjs(categoriaPublicacion.createAt) : undefined;
      });
    }
    return res;
  }
}
