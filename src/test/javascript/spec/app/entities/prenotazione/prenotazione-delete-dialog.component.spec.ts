/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CalendarioAppTestModule } from '../../../test.module';
import { PrenotazioneDeleteDialogComponent } from 'app/entities/prenotazione/prenotazione-delete-dialog.component';
import { PrenotazioneService } from 'app/entities/prenotazione/prenotazione.service';

describe('Component Tests', () => {
    describe('Prenotazione Management Delete Component', () => {
        let comp: PrenotazioneDeleteDialogComponent;
        let fixture: ComponentFixture<PrenotazioneDeleteDialogComponent>;
        let service: PrenotazioneService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CalendarioAppTestModule],
                declarations: [PrenotazioneDeleteDialogComponent]
            })
                .overrideTemplate(PrenotazioneDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PrenotazioneDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PrenotazioneService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
