/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CalendarioAppTestModule } from '../../../test.module';
import { PrenotazioneUpdateComponent } from 'app/entities/prenotazione/prenotazione-update.component';
import { PrenotazioneService } from 'app/entities/prenotazione/prenotazione.service';
import { Prenotazione } from 'app/shared/model/prenotazione.model';

describe('Component Tests', () => {
    describe('Prenotazione Management Update Component', () => {
        let comp: PrenotazioneUpdateComponent;
        let fixture: ComponentFixture<PrenotazioneUpdateComponent>;
        let service: PrenotazioneService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CalendarioAppTestModule],
                declarations: [PrenotazioneUpdateComponent]
            })
                .overrideTemplate(PrenotazioneUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PrenotazioneUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PrenotazioneService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Prenotazione(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.prenotazione = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Prenotazione();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.prenotazione = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
