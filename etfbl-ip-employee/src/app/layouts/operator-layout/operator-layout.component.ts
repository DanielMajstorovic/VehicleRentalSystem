import { Component, HostListener, inject, signal } from "@angular/core";
import { CommonModule } from "@angular/common";
import { RouterModule } from "@angular/router";
import { AuthService } from "../../core/services/auth.service";

@Component({
  standalone: true,
  selector: "app-operator-layout",
  templateUrl: "./operator-layout.component.html",
  styleUrls: ["./operator-layout.component.scss"],
  imports: [CommonModule, RouterModule],
})
export class OperatorLayoutComponent {
  private authService = inject(AuthService);
  isSidebarCollapsed = signal(false);
  isDropdownOpen = signal(false);

  toggleSidebar() {
    this.isSidebarCollapsed.update(v => !v);
  }

  toggleDropdown() {
    this.isDropdownOpen.update(v => !v);
  }

  logout() {
    this.toggleDropdown();
    this.authService.logout();
  }

  viewProfile() {
    this.toggleDropdown();
    alert("Operator profile information");
  }

  @HostListener("document:click", ["$event"])
  onDocumentClick(event: MouseEvent) {
    const dropdownEl = document.getElementById("userDropdownContainer");
    const target = event.target as HTMLElement;
    if (dropdownEl && !dropdownEl.contains(target) && this.isDropdownOpen()) {
      this.isDropdownOpen.set(false);
    }
  }
}
