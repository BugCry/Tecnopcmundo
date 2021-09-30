import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICategoriaPublicacion, CategoriaPublicacion } from '../categoria-publicacion.model';
import { CategoriaPublicacionService } from '../service/categoria-publicacion.service';

@Injectable({ providedIn: 'root' })
export class CategoriaPublicacionRoutingResolveService implements Resolve<ICategoriaPublicacion> {
  constructor(protected service: CategoriaPublicacionService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICategoriaPublicacion> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((categoriaPublicacion: HttpResponse<CategoriaPublicacion>) => {
          if (categoriaPublicacion.body) {
            return of(categoriaPublicacion.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CategoriaPublicacion());
  }
}
