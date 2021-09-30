import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PublicacionComponent } from '../list/publicacion.component';
import { PublicacionDetailComponent } from '../detail/publicacion-detail.component';
import { PublicacionUpdateComponent } from '../update/publicacion-update.component';
import { PublicacionRoutingResolveService } from './publicacion-routing-resolve.service';

const publicacionRoute: Routes = [
  {
    path: '',
    component: PublicacionComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PublicacionDetailComponent,
    resolve: {
      publicacion: PublicacionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PublicacionUpdateComponent,
    resolve: {
      publicacion: PublicacionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PublicacionUpdateComponent,
    resolve: {
      publicacion: PublicacionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(publicacionRoute)],
  exports: [RouterModule],
})
export class PublicacionRoutingModule {}
