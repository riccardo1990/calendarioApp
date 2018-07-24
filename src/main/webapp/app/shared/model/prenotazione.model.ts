import { Moment } from 'moment';

export interface IPrenotazione {
    id?: number;
    codPrenotazione?: string;
    dataPrenotazione?: Moment;
    recapito?: string;
    note?: string;
    codiceFiscaleExt?: string;
}

export class Prenotazione implements IPrenotazione {
    constructor(
        public id?: number,
        public codPrenotazione?: string,
        public dataPrenotazione?: Moment,
        public recapito?: string,
        public note?: string,
        public codiceFiscaleExt?: string
    ) {}
}
