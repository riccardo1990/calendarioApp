import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICalendario } from 'app/shared/model/calendario.model';

type EntityResponseType = HttpResponse<ICalendario>;
type EntityArrayResponseType = HttpResponse<ICalendario[]>;

@Injectable({ providedIn: 'root' })
export class CalendarioService {
    private resourceUrl = SERVER_API_URL + 'api/calendarios';

    constructor(private http: HttpClient) {}

    create(calendario: ICalendario): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(calendario);
        return this.http
            .post<ICalendario>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(calendario: ICalendario): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(calendario);
        return this.http
            .put<ICalendario>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ICalendario>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ICalendario[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(calendario: ICalendario): ICalendario {
        const copy: ICalendario = Object.assign({}, calendario, {
            dataCalendario:
                calendario.dataCalendario != null && calendario.dataCalendario.isValid()
                    ? calendario.dataCalendario.format(DATE_FORMAT)
                    : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.dataCalendario = res.body.dataCalendario != null ? moment(res.body.dataCalendario) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((calendario: ICalendario) => {
            calendario.dataCalendario = calendario.dataCalendario != null ? moment(calendario.dataCalendario) : null;
        });
        return res;
    }
}
