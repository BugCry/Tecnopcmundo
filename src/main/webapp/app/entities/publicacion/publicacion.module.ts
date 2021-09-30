import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PublicacionComponent } from './list/publicacion.component';
import { PublicacionDetailComponent } from './detail/publicacion-detail.component';
import { PublicacionUpdateComponent } from './update/publicacion-update.component';
import { PublicacionDeleteDialogComponent } from './delete/publicacion-delete-dialog.component';
import { PublicacionRoutingModule } from './route/publicacion-routing.module';

@NgModule({
  imports: [SharedModule, PublicacionRoutingModule],
  declarations: [PublicacionComponent, PublicacionDetailComponent, PublicacionUpdateComponent, PublicacionDeleteDialogComponent],
  entryComponents: [PublicacionDeleteDialogComponent],
})
export class PublicacionModule {}
