import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPublicacion, Publicacion } from '../publicacion.model';
import { PublicacionService } from '../service/publicacion.service';

@Injectable({ providedIn: 'root' })
export class PublicacionRoutingResolveService implements Resolve<IPublicacion> {
  constructor(protected service: PublicacionService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPublicacion> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((publicacion: HttpResponse<Publicacion>) => {
          if (publicacion.body) {
            return of(publicacion.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Publicacion());
  }
}
