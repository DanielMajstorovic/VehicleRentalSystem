import { Component, HostListener, inject, signal } from "@angular/core"
import { CommonModule } from "@angular/common"
import { RouterModule } from "@angular/router"
import { AuthService } from "../../core/services/auth.service"

@Component({
  standalone: true,
  selector: "app-administrator-layout",
  templateUrl: "./administrator-layout.component.html",
  styleUrls: ["./administrator-layout.component.scss"],
  imports: [CommonModule, RouterModule],
})
export class AdministratorLayoutComponent {
  authService = inject(AuthService)
  isSidebarCollapsed = signal(false)
  isDropdownOpen = signal(false)

  toggleSidebar() {
    this.isSidebarCollapsed.update((value) => !value)
  }

  toggleDropdown() {
    this.isDropdownOpen.update((value) => !value)
  }

  logout() {
    this.toggleDropdown();
    this.authService.logout()
  }

  viewProfile() {
    this.toggleDropdown();
    alert("User profile information")
  }

  @HostListener("document:click", ["$event"])
  onDocumentClick(event: MouseEvent) {
    const dropdownElement = document.getElementById("userDropdownContainer")
    const targetElement = event.target as HTMLElement

    if (dropdownElement && !dropdownElement.contains(targetElement) && this.isDropdownOpen()) {
      this.isDropdownOpen.set(false)
    }
  }

}
