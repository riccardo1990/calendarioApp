import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPrenotazione } from 'app/shared/model/prenotazione.model';

type EntityResponseType = HttpResponse<IPrenotazione>;
type EntityArrayResponseType = HttpResponse<IPrenotazione[]>;

@Injectable({ providedIn: 'root' })
export class PrenotazioneService {
    private resourceUrl = SERVER_API_URL + 'api/prenotaziones';

    constructor(private http: HttpClient) {}

    create(prenotazione: IPrenotazione): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(prenotazione);
        return this.http
            .post<IPrenotazione>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(prenotazione: IPrenotazione): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(prenotazione);
        return this.http
            .put<IPrenotazione>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IPrenotazione>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IPrenotazione[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(prenotazione: IPrenotazione): IPrenotazione {
        const copy: IPrenotazione = Object.assign({}, prenotazione, {
            dataPrenotazione:
                prenotazione.dataPrenotazione != null && prenotazione.dataPrenotazione.isValid()
                    ? prenotazione.dataPrenotazione.format(DATE_FORMAT)
                    : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.dataPrenotazione = res.body.dataPrenotazione != null ? moment(res.body.dataPrenotazione) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((prenotazione: IPrenotazione) => {
            prenotazione.dataPrenotazione = prenotazione.dataPrenotazione != null ? moment(prenotazione.dataPrenotazione) : null;
        });
        return res;
    }
}
