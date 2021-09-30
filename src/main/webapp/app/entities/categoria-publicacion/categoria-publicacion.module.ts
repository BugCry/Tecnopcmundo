import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CategoriaPublicacionComponent } from './list/categoria-publicacion.component';
import { CategoriaPublicacionDetailComponent } from './detail/categoria-publicacion-detail.component';
import { CategoriaPublicacionUpdateComponent } from './update/categoria-publicacion-update.component';
import { CategoriaPublicacionDeleteDialogComponent } from './delete/categoria-publicacion-delete-dialog.component';
import { CategoriaPublicacionRoutingModule } from './route/categoria-publicacion-routing.module';

@NgModule({
  imports: [SharedModule, CategoriaPublicacionRoutingModule],
  declarations: [
    CategoriaPublicacionComponent,
    CategoriaPublicacionDetailComponent,
    CategoriaPublicacionUpdateComponent,
    CategoriaPublicacionDeleteDialogComponent,
  ],
  entryComponents: [CategoriaPublicacionDeleteDialogComponent],
})
export class CategoriaPublicacionModule {}
