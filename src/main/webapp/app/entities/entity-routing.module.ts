import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'detalles-usuario',
        data: { pageTitle: 'inventarioTecnoPcMundoApp.detallesUsuario.home.title' },
        loadChildren: () => import('./detalles-usuario/detalles-usuario.module').then(m => m.DetallesUsuarioModule),
      },
      {
        path: 'producto',
        data: { pageTitle: 'inventarioTecnoPcMundoApp.producto.home.title' },
        loadChildren: () => import('./producto/producto.module').then(m => m.ProductoModule),
      },
      {
        path: 'categoria',
        data: { pageTitle: 'inventarioTecnoPcMundoApp.categoria.home.title' },
        loadChildren: () => import('./categoria/categoria.module').then(m => m.CategoriaModule),
      },
      {
        path: 'estado',
        data: { pageTitle: 'inventarioTecnoPcMundoApp.estado.home.title' },
        loadChildren: () => import('./estado/estado.module').then(m => m.EstadoModule),
      },
      {
        path: 'precio',
        data: { pageTitle: 'inventarioTecnoPcMundoApp.precio.home.title' },
        loadChildren: () => import('./precio/precio.module').then(m => m.PrecioModule),
      },
      {
        path: 'proveedor',
        data: { pageTitle: 'inventarioTecnoPcMundoApp.proveedor.home.title' },
        loadChildren: () => import('./proveedor/proveedor.module').then(m => m.ProveedorModule),
      },
      {
        path: 'pedido',
        data: { pageTitle: 'inventarioTecnoPcMundoApp.pedido.home.title' },
        loadChildren: () => import('./pedido/pedido.module').then(m => m.PedidoModule),
      },
      {
        path: 'compra',
        data: { pageTitle: 'inventarioTecnoPcMundoApp.compra.home.title' },
        loadChildren: () => import('./compra/compra.module').then(m => m.CompraModule),
      },
      {
        path: 'publicacion',
        data: { pageTitle: 'inventarioTecnoPcMundoApp.publicacion.home.title' },
        loadChildren: () => import('./publicacion/publicacion.module').then(m => m.PublicacionModule),
      },
      {
        path: 'estado-publicacion',
        data: { pageTitle: 'inventarioTecnoPcMundoApp.estadoPublicacion.home.title' },
        loadChildren: () => import('./estado-publicacion/estado-publicacion.module').then(m => m.EstadoPublicacionModule),
      },
      {
        path: 'categoria-publicacion',
        data: { pageTitle: 'inventarioTecnoPcMundoApp.categoriaPublicacion.home.title' },
        loadChildren: () => import('./categoria-publicacion/categoria-publicacion.module').then(m => m.CategoriaPublicacionModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
