import {
  Component,
  OnInit,
  ViewChild,
  ElementRef,
  AfterViewInit,
} from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  Validators,
  ReactiveFormsModule,
  FormsModule,
} from '@angular/forms';
import { CommonModule } from '@angular/common';
import { HttpClient, HttpParams } from '@angular/common/http';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import * as L from 'leaflet';

interface EScooterDto {
  id: number;
  vehicleId: number;
  vehicleUid: string;
  vehiclePurchasePrice: number;
  vehicleModel: string;
  vehicleManufacturerId: number;
  vehicleManufacturerName: string;
  vehicleManufacturerCountry: string;
  vehicleStatus: string;
  vehiclePricePerSecond: number;
  vehicleX: number;
  vehicleY: number;
  maxSpeed: number;
}

interface ManufacturerShort {
  id: number;
  name: string;
  country: string;
}

@Component({
  selector: 'app-escooters-manager',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, FormsModule],
  templateUrl: './escooters-manager.component.html',
  styleUrls: ['./escooters-manager.component.scss'],
})
export class EscootersManagerComponent implements OnInit, AfterViewInit {
  escooters: EScooterDto[] = [];
  searchTerm = '';

  currentPage = 0;
  pageSize = 5;
  totalElements = 0;
  totalPages = 0;

  showCreateModal = false;
  createForm: FormGroup;
  selectedImageFile: File | null = null;
  imageError: string | null = null;
  manufacturers: ManufacturerShort[] = [];

  @ViewChild('map', { static: false }) mapElement!: ElementRef;
  leafletMap!: L.Map;
  marker!: L.Marker;
  defaultLat = 44.7665;
  defaultLng = 17.187;
  defaultZoom = 15;

  private baseUrl = 'http://localhost:8080/vehicles';
  private manufacturerUrl = 'http://localhost:8080/manufacturers/short';

  constructor(
    private http: HttpClient,
    private fb: FormBuilder,
    private toastr: ToastrService,
    private router: Router
  ) {
    this.createForm = this.fb.group({
      vehicleUid: ['', Validators.required],
      vehicleModel: ['', Validators.required],
      vehicleManufacturerId: ['', Validators.required],
      vehiclePurchasePrice: ['', Validators.required],
      vehiclePricePerMinute: ['', Validators.required],
      maxSpeed: ['', Validators.required],
      vehicleY: [this.defaultLat, Validators.required],
      vehicleX: [this.defaultLng, Validators.required],
    });
  }

  ngOnInit() {
    this.loadScooters();
    this.loadManufacturers();
  }
  ngAfterViewInit() {}

  loadScooters() {
    let params = new HttpParams()
      .set('page', this.currentPage)
      .set('size', this.pageSize);
    if (this.searchTerm.trim())
      params = params.set('searchTerm', this.searchTerm.trim());

    this.http.get<any>(`${this.baseUrl}/eScooters`, { params }).subscribe({
      next: (r) => {
        this.escooters = r.content;
        this.totalElements = r.totalElements;
        this.totalPages = r.totalPages;
      },
      error: (_) => this.toastr.error('Error loading e‑scooters.'),
    });
  }

  loadManufacturers() {
    this.http.get<ManufacturerShort[]>(this.manufacturerUrl).subscribe({
      next: (list) => (this.manufacturers = list),
      error: (_) => this.toastr.error('Failed to load manufacturers.'),
    });
  }

  goToPage(p: number) {
    if (p < 0 || p >= this.totalPages) return;
    this.currentPage = p;
    this.loadScooters();
  }
  onSearch() {
    this.currentPage = 0;
    this.loadScooters();
  }

  onCsvFileSelected(ev: any) {
    const f: File = ev.target.files[0];
    if (!f) return;
    const fd = new FormData();
    fd.append('file', f);
    this.http
      .post(`${this.baseUrl}/uploadCSV`, fd, { responseType: 'text' })
      .subscribe({
        next: (m) => {
          this.toastr.info(
            (m || 'CSV uploaded successfully!').replace(/\n/g, '<br>'),
            'Success!',
            {
              timeOut: 10000,
              closeButton: true,
              extendedTimeOut: 0,
              tapToDismiss: false,
              enableHtml: true,
              positionClass: 'toast-bottom-right',
            }
          );
          this.loadScooters();
        },
        error: (e) => this.toastr.error(e.error || 'CSV upload failed'),
      });
  }

