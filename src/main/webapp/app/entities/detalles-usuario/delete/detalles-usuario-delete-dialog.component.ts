import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDetallesUsuario } from '../detalles-usuario.model';
import { DetallesUsuarioService } from '../service/detalles-usuario.service';

@Component({
  templateUrl: './detalles-usuario-delete-dialog.component.html',
})
export class DetallesUsuarioDeleteDialogComponent {
  detallesUsuario?: IDetallesUsuario;

  constructor(protected detallesUsuarioService: DetallesUsuarioService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.detallesUsuarioService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
