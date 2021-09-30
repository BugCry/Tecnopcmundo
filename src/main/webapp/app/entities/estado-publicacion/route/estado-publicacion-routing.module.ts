import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { EstadoPublicacionComponent } from '../list/estado-publicacion.component';
import { EstadoPublicacionDetailComponent } from '../detail/estado-publicacion-detail.component';
import { EstadoPublicacionUpdateComponent } from '../update/estado-publicacion-update.component';
import { EstadoPublicacionRoutingResolveService } from './estado-publicacion-routing-resolve.service';

const estadoPublicacionRoute: Routes = [
  {
    path: '',
    component: EstadoPublicacionComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EstadoPublicacionDetailComponent,
    resolve: {
      estadoPublicacion: EstadoPublicacionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EstadoPublicacionUpdateComponent,
    resolve: {
      estadoPublicacion: EstadoPublicacionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EstadoPublicacionUpdateComponent,
    resolve: {
      estadoPublicacion: EstadoPublicacionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(estadoPublicacionRoute)],
  exports: [RouterModule],
})
export class EstadoPublicacionRoutingModule {}
