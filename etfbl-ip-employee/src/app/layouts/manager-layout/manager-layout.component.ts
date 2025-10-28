import { Component, HostListener, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { AuthService } from '../../core/services/auth.service';

@Component({
  standalone: true,
  selector: 'app-manager-layout',
  templateUrl: './manager-layout.component.html',
  styleUrls: ['./manager-layout.component.scss'],
  imports: [CommonModule, RouterModule],
})
export class ManagerLayoutComponent {
  private authService = inject(AuthService);

  isSidebarCollapsed = signal(false);
  isDropdownOpen = signal(false);

  /* Sidebar */
  toggleSidebar() { this.isSidebarCollapsed.update(v => !v); }

  /* Dropdown */
  toggleDropdown() { this.isDropdownOpen.update(v => !v); }

  /* User actions */
  logout() {
    this.toggleDropdown();
    this.authService.logout();
  }

  viewProfile() {
    this.toggleDropdown();
    alert('User profile information');
  }

  /* Close dropdown on outside click */
  @HostListener('document:click', ['$event'])
  onDocumentClick(event: MouseEvent) {
    const dropdown = document.getElementById('userDropdownContainer');
    const target = event.target as HTMLElement;
    if (dropdown && !dropdown.contains(target) && this.isDropdownOpen()) {
      this.isDropdownOpen.set(false);
    }
  }
}
