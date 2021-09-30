import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPublicacion } from '../publicacion.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-publicacion-detail',
  templateUrl: './publicacion-detail.component.html',
})
export class PublicacionDetailComponent implements OnInit {
  publicacion: IPublicacion | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ publicacion }) => {
      this.publicacion = publicacion;
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
