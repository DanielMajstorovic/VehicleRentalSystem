import { Component } from "@angular/core";
import { Router } from "@angular/router";
import { CommonModule } from "@angular/common";

@Component({
  selector: "app-unauthorized",
  standalone: true,
  imports: [CommonModule], 
  templateUrl: "./unauthorized.component.html",
  styleUrls: ["./unauthorized.component.scss"],
})
export class UnauthorizedComponent {
  constructor(private router: Router) {}

  navigateToLogin(): void {
    this.router.navigate(["/login"]);
  }

  goBack(): void {
    // window.history.back();
    window.history.go(-2); 
  }
}
