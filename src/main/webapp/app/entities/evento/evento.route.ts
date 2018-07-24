import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Evento } from 'app/shared/model/evento.model';
import { EventoService } from './evento.service';
import { EventoComponent } from './evento.component';
import { EventoDetailComponent } from './evento-detail.component';
import { EventoUpdateComponent } from './evento-update.component';
import { EventoDeletePopupComponent } from './evento-delete-dialog.component';
import { IEvento } from 'app/shared/model/evento.model';

@Injectable({ providedIn: 'root' })
export class EventoResolve implements Resolve<IEvento> {
    constructor(private service: EventoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((evento: HttpResponse<Evento>) => evento.body));
        }
        return of(new Evento());
    }
}

export const eventoRoute: Routes = [
    {
        path: 'evento',
        component: EventoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Eventos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'evento/:id/view',
        component: EventoDetailComponent,
        resolve: {
            evento: EventoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Eventos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'evento/new',
        component: EventoUpdateComponent,
        resolve: {
            evento: EventoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Eventos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'evento/:id/edit',
        component: EventoUpdateComponent,
        resolve: {
            evento: EventoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Eventos'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const eventoPopupRoute: Routes = [
    {
        path: 'evento/:id/delete',
        component: EventoDeletePopupComponent,
        resolve: {
            evento: EventoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Eventos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
