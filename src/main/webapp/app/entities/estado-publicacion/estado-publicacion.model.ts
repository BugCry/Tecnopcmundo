export interface IEstadoPublicacion {
  id?: number;
  nombre?: string | null;
}

export class EstadoPublicacion implements IEstadoPublicacion {
  constructor(public id?: number, public nombre?: string | null) {}
}

export function getEstadoPublicacionIdentifier(estadoPublicacion: IEstadoPublicacion): number | undefined {
  return estadoPublicacion.id;
}
