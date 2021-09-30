import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { EstadoPublicacionComponent } from './list/estado-publicacion.component';
import { EstadoPublicacionDetailComponent } from './detail/estado-publicacion-detail.component';
import { EstadoPublicacionUpdateComponent } from './update/estado-publicacion-update.component';
import { EstadoPublicacionDeleteDialogComponent } from './delete/estado-publicacion-delete-dialog.component';
import { EstadoPublicacionRoutingModule } from './route/estado-publicacion-routing.module';

@NgModule({
  imports: [SharedModule, EstadoPublicacionRoutingModule],
  declarations: [
    EstadoPublicacionComponent,
    EstadoPublicacionDetailComponent,
    EstadoPublicacionUpdateComponent,
    EstadoPublicacionDeleteDialogComponent,
  ],
  entryComponents: [EstadoPublicacionDeleteDialogComponent],
})
export class EstadoPublicacionModule {}
