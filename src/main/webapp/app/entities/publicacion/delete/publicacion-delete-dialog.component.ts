import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPublicacion } from '../publicacion.model';
import { PublicacionService } from '../service/publicacion.service';

@Component({
  templateUrl: './publicacion-delete-dialog.component.html',
})
export class PublicacionDeleteDialogComponent {
  publicacion?: IPublicacion;

  constructor(protected publicacionService: PublicacionService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.publicacionService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
