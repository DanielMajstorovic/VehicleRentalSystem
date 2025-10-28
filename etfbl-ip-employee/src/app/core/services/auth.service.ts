import { Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Role, User } from '../../interfaces/user';
import { ToastrService } from 'ngx-toastr';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private backendUrl = 'http://localhost:8080';

  currentUser = signal<User | null>(this.loadUser());

  constructor(
    private http: HttpClient,
    private router: Router,
    private toastr: ToastrService
  ) {}

  private loadUser(): User | null {
    const userStr = sessionStorage.getItem('currentUser');
    return userStr ? (JSON.parse(userStr) as User) : null;
  }

  private saveUser(user: User): void {
    sessionStorage.setItem('currentUser', JSON.stringify(user));
  }

  private clearUser(): void {
    sessionStorage.removeItem('currentUser');
  }

  login(username: string, password: string) {

    this.http.post<any>(`${this.backendUrl}/login`, { username, password })
      .subscribe({
        next: (response) => {
          const user: User = {
            username: response.username,
            role: response.role,
          };

          this.currentUser.set(user);
          this.saveUser(user);

          switch (user.role) {
            case Role.ADMINISTRATOR:
              this.router.navigate(['/administrator']);
              break;
            case Role.MANAGER:
              this.router.navigate(['/manager']);
              break;
            case Role.OPERATOR:
              this.router.navigate(['/operator']);
              break;
            default:
              this.toastr.error('Unknown role!');
          }
        },
        error: (err) => {
          console.error(err);
          this.toastr.error(err.error, 'Error during login!');
        }
      });
  }

  logout() {
    this.router.navigate(['/login']);
    this.currentUser.set(null);
    this.clearUser();
  }

  isLoggedIn(): boolean {
    return this.currentUser() !== null;
  }

  getRole(): string | null {
    return this.currentUser()?.role || null;
  }

}
