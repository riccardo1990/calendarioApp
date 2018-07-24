/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CalendarioAppTestModule } from '../../../test.module';
import { CalendarioUpdateComponent } from 'app/entities/calendario/calendario-update.component';
import { CalendarioService } from 'app/entities/calendario/calendario.service';
import { Calendario } from 'app/shared/model/calendario.model';

describe('Component Tests', () => {
    describe('Calendario Management Update Component', () => {
        let comp: CalendarioUpdateComponent;
        let fixture: ComponentFixture<CalendarioUpdateComponent>;
        let service: CalendarioService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CalendarioAppTestModule],
                declarations: [CalendarioUpdateComponent]
            })
                .overrideTemplate(CalendarioUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CalendarioUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CalendarioService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Calendario(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.calendario = entity;
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
                    const entity = new Calendario();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.calendario = entity;
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
