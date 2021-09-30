import { TestBed } from '@angular/core/testing';

import { PedidocarService } from './pedidocar.service';

describe('PedidocarService', () => {
  let service: PedidocarService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PedidocarService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
