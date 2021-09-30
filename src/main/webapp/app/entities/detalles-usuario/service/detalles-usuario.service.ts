import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDetallesUsuario, getDetallesUsuarioIdentifier, DetallesUsuario } from '../detalles-usuario.model';

export type EntityResponseType = HttpResponse<IDetallesUsuario>;
export type EntityArrayResponseType = HttpResponse<IDetallesUsuario[]>;

@Injectable({ providedIn: 'root' })
export class DetallesUsuarioService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/detalles-usuarios');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(detallesUsuario: IDetallesUsuario): Observable<EntityResponseType> {
    return this.http.post<IDetallesUsuario>(this.resourceUrl, detallesUsuario, { observe: 'response' });
  }

  update(detallesUsuario: IDetallesUsuario): Observable<EntityResponseType> {
    return this.http.put<IDetallesUsuario>(
      `${this.resourceUrl}/${getDetallesUsuarioIdentifier(detallesUsuario) as number}`,
      detallesUsuario,
      { observe: 'response' }
    );
  }

  partialUpdate(detallesUsuario: IDetallesUsuario): Observable<EntityResponseType> {
    return this.http.patch<IDetallesUsuario>(
      `${this.resourceUrl}/${getDetallesUsuarioIdentifier(detallesUsuario) as number}`,
      detallesUsuario,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDetallesUsuario>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  findByUserId(id: number): Observable<DetallesUsuario> {
    return this.http.get<DetallesUsuario>(`${this.resourceUrl}/userid/${id}`);
  }

  findByUserLogin(login: string): Observable<DetallesUsuario> {
    return this.http.get<DetallesUsuario>(`${this.resourceUrl}/login/${login}`);
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDetallesUsuario[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addDetallesUsuarioToCollectionIfMissing(
    detallesUsuarioCollection: IDetallesUsuario[],
    ...detallesUsuariosToCheck: (IDetallesUsuario | null | undefined)[]
  ): IDetallesUsuario[] {
    const detallesUsuarios: IDetallesUsuario[] = detallesUsuariosToCheck.filter(isPresent);
    if (detallesUsuarios.length > 0) {
      const detallesUsuarioCollectionIdentifiers = detallesUsuarioCollection.map(
        detallesUsuarioItem => getDetallesUsuarioIdentifier(detallesUsuarioItem)!
      );
      const detallesUsuariosToAdd = detallesUsuarios.filter(detallesUsuarioItem => {
        const detallesUsuarioIdentifier = getDetallesUsuarioIdentifier(detallesUsuarioItem);
        if (detallesUsuarioIdentifier == null || detallesUsuarioCollectionIdentifiers.includes(detallesUsuarioIdentifier)) {
          return false;
        }
        detallesUsuarioCollectionIdentifiers.push(detallesUsuarioIdentifier);
        return true;
      });
      return [...detallesUsuariosToAdd, ...detallesUsuarioCollection];
    }
    return detallesUsuarioCollection;
  }
}
