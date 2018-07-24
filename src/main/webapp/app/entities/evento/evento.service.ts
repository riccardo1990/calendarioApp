import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEvento } from 'app/shared/model/evento.model';

type EntityResponseType = HttpResponse<IEvento>;
type EntityArrayResponseType = HttpResponse<IEvento[]>;

@Injectable({ providedIn: 'root' })
export class EventoService {
    private resourceUrl = SERVER_API_URL + 'api/eventos';

    constructor(private http: HttpClient) {}

    create(evento: IEvento): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(evento);
        return this.http
            .post<IEvento>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(evento: IEvento): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(evento);
        return this.http
            .put<IEvento>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IEvento>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IEvento[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(evento: IEvento): IEvento {
        const copy: IEvento = Object.assign({}, evento, {
            dataDa: evento.dataDa != null && evento.dataDa.isValid() ? evento.dataDa.toJSON() : null,
            dataA: evento.dataA != null && evento.dataA.isValid() ? evento.dataA.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.dataDa = res.body.dataDa != null ? moment(res.body.dataDa) : null;
        res.body.dataA = res.body.dataA != null ? moment(res.body.dataA) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((evento: IEvento) => {
            evento.dataDa = evento.dataDa != null ? moment(evento.dataDa) : null;
            evento.dataA = evento.dataA != null ? moment(evento.dataA) : null;
        });
        return res;
    }
}
