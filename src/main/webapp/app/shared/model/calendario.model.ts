import { Moment } from 'moment';

export interface ICalendario {
    id?: number;
    codCalendario?: string;
    descCalendario?: string;
    anno?: number;
    dataCalendario?: Moment;
}

export class Calendario implements ICalendario {
    constructor(
        public id?: number,
        public codCalendario?: string,
        public descCalendario?: string,
        public anno?: number,
        public dataCalendario?: Moment
    ) {}
}
