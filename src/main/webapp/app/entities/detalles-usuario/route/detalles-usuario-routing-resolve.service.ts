import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDetallesUsuario, DetallesUsuario } from '../detalles-usuario.model';
import { DetallesUsuarioService } from '../service/detalles-usuario.service';

@Injectable({ providedIn: 'root' })
export class DetallesUsuarioRoutingResolveService implements Resolve<IDetallesUsuario> {
  constructor(protected service: DetallesUsuarioService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDetallesUsuario> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((detallesUsuario: HttpResponse<DetallesUsuario>) => {
          if (detallesUsuario.body) {
            return of(detallesUsuario.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DetallesUsuario());
  }
}
