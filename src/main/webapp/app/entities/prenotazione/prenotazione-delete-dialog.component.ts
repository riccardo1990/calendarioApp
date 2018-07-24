import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPrenotazione } from 'app/shared/model/prenotazione.model';
import { PrenotazioneService } from './prenotazione.service';

@Component({
    selector: 'jhi-prenotazione-delete-dialog',
    templateUrl: './prenotazione-delete-dialog.component.html'
})
export class PrenotazioneDeleteDialogComponent {
    prenotazione: IPrenotazione;

    constructor(
        private prenotazioneService: PrenotazioneService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.prenotazioneService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'prenotazioneListModification',
                content: 'Deleted an prenotazione'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-prenotazione-delete-popup',
    template: ''
})
export class PrenotazioneDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ prenotazione }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PrenotazioneDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.prenotazione = prenotazione;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
