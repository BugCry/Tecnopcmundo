import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EstadoPublicacionDetailComponent } from './estado-publicacion-detail.component';

describe('Component Tests', () => {
  describe('EstadoPublicacion Management Detail Component', () => {
    let comp: EstadoPublicacionDetailComponent;
    let fixture: ComponentFixture<EstadoPublicacionDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [EstadoPublicacionDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ estadoPublicacion: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(EstadoPublicacionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EstadoPublicacionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load estadoPublicacion on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.estadoPublicacion).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
