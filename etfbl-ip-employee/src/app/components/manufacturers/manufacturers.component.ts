import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  Validators,
  ReactiveFormsModule,
  FormsModule,
} from '@angular/forms';
import {
  HttpClient,
  HttpParams,
} from '@angular/common/http';
import { ToastrService } from 'ngx-toastr';
import Swal from 'sweetalert2';
import { CommonModule } from '@angular/common';

interface Manufacturer {
  id: number;
  name: string;
  country: string;
  address: string;
  phone?: string;
  fax?: string;
  email: string;
}

@Component({
  selector: 'app-manufacturers',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, FormsModule],
  templateUrl: './manufacturers.component.html',
  styleUrl: './manufacturers.component.scss',
})
export class ManufacturersComponent implements OnInit {
  manufacturers: Manufacturer[] = [];

  currentPage = 0;
  pageSize = 5;
  totalElements = 0;
  totalPages = 0;

  searchTerm = '';

  showCreateModal = false;
  showUpdateModal = false;
  createForm: FormGroup;
  updateForm: FormGroup;
  selectedId: number | null = null;

  private baseUrl = 'http://localhost:8080/manufacturers';

  constructor(
    private http: HttpClient,
    private fb: FormBuilder,
    private toastr: ToastrService
  ) {
    const builder = () =>
      this.fb.group({
        name: ['', [Validators.required, Validators.maxLength(75)]],
        country: ['', Validators.required],
        address: ['', Validators.required],
        phone: [''],
        fax: [''],
        email: ['', [Validators.required, Validators.email]],
      });
    this.createForm = builder();
    this.updateForm = builder();
  }

  ngOnInit(): void {
    this.loadManufacturers();
  }

  isInvalidFieldUpdate(fieldName: string): boolean {
    const field = this.updateForm.get(fieldName);
    return !!(field && field.invalid && (field.dirty || field.touched));
  }

  isInvalidFieldCreate(fieldName: string): boolean {
    const field = this.createForm.get(fieldName);
    return !!(field && field.invalid && (field.dirty || field.touched));
  }


  loadManufacturers() {
    let params = new HttpParams()
      .set('page', this.currentPage)
      .set('size', this.pageSize);
    if (this.searchTerm.trim())
      params = params.set('searchTerm', this.searchTerm.trim());

    this.http
      .get<any>(this.baseUrl, { params })
      .subscribe({
        next: (res) => {
          this.manufacturers = res.content;
          this.totalElements = res.totalElements;
          this.totalPages = res.totalPages;
        },
        error: () =>
          this.toastr.error('Error while fetching manufacturers list.'),
      });
  }

  onSearch() {
    this.currentPage = 0;
    this.loadManufacturers();
  }

  goToPage(page: number) {
    if (page < 0 || page >= this.totalPages) return;
    this.currentPage = page;
    this.loadManufacturers();
  }


  openCreateModal() {
    this.createForm.reset();
    this.showCreateModal = true;
  }
  closeCreateModal() {
    this.showCreateModal = false;
  }

  onCreateManufacturer() {
    if (!this.createForm.valid) {
      this.createForm.markAllAsTouched();
      return;
    }
    this.http
      .post(this.baseUrl, this.createForm.value, { responseType: 'text' })
      .subscribe({
        next: (res: any) => {
          this.toastr.success('Manufacturer created.');
          this.closeCreateModal();
          this.loadManufacturers();
        },
        error: (err) => {
          if(err.status === 500) {
            this.toastr.error('Manufacturer with this name or email already exists!');
            return;
          }

          this.toastr.error(
            err?.error || 'Error creating manufacturer.'
          );

        }
          
      });
  }


  openUpdateModal(m: Manufacturer) {
    this.selectedId = m.id;
    this.updateForm.patchValue(m);
    this.showUpdateModal = true;
  }
  closeUpdateModal() {
    this.showUpdateModal = false;
    this.selectedId = null;
  }

  onUpdateManufacturer() {
    if (!this.updateForm.valid || this.selectedId === null) {
      this.updateForm.markAllAsTouched();
      return;
    }
    this.http
      .put(`${this.baseUrl}/${this.selectedId}`, this.updateForm.value, { responseType: 'text' })
      .subscribe({
        next: (res: any) => {
          this.toastr.success('Manufacturer updated.');
          this.closeUpdateModal();
          this.loadManufacturers();
        },
        error: (err) => {

          

          if(err.status === 500) {
            this.toastr.error('Error while updating! Manufacturer with this name or email already exists in the database!');
            return;
          }

          this.toastr.error(
            err?.error || 'Error while updating manufacturer.'
          );

        }
      });
  }


  onDeleteManufacturer(id: number, name: string) {
    Swal.fire({
      title: `Delete ${name}?`,
      text: 'This action cannot be undone.',
      icon: 'question',
      showCancelButton: true,
      confirmButtonText: 'Delete',
      cancelButtonText: 'Cancel',
      buttonsStyling: false,
      customClass: {
        popup: 'rounded border',
        title: 'fs-5',
        confirmButton: 'btn btn-outline-danger me-2',
        cancelButton: 'btn btn-outline-secondary',
      },
      backdrop: false,
      allowOutsideClick: false,
      allowEscapeKey: true,
    }).then((r) => {
      if (!r.isConfirmed) return;
      this.http
        .delete(`${this.baseUrl}/${id}`, { responseType: 'text' })
        .subscribe({
          next: (msg) => {
            this.toastr.success(msg || 'Manufacturer deleted.');
            this.loadManufacturers();
          },
          error: (err) =>
            this.toastr.error(
              err?.error || 'Error deleting manufacturer.'
            ),
        });
    });
  }


  invalid(form: FormGroup, control: string) {
    const c = form.get(control);
    return !!(c && c.invalid && (c.dirty || c.touched));
  }
}
