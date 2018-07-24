import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPrenotazione } from 'app/shared/model/prenotazione.model';

@Component({
    selector: 'jhi-prenotazione-detail',
    templateUrl: './prenotazione-detail.component.html'
})
export class PrenotazioneDetailComponent implements OnInit {
    prenotazione: IPrenotazione;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ prenotazione }) => {
            this.prenotazione = prenotazione;
        });
    }

    previousState() {
        window.history.back();
    }
}
