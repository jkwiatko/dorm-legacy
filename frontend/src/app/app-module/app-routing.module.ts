import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {WelcomeScreenComponent} from './welcome-screen/welcome-screen.component';

const routes: Routes = [
    {path: '', redirectTo: 'home', pathMatch: 'full'},
    {path: 'home', component: WelcomeScreenComponent},
    // {path: '**', redirectTo: 'home'}, TODO add not found 404 like swarzmuller
];

@NgModule({
    imports: [
        RouterModule.forRoot(routes),
    ],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
