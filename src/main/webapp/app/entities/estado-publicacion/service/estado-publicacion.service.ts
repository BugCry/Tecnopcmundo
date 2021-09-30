import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEstadoPublicacion, getEstadoPublicacionIdentifier } from '../estado-publicacion.model';

export type EntityResponseType = HttpResponse<IEstadoPublicacion>;
export type EntityArrayResponseType = HttpResponse<IEstadoPublicacion[]>;

@Injectable({ providedIn: 'root' })
export class EstadoPublicacionService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/estado-publicacions');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(estadoPublicacion: IEstadoPublicacion): Observable<EntityResponseType> {
    return this.http.post<IEstadoPublicacion>(this.resourceUrl, estadoPublicacion, { observe: 'response' });
  }

  update(estadoPublicacion: IEstadoPublicacion): Observable<EntityResponseType> {
    return this.http.put<IEstadoPublicacion>(
      `${this.resourceUrl}/${getEstadoPublicacionIdentifier(estadoPublicacion) as number}`,
      estadoPublicacion,
      { observe: 'response' }
    );
  }

  partialUpdate(estadoPublicacion: IEstadoPublicacion): Observable<EntityResponseType> {
    return this.http.patch<IEstadoPublicacion>(
      `${this.resourceUrl}/${getEstadoPublicacionIdentifier(estadoPublicacion) as number}`,
      estadoPublicacion,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEstadoPublicacion>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEstadoPublicacion[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addEstadoPublicacionToCollectionIfMissing(
    estadoPublicacionCollection: IEstadoPublicacion[],
    ...estadoPublicacionsToCheck: (IEstadoPublicacion | null | undefined)[]
  ): IEstadoPublicacion[] {
    const estadoPublicacions: IEstadoPublicacion[] = estadoPublicacionsToCheck.filter(isPresent);
    if (estadoPublicacions.length > 0) {
      const estadoPublicacionCollectionIdentifiers = estadoPublicacionCollection.map(
        estadoPublicacionItem => getEstadoPublicacionIdentifier(estadoPublicacionItem)!
      );
      const estadoPublicacionsToAdd = estadoPublicacions.filter(estadoPublicacionItem => {
        const estadoPublicacionIdentifier = getEstadoPublicacionIdentifier(estadoPublicacionItem);
        if (estadoPublicacionIdentifier == null || estadoPublicacionCollectionIdentifiers.includes(estadoPublicacionIdentifier)) {
          return false;
        }
        estadoPublicacionCollectionIdentifiers.push(estadoPublicacionIdentifier);
        return true;
      });
      return [...estadoPublicacionsToAdd, ...estadoPublicacionCollection];
    }
    return estadoPublicacionCollection;
  }
}
