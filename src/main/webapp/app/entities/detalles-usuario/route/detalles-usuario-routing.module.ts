import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DetallesUsuarioComponent } from '../list/detalles-usuario.component';
import { DetallesUsuarioDetailComponent } from '../detail/detalles-usuario-detail.component';
import { DetallesUsuarioUpdateComponent } from '../update/detalles-usuario-update.component';
import { DetallesUsuarioRoutingResolveService } from './detalles-usuario-routing-resolve.service';

const detallesUsuarioRoute: Routes = [
  {
    path: '',
    component: DetallesUsuarioComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DetallesUsuarioDetailComponent,
    resolve: {
      detallesUsuario: DetallesUsuarioRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DetallesUsuarioUpdateComponent,
    resolve: {
      detallesUsuario: DetallesUsuarioRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DetallesUsuarioUpdateComponent,
    resolve: {
      detallesUsuario: DetallesUsuarioRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(detallesUsuarioRoute)],
  exports: [RouterModule],
})
export class DetallesUsuarioRoutingModule {}
