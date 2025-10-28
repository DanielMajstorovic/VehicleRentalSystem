import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  Validators,
  ReactiveFormsModule,
  FormsModule,
} from '@angular/forms';
import { HttpClient, HttpParams } from '@angular/common/http';
import { ToastrService } from 'ngx-toastr';
import Swal from 'sweetalert2';
import { CommonModule } from '@angular/common';

interface Employee {
  id: number;
  userUsername: string;
  userFirstName: string;
  userLastName: string;
  role: string;
}

@Component({
  selector: 'app-employees',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, FormsModule],
  templateUrl: './employees.component.html',
  styleUrl: './employees.component.scss',
})
export class EmployeesComponent implements OnInit {
  employees: Employee[] = [];

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

  roles = ['ADMINISTRATOR', 'OPERATOR', 'MANAGER'];

  private baseUrl = 'http://localhost:8080/employees';

  constructor(
    private http: HttpClient,
    private fb: FormBuilder,
    private toastr: ToastrService
  ) {
    const builder = (isCreate: boolean) =>
      this.fb.group({
        userUsername: [
          '',
          isCreate ? [Validators.required, Validators.maxLength(50)] : [],
        ],
        userPassword: [
          '',
          isCreate ? [Validators.required, Validators.maxLength(50)] : [],
        ],
        userFirstName: ['', [Validators.required, Validators.maxLength(50)]],
        userLastName: ['', [Validators.required, Validators.maxLength(50)]],
        role: ['', Validators.required],
      });

    this.createForm = builder(true);
    this.updateForm = builder(false);
  }

  ngOnInit(): void {
    this.loadEmployees();
  }


  isInvalid(form: FormGroup, name: string) {
    const c = form.get(name);
    return !!(c && c.invalid && (c.dirty || c.touched));
  }


  loadEmployees() {
    let params = new HttpParams()
      .set('page', this.currentPage)
      .set('size', this.pageSize);
    if (this.searchTerm.trim())
      params = params.set('searchTerm', this.searchTerm.trim());

    this.http.get<any>(this.baseUrl, { params }).subscribe({
      next: (res) => {
        this.employees = res.content;
        this.totalElements = res.totalElements;
        this.totalPages = res.totalPages;
      },
      error: () => this.toastr.error('Error while fetching employees list.'),
    });
  }

  onSearch() {
    this.currentPage = 0;
    this.loadEmployees();
  }

  goToPage(p: number) {
    if (p < 0 || p >= this.totalPages) return;
    this.currentPage = p;
    this.loadEmployees();
  }


  openCreateModal() {
    this.createForm.reset();
    this.showCreateModal = true;
  }
  closeCreateModal() {
    this.showCreateModal = false;
  }

  onCreate() {
    if (!this.createForm.valid) {
      this.createForm.markAllAsTouched();
      return;
    }
    this.http
      .post(this.baseUrl, this.createForm.value, { responseType: 'text' })
      .subscribe({
        next: () => {
          this.toastr.success('Employee created.');
          this.closeCreateModal();
          this.loadEmployees();
        },
        error: (err) => {
          if (err.status === 500) {
            this.toastr.error('Username already exists!');
            return;
          }
          this.toastr.error(err?.error || 'Error creating employee.');
        },
      });
  }


  openUpdateModal(e: Employee) {
    this.selectedId = e.id;
    this.updateForm.reset({
      userFirstName: e.userFirstName,
      userLastName: e.userLastName,
      role: e.role,
      userPassword: '',
    });
    this.showUpdateModal = true;
  }
  closeUpdateModal() {
    this.showUpdateModal = false;
    this.selectedId = null;
  }

  onUpdate() {
    if (!this.updateForm.valid || this.selectedId === null) {
      this.updateForm.markAllAsTouched();
      return;
    }
    this.http
      .put(`${this.baseUrl}/${this.selectedId}`, this.updateForm.value, {
        responseType: 'text',
      })
      .subscribe({
        next: () => {
          this.toastr.success('Employee updated.');
          this.closeUpdateModal();
          this.loadEmployees();
        },
        error: (err) => {
          this.toastr.error('Error while updating employee.')
          console.log(err);
          
        }
      });
  }


  onDelete(id: number, username: string) {
    Swal.fire({
      title: `Delete ${username}?`,
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
            this.toastr.success(msg || 'Employee deleted.');
            this.loadEmployees();
          },
          error: () => this.toastr.error('Error deleting employee.'),
        });
    });
  }
}
