import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICategoriaPublicacion } from '../categoria-publicacion.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-categoria-publicacion-detail',
  templateUrl: './categoria-publicacion-detail.component.html',
})
export class CategoriaPublicacionDetailComponent implements OnInit {
  categoriaPublicacion: ICategoriaPublicacion | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ categoriaPublicacion }) => {
      this.categoriaPublicacion = categoriaPublicacion;
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  previousState(): void {
    window.history.back();
  }
}
