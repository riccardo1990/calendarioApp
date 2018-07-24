import { Moment } from 'moment';
import { IPrenotazione } from 'app/shared/model//prenotazione.model';
import { ICalendario } from 'app/shared/model//calendario.model';

export const enum TipoEvento {
    ALLOCATO = 'ALLOCATO',
    LIBERO = 'LIBERO',
    NON_ALLOCABILE = 'NON_ALLOCABILE',
    FESTIVITA = 'FESTIVITA',
    FERIE = 'FERIE'
}

export const enum TipoGenerazioneEvento {
    AUTOMATICO = 'AUTOMATICO',
    MANUALE = 'MANUALE'
}

export interface IEvento {
    id?: number;
    codEvento?: string;
    flagAutomatico?: boolean;
    dataDa?: Moment;
    dataA?: Moment;
    tipoEvento?: TipoEvento;
    tipoGenerazioneEvento?: TipoGenerazioneEvento;
    eventoRelPrenot?: IPrenotazione;
    eventoRelCal?: ICalendario;
}

export class Evento implements IEvento {
    constructor(
        public id?: number,
        public codEvento?: string,
        public flagAutomatico?: boolean,
        public dataDa?: Moment,
        public dataA?: Moment,
        public tipoEvento?: TipoEvento,
        public tipoGenerazioneEvento?: TipoGenerazioneEvento,
        public eventoRelPrenot?: IPrenotazione,
        public eventoRelCal?: ICalendario
    ) {
        this.flagAutomatico = false;
    }
}
