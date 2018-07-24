import { NgModule } from '@angular/core';

import { CalendarioAppSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [CalendarioAppSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [CalendarioAppSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class CalendarioAppSharedCommonModule {}
