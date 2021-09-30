import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEstadoPublicacion } from '../estado-publicacion.model';

@Component({
  selector: 'jhi-estado-publicacion-detail',
  templateUrl: './estado-publicacion-detail.component.html',
})
export class EstadoPublicacionDetailComponent implements OnInit {
  estadoPublicacion: IEstadoPublicacion | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ estadoPublicacion }) => {
      this.estadoPublicacion = estadoPublicacion;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
