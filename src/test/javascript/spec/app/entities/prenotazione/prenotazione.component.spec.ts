/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CalendarioAppTestModule } from '../../../test.module';
import { PrenotazioneComponent } from 'app/entities/prenotazione/prenotazione.component';
import { PrenotazioneService } from 'app/entities/prenotazione/prenotazione.service';
import { Prenotazione } from 'app/shared/model/prenotazione.model';

describe('Component Tests', () => {
    describe('Prenotazione Management Component', () => {
        let comp: PrenotazioneComponent;
        let fixture: ComponentFixture<PrenotazioneComponent>;
        let service: PrenotazioneService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CalendarioAppTestModule],
                declarations: [PrenotazioneComponent],
                providers: []
            })
                .overrideTemplate(PrenotazioneComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PrenotazioneComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PrenotazioneService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Prenotazione(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.prenotaziones[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
