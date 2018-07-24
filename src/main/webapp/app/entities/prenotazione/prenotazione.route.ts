import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Prenotazione } from 'app/shared/model/prenotazione.model';
import { PrenotazioneService } from './prenotazione.service';
import { PrenotazioneComponent } from './prenotazione.component';
import { PrenotazioneDetailComponent } from './prenotazione-detail.component';
import { PrenotazioneUpdateComponent } from './prenotazione-update.component';
import { PrenotazioneDeletePopupComponent } from './prenotazione-delete-dialog.component';
import { IPrenotazione } from 'app/shared/model/prenotazione.model';

@Injectable({ providedIn: 'root' })
export class PrenotazioneResolve implements Resolve<IPrenotazione> {
    constructor(private service: PrenotazioneService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((prenotazione: HttpResponse<Prenotazione>) => prenotazione.body));
        }
        return of(new Prenotazione());
    }
}

export const prenotazioneRoute: Routes = [
    {
        path: 'prenotazione',
        component: PrenotazioneComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Prenotaziones'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'prenotazione/:id/view',
        component: PrenotazioneDetailComponent,
        resolve: {
            prenotazione: PrenotazioneResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Prenotaziones'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'prenotazione/new',
        component: PrenotazioneUpdateComponent,
        resolve: {
            prenotazione: PrenotazioneResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Prenotaziones'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'prenotazione/:id/edit',
        component: PrenotazioneUpdateComponent,
        resolve: {
            prenotazione: PrenotazioneResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Prenotaziones'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const prenotazionePopupRoute: Routes = [
    {
        path: 'prenotazione/:id/delete',
        component: PrenotazioneDeletePopupComponent,
        resolve: {
            prenotazione: PrenotazioneResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Prenotaziones'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
