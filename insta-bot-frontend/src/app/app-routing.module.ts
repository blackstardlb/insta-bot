import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AuthenticatedGuard} from './guards/authenticated-guard.service';
import {HomeComponent} from './home/home.component';
import {PostLoginComponent} from './post-login/post-login.component';
import {ManageableServerComponent} from './manageable-server/manageable-server.component';
import {LandingComponent} from './landing/landing.component';
import {LandingGuard} from './guards/landing.guard';
import {NotFoundComponent} from './not-found/not-found.component';


const routes: Routes = [
    {
        path: '',
        component: LandingComponent,
        canActivate: [LandingGuard]
    },
    {
        path: '',
        canActivateChild: [AuthenticatedGuard],
        children: [
            {
                path: 'home',
                component: HomeComponent,
            },
            {
                path: 'server/:id',
                component: ManageableServerComponent
            },
        ]
    },
    {
        path: 'login',
        component: PostLoginComponent,
    },
    {
        path: '**',
        component: NotFoundComponent
    }
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
