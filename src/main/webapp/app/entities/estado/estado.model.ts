export interface IEstado {
  id?: number;
  nombre?: string | null;
}

export class Estado implements IEstado {
  constructor(public id?: number, public nombre?: string | null) {}
}

export function getEstadoIdentifier(estado: IEstado): number | undefined {
  return estado.id;
}
