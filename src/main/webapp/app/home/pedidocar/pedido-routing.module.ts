import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PedidoComponent } from 'app/entities/pedido/list/pedido.component';

const routes: Routes = [
  {
    path: '',
    component: PedidoComponent,
    data: {
      authorities: [],
      pageTitle: 'home.title',
    },
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class PedidoRoutingModule {}
