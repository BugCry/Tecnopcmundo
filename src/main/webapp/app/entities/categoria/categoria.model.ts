export interface ICategoria {
  id?: number;
  nombre?: string | null;
}

export class Categoria implements ICategoria {
  constructor(public id?: number, public nombre?: string | null) {}
}

export function getCategoriaIdentifier(categoria: ICategoria): number | undefined {
  return categoria.id;
}
