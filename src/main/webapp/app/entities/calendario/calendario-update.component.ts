import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ICalendario } from 'app/shared/model/calendario.model';
import { CalendarioService } from './calendario.service';

@Component({
    selector: 'jhi-calendario-update',
    templateUrl: './calendario-update.component.html'
})
export class CalendarioUpdateComponent implements OnInit {
    private _calendario: ICalendario;
    isSaving: boolean;
    dataCalendarioDp: any;

    constructor(private calendarioService: CalendarioService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ calendario }) => {
            this.calendario = calendario;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.calendario.id !== undefined) {
            this.subscribeToSaveResponse(this.calendarioService.update(this.calendario));
        } else {
            this.subscribeToSaveResponse(this.calendarioService.create(this.calendario));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICalendario>>) {
        result.subscribe((res: HttpResponse<ICalendario>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get calendario() {
        return this._calendario;
    }

    set calendario(calendario: ICalendario) {
        this._calendario = calendario;
    }
}
