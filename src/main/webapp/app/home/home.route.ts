import { Route, Routes } from '@angular/router';

import { HomeComponent } from './home.component';
import { PedidocarComponent } from './pedidocar/pedido.component';

export const HOME_ROUTES: Routes = [
  {
    path: '',
    component: HomeComponent,
    data: {
      pageTitle: 'home.title',
    },
  },
  {
    path: 'pedidocar',
    component: PedidocarComponent,
    data: {
      authorities: [],
      pageTitle: 'home.title',
    },
  },
];
