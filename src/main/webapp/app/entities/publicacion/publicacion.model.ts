import * as dayjs from 'dayjs';
import { ICategoriaPublicacion } from 'app/entities/categoria-publicacion/categoria-publicacion.model';
import { IUser } from 'app/entities/user/user.model';
import { IEstadoPublicacion } from 'app/entities/estado-publicacion/estado-publicacion.model';

export interface IPublicacion {
  id?: number;
  titulo?: string | null;
  descripcion?: string | null;
  imagenContentType?: string | null;
  imagen?: string | null;
  createAt?: dayjs.Dayjs | null;
  categoriaPublicacion?: ICategoriaPublicacion | null;
  user?: IUser | null;
  estado?: IEstadoPublicacion | null;
}

export class Publicacion implements IPublicacion {
  constructor(
    public id?: number,
    public titulo?: string | null,
    public descripcion?: string | null,
    public imagenContentType?: string | null,
    public imagen?: string | null,
    public createAt?: dayjs.Dayjs | null,
    public categoriaPublicacion?: ICategoriaPublicacion | null,
    public user?: IUser | null,
    public estado?: IEstadoPublicacion | null
  ) {}
}

export function getPublicacionIdentifier(publicacion: IPublicacion): number | undefined {
  return publicacion.id;
}
