/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CalendarioAppTestModule } from '../../../test.module';
import { CalendarioDetailComponent } from 'app/entities/calendario/calendario-detail.component';
import { Calendario } from 'app/shared/model/calendario.model';

describe('Component Tests', () => {
    describe('Calendario Management Detail Component', () => {
        let comp: CalendarioDetailComponent;
        let fixture: ComponentFixture<CalendarioDetailComponent>;
        const route = ({ data: of({ calendario: new Calendario(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CalendarioAppTestModule],
                declarations: [CalendarioDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CalendarioDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CalendarioDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.calendario).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
