import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICategoriaPublicacion } from '../categoria-publicacion.model';
import { CategoriaPublicacionService } from '../service/categoria-publicacion.service';

@Component({
  templateUrl: './categoria-publicacion-delete-dialog.component.html',
})
export class CategoriaPublicacionDeleteDialogComponent {
  categoriaPublicacion?: ICategoriaPublicacion;

  constructor(protected categoriaPublicacionService: CategoriaPublicacionService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.categoriaPublicacionService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
