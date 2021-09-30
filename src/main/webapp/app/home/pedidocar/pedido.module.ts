import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PedidoRoutingModule } from './pedido-routing.module';
import { PedidocarComponent } from './pedido.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';

@NgModule({
  declarations: [PedidocarComponent],
  imports: [CommonModule, PedidoRoutingModule, FormsModule, ReactiveFormsModule],
})
export class PedidocarModule {}
