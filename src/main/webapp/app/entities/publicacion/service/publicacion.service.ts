import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPublicacion, getPublicacionIdentifier } from '../publicacion.model';

export type EntityResponseType = HttpResponse<IPublicacion>;
export type EntityArrayResponseType = HttpResponse<IPublicacion[]>;

@Injectable({ providedIn: 'root' })
export class PublicacionService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/publicacions');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(publicacion: IPublicacion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(publicacion);
    return this.http
      .post<IPublicacion>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(publicacion: IPublicacion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(publicacion);
    return this.http
      .put<IPublicacion>(`${this.resourceUrl}/${getPublicacionIdentifier(publicacion) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(publicacion: IPublicacion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(publicacion);
    return this.http
      .patch<IPublicacion>(`${this.resourceUrl}/${getPublicacionIdentifier(publicacion) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPublicacion>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPublicacion[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPublicacionToCollectionIfMissing(
    publicacionCollection: IPublicacion[],
    ...publicacionsToCheck: (IPublicacion | null | undefined)[]
  ): IPublicacion[] {
    const publicacions: IPublicacion[] = publicacionsToCheck.filter(isPresent);
    if (publicacions.length > 0) {
      const publicacionCollectionIdentifiers = publicacionCollection.map(publicacionItem => getPublicacionIdentifier(publicacionItem)!);
      const publicacionsToAdd = publicacions.filter(publicacionItem => {
        const publicacionIdentifier = getPublicacionIdentifier(publicacionItem);
        if (publicacionIdentifier == null || publicacionCollectionIdentifiers.includes(publicacionIdentifier)) {
          return false;
        }
        publicacionCollectionIdentifiers.push(publicacionIdentifier);
        return true;
      });
      return [...publicacionsToAdd, ...publicacionCollection];
    }
    return publicacionCollection;
  }

  protected convertDateFromClient(publicacion: IPublicacion): IPublicacion {
    return Object.assign({}, publicacion, {
      createAt: publicacion.createAt?.isValid() ? publicacion.createAt.toJSON() : undefined,
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
      res.body.forEach((publicacion: IPublicacion) => {
        publicacion.createAt = publicacion.createAt ? dayjs(publicacion.createAt) : undefined;
      });
    }
    return res;
  }
}
