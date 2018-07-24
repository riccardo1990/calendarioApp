/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CalendarioAppTestModule } from '../../../test.module';
import { PrenotazioneDetailComponent } from 'app/entities/prenotazione/prenotazione-detail.component';
import { Prenotazione } from 'app/shared/model/prenotazione.model';

describe('Component Tests', () => {
    describe('Prenotazione Management Detail Component', () => {
        let comp: PrenotazioneDetailComponent;
        let fixture: ComponentFixture<PrenotazioneDetailComponent>;
        const route = ({ data: of({ prenotazione: new Prenotazione(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CalendarioAppTestModule],
                declarations: [PrenotazioneDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PrenotazioneDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PrenotazioneDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.prenotazione).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