  /* ---------- CREATE MODAL ---------- */
  openCreateModal() {
    this.showCreateModal = true;
    this.createForm.reset({
      vehicleY: this.defaultLat,
      vehicleX: this.defaultLng,
    });
    this.selectedImageFile = null;
    this.imageError = null;
    setTimeout(() => this.initLeafletMap());
  }
  closeCreateModal() {
    this.showCreateModal = false;
  }

  initLeafletMap() {
    if (this.leafletMap) this.leafletMap.remove();
    this.leafletMap = L.map(this.mapElement.nativeElement).setView(
      [this.defaultLat, this.defaultLng],
      this.defaultZoom
    );
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution: '© OpenStreetMap',
    }).addTo(this.leafletMap);

    const icon = L.icon({
      iconUrl: 'assets/images/pin.png',
      iconSize: [50, 50],
      iconAnchor: [25, 50],
    });
    this.marker = L.marker([this.defaultLat, this.defaultLng], {
      draggable: true,
      icon,
    }).addTo(this.leafletMap);
    this.marker.on('dragend', () => {
      const ll = this.marker.getLatLng();
      this.createForm.patchValue({ vehicleY: ll.lat, vehicleX: ll.lng });
    });
  }

  onImageSelected(ev: any) {
    const f: File = ev.target.files[0];
    if (f) {
      this.selectedImageFile = f;
      this.imageError = null;
    }
  }

  isInvalid(f: string) {
    const c = this.createForm.get(f);
    return !!(c && c.invalid && (c.dirty || c.touched));
  }

  isInvalidField(fieldName: string): boolean {
    const field = this.createForm.get(fieldName);
    return !!(field && field.invalid && (field.dirty || field.touched));
  }

  onCreate() {
    if (!this.createForm.valid) {
      this.createForm.markAllAsTouched();
      return;
    }
    if (!this.selectedImageFile) {
      this.imageError = 'Image required';
      return;
    }

    const pricePerSecond = this.createForm.value.vehiclePricePerMinute / 60;
    const dto = {
      vehicleUid: this.createForm.value.vehicleUid,
      vehiclePurchasePrice: this.createForm.value.vehiclePurchasePrice,
      vehicleModel: this.createForm.value.vehicleModel,
      vehicleManufacturerId: this.createForm.value.vehicleManufacturerId,
      vehiclePricePerSecond: pricePerSecond,
      vehicleX: this.createForm.value.vehicleX,
      vehicleY: this.createForm.value.vehicleY,
      maxSpeed: this.createForm.value.maxSpeed,
    };

    const fd = new FormData();
    fd.append(
      'eScooterData',
      new Blob([JSON.stringify(dto)], { type: 'application/json' })
    );
    fd.append('image', this.selectedImageFile!);

    this.http.post(`${this.baseUrl}/createEScooter`, fd).subscribe({
      next: (_) => {
        this.toastr.success('E‑scooter created!');
        this.loadScooters();
        this.closeCreateModal();
      },
      error: (e) => { 
        if(e.status === 500) {
          this.toastr.error('E-Scooter with this UID already exists!');
          this.createForm.get('vehicleUid')?.setErrors({ duplicate: true });
          return;
        }
       }
    });
  }

  onViewDetails(sc: EScooterDto) {
    this.router.navigate(['/manager/escooter-details', sc.id]);
  }

  onDelete(sc: EScooterDto) {
    Swal.fire({
      title: `Delete ${sc.vehicleUid}?`,
      text: 'This action cannot be undone.',
      icon: 'question',
      showCancelButton: true,
      confirmButtonText: 'Delete',
      cancelButtonText: 'Cancel',
      buttonsStyling: false,
      customClass: {
        popup: 'rounded border',
        confirmButton: 'btn btn-outline-danger me-2',
        cancelButton: 'btn btn-outline-secondary',
      },
      backdrop: false,
    }).then((res) => {
      if (!res.isConfirmed) return;
      this.http
        .delete(`${this.baseUrl}/${sc.id}`, { responseType: 'text' })
        .subscribe({
          next: (m) => {
            this.toastr.success(m || 'E‑scooter deleted');
            this.loadScooters();
          },
          error: (e) => this.toastr.error(e.error || 'Delete failed'),
        });
    });
  }
}
