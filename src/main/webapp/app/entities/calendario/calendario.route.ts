import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Calendario } from 'app/shared/model/calendario.model';
import { CalendarioService } from './calendario.service';
import { CalendarioComponent } from './calendario.component';
import { CalendarioDetailComponent } from './calendario-detail.component';
import { CalendarioUpdateComponent } from './calendario-update.component';
import { CalendarioDeletePopupComponent } from './calendario-delete-dialog.component';
import { ICalendario } from 'app/shared/model/calendario.model';

@Injectable({ providedIn: 'root' })
export class CalendarioResolve implements Resolve<ICalendario> {
    constructor(private service: CalendarioService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((calendario: HttpResponse<Calendario>) => calendario.body));
        }
        return of(new Calendario());
    }
}

export const calendarioRoute: Routes = [
    {
        path: 'calendario',
        component: CalendarioComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Calendarios'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'calendario/:id/view',
        component: CalendarioDetailComponent,
        resolve: {
            calendario: CalendarioResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Calendarios'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'calendario/new',
        component: CalendarioUpdateComponent,
        resolve: {
            calendario: CalendarioResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Calendarios'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'calendario/:id/edit',
        component: CalendarioUpdateComponent,
        resolve: {
            calendario: CalendarioResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Calendarios'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const calendarioPopupRoute: Routes = [
    {
        path: 'calendario/:id/delete',
        component: CalendarioDeletePopupComponent,
        resolve: {
            calendario: CalendarioResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Calendarios'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
