import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {NavComponent} from './nav/nav.component';
import {LayoutModule} from '@angular/cdk/layout';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatIconModule} from '@angular/material/icon';
import {MatListModule} from '@angular/material/list';
import {HomeComponent} from './home/home.component';
import {PostLoginComponent} from './post-login/post-login.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {CookieService} from 'ngx-cookie-service';
import {UserNavComponent} from './user-nav/user-nav.component';
import {FlexLayoutModule} from '@angular/flex-layout';
import {AuthInterceptor} from '../service/auth-interceptor';
import {MatCardModule} from '@angular/material/card';
import {AvatarModule} from 'ngx-avatar';
import {ManageableServersComponent} from './manageable-servers/manageable-servers.component';
import {ManageableServerComponent} from './manageable-server/manageable-server.component';
import {MatInputModule} from '@angular/material/input';
import {MatSelectModule} from '@angular/material/select';
import {MatButtonModule} from '@angular/material/button';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MatSlideToggleModule} from '@angular/material/slide-toggle';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import {MatMenuModule} from '@angular/material/menu';
import {LandingComponent} from './landing/landing.component';
import {NotFoundComponent} from './not-found/not-found.component';
import {MatSnackBarModule, MatTooltipModule} from '@angular/material';

@NgModule({
    declarations: [
        AppComponent,
        NavComponent,
        HomeComponent,
        PostLoginComponent,
        UserNavComponent,
        ManageableServersComponent,
        ManageableServerComponent,
        LandingComponent,
        NotFoundComponent,
    ],
    imports: [
        HttpClientModule,
        BrowserModule,
        AppRoutingModule,
        BrowserAnimationsModule,
        LayoutModule,
        MatToolbarModule,
        MatSidenavModule,
        MatButtonModule,
        MatIconModule,
        MatListModule,
        FlexLayoutModule,
        MatCardModule,
        AvatarModule,
        MatInputModule,
        MatSelectModule,
        MatCheckboxModule,
        FormsModule,
        MatSlideToggleModule,
        MatProgressSpinnerModule,
        MatMenuModule,
        MatSnackBarModule,
        MatTooltipModule,
        ReactiveFormsModule,
    ],
    providers: [CookieService,
        {
            provide: HTTP_INTERCEPTORS,
            useClass: AuthInterceptor,
            multi: true
        },
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}
