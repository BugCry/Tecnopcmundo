import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DetallesUsuarioComponent } from './list/detalles-usuario.component';
import { DetallesUsuarioDetailComponent } from './detail/detalles-usuario-detail.component';
import { DetallesUsuarioUpdateComponent } from './update/detalles-usuario-update.component';
import { DetallesUsuarioDeleteDialogComponent } from './delete/detalles-usuario-delete-dialog.component';
import { DetallesUsuarioRoutingModule } from './route/detalles-usuario-routing.module';

@NgModule({
  imports: [SharedModule, DetallesUsuarioRoutingModule],
  declarations: [
    DetallesUsuarioComponent,
    DetallesUsuarioDetailComponent,
    DetallesUsuarioUpdateComponent,
    DetallesUsuarioDeleteDialogComponent,
  ],
  entryComponents: [DetallesUsuarioDeleteDialogComponent],
})
export class DetallesUsuarioModule {}
