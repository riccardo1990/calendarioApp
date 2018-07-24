import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { CalendarioAppCalendarioModule } from './calendario/calendario.module';
import { CalendarioAppEventoModule } from './evento/evento.module';
import { CalendarioAppPrenotazioneModule } from './prenotazione/prenotazione.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        CalendarioAppCalendarioModule,
        CalendarioAppEventoModule,
        CalendarioAppPrenotazioneModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CalendarioAppEntityModule {}
