import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IEvento } from 'app/shared/model/evento.model';
import { EventoService } from './evento.service';
import { IPrenotazione } from 'app/shared/model/prenotazione.model';
import { PrenotazioneService } from 'app/entities/prenotazione';
import { ICalendario } from 'app/shared/model/calendario.model';
import { CalendarioService } from 'app/entities/calendario';

@Component({
    selector: 'jhi-evento-update',
    templateUrl: './evento-update.component.html'
})
export class EventoUpdateComponent implements OnInit {
    private _evento: IEvento;
    isSaving: boolean;

    prenotaziones: IPrenotazione[];

    calendarios: ICalendario[];
    dataDa: string;
    dataA: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private eventoService: EventoService,
        private prenotazioneService: PrenotazioneService,
        private calendarioService: CalendarioService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ evento }) => {
            this.evento = evento;
        });
        this.prenotazioneService.query().subscribe(
            (res: HttpResponse<IPrenotazione[]>) => {
                this.prenotaziones = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.calendarioService.query().subscribe(
            (res: HttpResponse<ICalendario[]>) => {
                this.calendarios = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.evento.dataDa = moment(this.dataDa, DATE_TIME_FORMAT);
        this.evento.dataA = moment(this.dataA, DATE_TIME_FORMAT);
        if (this.evento.id !== undefined) {
            this.subscribeToSaveResponse(this.eventoService.update(this.evento));
        } else {
            this.subscribeToSaveResponse(this.eventoService.create(this.evento));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IEvento>>) {
        result.subscribe((res: HttpResponse<IEvento>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackPrenotazioneById(index: number, item: IPrenotazione) {
        return item.id;
    }

    trackCalendarioById(index: number, item: ICalendario) {
        return item.id;
    }
    get evento() {
        return this._evento;
    }

    set evento(evento: IEvento) {
        this._evento = evento;
        this.dataDa = moment(evento.dataDa).format(DATE_TIME_FORMAT);
        this.dataA = moment(evento.dataA).format(DATE_TIME_FORMAT);
    }
}
