import * as dayjs from 'dayjs';

export interface ICategoriaPublicacion {
  id?: number;
  titulo?: string | null;
  descripcion?: string | null;
  createAt?: dayjs.Dayjs | null;
}

export class CategoriaPublicacion implements ICategoriaPublicacion {
  constructor(
    public id?: number,
    public titulo?: string | null,
    public descripcion?: string | null,
    public createAt?: dayjs.Dayjs | null
  ) {}
}

export function getCategoriaPublicacionIdentifier(categoriaPublicacion: ICategoriaPublicacion): number | undefined {
  return categoriaPublicacion.id;
}
