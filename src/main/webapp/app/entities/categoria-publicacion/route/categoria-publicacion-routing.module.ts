import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CategoriaPublicacionComponent } from '../list/categoria-publicacion.component';
import { CategoriaPublicacionDetailComponent } from '../detail/categoria-publicacion-detail.component';
import { CategoriaPublicacionUpdateComponent } from '../update/categoria-publicacion-update.component';
import { CategoriaPublicacionRoutingResolveService } from './categoria-publicacion-routing-resolve.service';

const categoriaPublicacionRoute: Routes = [
  {
    path: '',
    component: CategoriaPublicacionComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CategoriaPublicacionDetailComponent,
    resolve: {
      categoriaPublicacion: CategoriaPublicacionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CategoriaPublicacionUpdateComponent,
    resolve: {
      categoriaPublicacion: CategoriaPublicacionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CategoriaPublicacionUpdateComponent,
    resolve: {
      categoriaPublicacion: CategoriaPublicacionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(categoriaPublicacionRoute)],
  exports: [RouterModule],
})
export class CategoriaPublicacionRoutingModule {}
