import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CalendarioAppSharedModule } from 'app/shared';
import {
    PrenotazioneComponent,
    PrenotazioneDetailComponent,
    PrenotazioneUpdateComponent,
    PrenotazioneDeletePopupComponent,
    PrenotazioneDeleteDialogComponent,
    prenotazioneRoute,
    prenotazionePopupRoute
} from './';

const ENTITY_STATES = [...prenotazioneRoute, ...prenotazionePopupRoute];

@NgModule({
    imports: [CalendarioAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PrenotazioneComponent,
        PrenotazioneDetailComponent,
        PrenotazioneUpdateComponent,
        PrenotazioneDeleteDialogComponent,
        PrenotazioneDeletePopupComponent
    ],
    entryComponents: [
        PrenotazioneComponent,
        PrenotazioneUpdateComponent,
        PrenotazioneDeleteDialogComponent,
        PrenotazioneDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CalendarioAppPrenotazioneModule {}
