import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDetallesUsuario } from '../detalles-usuario.model';

@Component({
  selector: 'jhi-detalles-usuario-detail',
  templateUrl: './detalles-usuario-detail.component.html',
})
export class DetallesUsuarioDetailComponent implements OnInit {
  detallesUsuario: IDetallesUsuario | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ detallesUsuario }) => {
      this.detallesUsuario = detallesUsuario;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
