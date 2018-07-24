import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IPrenotazione } from 'app/shared/model/prenotazione.model';
import { PrenotazioneService } from './prenotazione.service';

@Component({
    selector: 'jhi-prenotazione-update',
    templateUrl: './prenotazione-update.component.html'
})
export class PrenotazioneUpdateComponent implements OnInit {
    private _prenotazione: IPrenotazione;
    isSaving: boolean;
    dataPrenotazioneDp: any;

    constructor(private prenotazioneService: PrenotazioneService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ prenotazione }) => {
            this.prenotazione = prenotazione;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.prenotazione.id !== undefined) {
            this.subscribeToSaveResponse(this.prenotazioneService.update(this.prenotazione));
        } else {
            this.subscribeToSaveResponse(this.prenotazioneService.create(this.prenotazione));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPrenotazione>>) {
        result.subscribe((res: HttpResponse<IPrenotazione>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get prenotazione() {
        return this._prenotazione;
    }

    set prenotazione(prenotazione: IPrenotazione) {
        this._prenotazione = prenotazione;
    }
}
