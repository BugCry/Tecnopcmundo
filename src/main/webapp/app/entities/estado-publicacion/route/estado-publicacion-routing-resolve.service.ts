import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEstadoPublicacion, EstadoPublicacion } from '../estado-publicacion.model';
import { EstadoPublicacionService } from '../service/estado-publicacion.service';

@Injectable({ providedIn: 'root' })
export class EstadoPublicacionRoutingResolveService implements Resolve<IEstadoPublicacion> {
  constructor(protected service: EstadoPublicacionService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEstadoPublicacion> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((estadoPublicacion: HttpResponse<EstadoPublicacion>) => {
          if (estadoPublicacion.body) {
            return of(estadoPublicacion.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EstadoPublicacion());
  }
}
