import { Routes } from '@angular/router';
import { AuthGuard } from './guards/auth.guard';
import { RoleGuard } from './guards/role.guard';

export const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'login' },
  {
    path: 'login',
    loadComponent: () => import('./pages/login/login.component').then(m => m.LoginComponent)
  },
  {
    path: 'administrator',
    loadComponent: () => import('./layouts/administrator-layout/administrator-layout.component').then(m => m.AdministratorLayoutComponent),
    canActivate: [AuthGuard, RoleGuard],
    data: { expectedRole: 'ADMINISTRATOR' },
    children: [
      { path: "", redirectTo: "cars", pathMatch: "full" },
      { path: "cars", loadComponent: () => import('./components/cars/cars.component').then(m => m.CarsComponent) },
      { 
        path: "car-details/:id", 
        loadComponent: () => import('./components/car-details/car-details.component').then(m => m.CarDetailsComponent) 
      },
      { path: "ebikes", loadComponent: () => import('./components/ebikes/ebikes.component').then(m => m.EbikesComponent) },
      { 
        path: "ebike-details/:id", 
        loadComponent: () => import('./components/ebike-details/ebike-details.component').then(m => m.EbikeDetailsComponent) 
      },
      { path: "escooters", loadComponent: () => import('./components/escooters/escooters.component').then(m => m.EscootersComponent) },
      { 
        path: "escooter-details/:id", 
        loadComponent: () => import('./components/escooter-details/escooter-details.component').then(m => m.EscooterDetailsComponent) 
      },
      { path: "manufacturers", loadComponent: () => import('./components/manufacturers/manufacturers.component').then(m => m.ManufacturersComponent) },
      { path: "clients", loadComponent: () => import('./components/clients/clients.component').then(m => m.ClientsComponent) },
      { path: "employees", loadComponent: () => import('./components/employees/employees.component').then(m => m.EmployeesComponent) },
    ]
  },
  {
    path: 'manager',
    loadComponent: () => import('./layouts/manager-layout/manager-layout.component').then(m => m.ManagerLayoutComponent),
    canActivate: [AuthGuard, RoleGuard],
    data: { expectedRole: 'MANAGER' },
    children: [
      { path: "", redirectTo: "prices", pathMatch: "full" },

      { path: "prices", loadComponent: () => import('./components/prices/prices.component').then(m => m.PricesComponent) },
      { path: "statistics", loadComponent: () => import('./components/statistics/statistics.component').then(m => m.StatisticsComponent) },

      { path: "cars-manager", loadComponent: () => import('./components/cars-manager/cars-manager.component').then(m => m.CarsManagerComponent) },
      { 
        path: "car-details/:id", 
        loadComponent: () => import('./components/car-details-manager/car-details-manager.component').then(m => m.CarDetailsManagerComponent) 
      },
      { path: "ebikes-manager", loadComponent: () => import('./components/ebikes-manager/ebikes-manager.component').then(m => m.EbikesManagerComponent) },
      { 
        path: "ebike-details/:id", 
        loadComponent: () => import('./components/ebike-details-manager/ebike-details-manager.component').then(m => m.EbikeDetailsManagerComponent) 
      },
      { path: "escooters-manager", loadComponent: () => import('./components/escooters-manager/escooters-manager.component').then(m => m.EscootersManagerComponent) },
      { 
        path: "escooter-details/:id", 
        loadComponent: () => import('./components/escooter-details-manager/escooter-details-manager.component').then(m => m.EscooterDetailsManagerComponent) 
      },
      { path: "manufacturers", loadComponent: () => import('./components/manufacturers/manufacturers.component').then(m => m.ManufacturersComponent) },
      { path: "clients", loadComponent: () => import('./components/clients/clients.component').then(m => m.ClientsComponent) },
      { path: "employees", loadComponent: () => import('./components/employees/employees.component').then(m => m.EmployeesComponent) },
      

      { path: "rentals", loadComponent: () => import('./components/rentals/rentals.component').then(m => m.RentalsComponent) },
      { path: "vehicles-map", loadComponent: () => import('./components/vehicles-map/vehicles-map.component').then(m => m.VehiclesMapComponent) },
      { path: "breakdown-entry", loadComponent: () => import('./components/breakdown-entry/breakdown-entry.component').then(m => m.BreakdownEntryComponent) },

    ]
  },
  {
    path: 'operator',
    loadComponent: () => import('./layouts/operator-layout/operator-layout.component').then(m => m.OperatorLayoutComponent),
    canActivate: [AuthGuard, RoleGuard],
    data: { expectedRole: 'OPERATOR' },
    children: [
      { path: "", redirectTo: "rentals", pathMatch: "full" },
      { path: "rentals", loadComponent: () => import('./components/rentals/rentals.component').then(m => m.RentalsComponent) },
      { path: "vehicles-map", loadComponent: () => import('./components/vehicles-map/vehicles-map.component').then(m => m.VehiclesMapComponent) },
      { path: "clients", loadComponent: () => import('./components/clients/clients.component').then(m => m.ClientsComponent) },
      { path: "breakdown-entry", loadComponent: () => import('./components/breakdown-entry/breakdown-entry.component').then(m => m.BreakdownEntryComponent) },
    ]
  },
  {
    path: 'unauthorized',
    loadComponent: () => import('./pages/unauthorized/unauthorized.component').then(m => m.UnauthorizedComponent)
  },
  {
    path: '**',
    redirectTo: 'login'
  }
];
