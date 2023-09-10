import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomepageComponent } from './homepage/homepage.component';
import { MoviesComponent } from './movies/movies.component';
import { RentalsComponent } from './rentals/rentals.component';
import { CustomersComponent } from './customers/customers.component';
import { RenturnsComponent } from './renturns/renturns.component';
import { LoginComponent } from './auth/login/login.component';
import { SignupComponent } from './auth/signup/signup.component';

const routes: Routes = [
  { path: "homepage", redirectTo: "/", pathMatch: "full" },
  { path: "", component: HomepageComponent },
  { path: "movies", component: MoviesComponent },
  { path: "rentals", component: RentalsComponent },
  { path: "returns", component: RenturnsComponent },
  { path: "customers", component: CustomersComponent },
  { path: "login", component: LoginComponent },
  { path: "signup", component: SignupComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
