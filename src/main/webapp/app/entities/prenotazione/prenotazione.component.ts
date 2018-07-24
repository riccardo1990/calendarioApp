import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPrenotazione } from 'app/shared/model/prenotazione.model';
import { Principal } from 'app/core';
import { PrenotazioneService } from './prenotazione.service';

@Component({
    selector: 'jhi-prenotazione',
    templateUrl: './prenotazione.component.html'
})
export class PrenotazioneComponent implements OnInit, OnDestroy {
    prenotaziones: IPrenotazione[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private prenotazioneService: PrenotazioneService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.prenotazioneService.query().subscribe(
            (res: HttpResponse<IPrenotazione[]>) => {
                this.prenotaziones = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInPrenotaziones();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IPrenotazione) {
        return item.id;
    }

    registerChangeInPrenotaziones() {
        this.eventSubscriber = this.eventManager.subscribe('prenotazioneListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
