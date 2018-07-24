import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICalendario } from 'app/shared/model/calendario.model';

@Component({
    selector: 'jhi-calendario-detail',
    templateUrl: './calendario-detail.component.html'
})
export class CalendarioDetailComponent implements OnInit {
    calendario: ICalendario;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ calendario }) => {
            this.calendario = calendario;
        });
    }

    previousState() {
        window.history.back();
    }
}
