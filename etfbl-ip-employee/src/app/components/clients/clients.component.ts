import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { ToastrService } from 'ngx-toastr';
import Swal from 'sweetalert2';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

interface Client {
  id: number;
  userUsername: string;
  userFirstName: string;
  userLastName: string;
  userDeleted: number; 
  iDNumber: string | null;
  passportNumber: string | null;
  email: string;
  phone: string;
  balance: number;
}

@Component({
  selector: 'app-clients',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule],
  templateUrl: './clients.component.html',
  styleUrl: './clients.component.scss',
})
export class ClientsComponent implements OnInit {
  data: Client[] = [];

  page = 0;
  size = 5;
  totalPages = 0;
  totalElements = 0;

  searchTerm = '';
  deletedFilter: '' | '0' | '1' = ''; 

  showDetails = false;
  selected!: Client;
  avatarUrl = '';

  private base = 'http://localhost:8080/clients';

  constructor(private http: HttpClient, private toast: ToastrService) {}

  ngOnInit() {
    this.load();
  }

  load() {
    let params = new HttpParams().set('page', this.page).set('size', this.size);
    if (this.searchTerm.trim())
      params = params.set('searchTerm', this.searchTerm.trim());
    if (this.deletedFilter !== '')
      params = params.set('deleted', this.deletedFilter);

    this.http.get<any>(this.base, { params }).subscribe({
      next: (r) => {
        this.data = r.content;
        this.totalPages = r.totalPages;
        this.totalElements = r.totalElements;
      },
      error: () => this.toast.error('Error loading clients.'),
    });
  }

  onSearch() {
    this.page = 0;
    this.load();
  }
  onFilterChange(val: string) {
    this.deletedFilter = val as any;
    this.page = 0;
    this.load();
  }
  go(p: number) {
    if (p < 0 || p >= this.totalPages) return;
    this.page = p;
    this.load();
  }

  openDetails(c: Client) {
    this.selected = c;
    this.avatarUrl = `http://localhost:8080/images/avatars/${c.id}.png`;
    this.showDetails = true;
  }
  closeDetails() {
    this.showDetails = false;
  }

  toggle(c: Client) {
    const action = c.userDeleted === 0 ? 'block' : 'unblock';
    Swal.fire({
      title: `${action === 'block' ? 'Block' : 'Activate'} ${c.userUsername}?`,
      icon: 'question',
      showCancelButton: true,
      confirmButtonText: action === 'block' ? 'Block' : 'Activate',
      cancelButtonText: 'Cancel',
      buttonsStyling: false,
      customClass: {
        popup: 'rounded border',
        confirmButton: 'btn btn-outline-danger me-2',
        cancelButton: 'btn btn-outline-secondary',
      },
      backdrop: false,
    }).then((r) => {
      if (!r.isConfirmed) return;
      this.http
        .put(`${this.base}/${c.id}/${action}`, {}, { responseType: 'text' })
        .subscribe({
          next: (msg) => {
            this.toast.success(msg);
            this.load();
            if (this.showDetails) {
              this.selected.userDeleted = 1 - this.selected.userDeleted;
            }
          },
          error: () => this.toast.error('Error processing request.'),
        });
    });
  }

  onAvatarError(ev: Event) {
    (ev.target as HTMLImageElement).src = 'assets/images/client.jpg';
  }
}
