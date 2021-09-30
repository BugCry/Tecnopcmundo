import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DetallesUsuarioDetailComponent } from './detalles-usuario-detail.component';

describe('Component Tests', () => {
  describe('DetallesUsuario Management Detail Component', () => {
    let comp: DetallesUsuarioDetailComponent;
    let fixture: ComponentFixture<DetallesUsuarioDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [DetallesUsuarioDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ detallesUsuario: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(DetallesUsuarioDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DetallesUsuarioDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load detallesUsuario on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.detallesUsuario).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
