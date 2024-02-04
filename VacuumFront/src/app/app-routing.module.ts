import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { AddUserComponent } from './components/add-user/add-user.component';
import { UserListComponent } from './components/user-list/user-list.component';
import { AddVacuumComponent } from './components/add-vacuum/add-vacuum.component';
import { VacuumListComponent } from './components/vacuum-list/vacuum-list.component';
import { ErrorListComponent } from './components/error-list/error-list.component';
import { EditUserComponent } from './components/edit-user/edit-user.component';

const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'add-user', component: AddUserComponent },
  { path: 'edit-user/:id', component: EditUserComponent},
  { path: 'user-list', component: UserListComponent },
  { path: 'add-vacuum', component: AddVacuumComponent },
  { path: 'vacuum-list', component: VacuumListComponent },
  { path: 'error-list', component: ErrorListComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }