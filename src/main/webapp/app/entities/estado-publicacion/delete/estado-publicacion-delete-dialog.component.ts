import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IEstadoPublicacion } from '../estado-publicacion.model';
import { EstadoPublicacionService } from '../service/estado-publicacion.service';

@Component({
  templateUrl: './estado-publicacion-delete-dialog.component.html',
})
export class EstadoPublicacionDeleteDialogComponent {
  estadoPublicacion?: IEstadoPublicacion;

  constructor(protected estadoPublicacionService: EstadoPublicacionService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.estadoPublicacionService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
