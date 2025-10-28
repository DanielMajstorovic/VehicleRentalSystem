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

interface EbikeDto {
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
  autonomy: number;
}

interface ManufacturerShort {
  id: number;
  name: string;
  country: string;
}

@Component({
  selector: 'app-ebikes-manager',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, FormsModule],
  templateUrl: './ebikes-manager.component.html',
  styleUrls: ['./ebikes-manager.component.scss'],
})
export class EbikesManagerComponent implements OnInit, AfterViewInit {
  ebikes: EbikeDto[] = [];
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
      vehiclePricePerHour: ['', Validators.required],
      autonomy: ['', Validators.required],
      vehicleY: [this.defaultLat, Validators.required],
      vehicleX: [this.defaultLng, Validators.required],
    });
  }


  ngOnInit(): void {
    this.loadEbikes();
    this.loadManufacturers();
  }

  ngAfterViewInit(): void {
  }


  loadEbikes() {
    let params = new HttpParams()
      .set('page', this.currentPage)
      .set('size', this.pageSize);
    if (this.searchTerm.trim())
      params = params.set('searchTerm', this.searchTerm.trim());

    this.http.get<any>(`${this.baseUrl}/eBikes`, { params }).subscribe({
      next: (res) => {
        this.ebikes = res.content;
        this.totalElements = res.totalElements;
        this.totalPages = res.totalPages;
      },
      error: (_) => this.toastr.error('Error while fetching e‑bikes list.'),
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
    this.loadEbikes();
  }
  onSearch() {
    this.currentPage = 0;
    this.loadEbikes();
  }

  onCsvFileSelected(event: any) {
    const file: File = event.target.files[0];
    if (!file) return;
    const fd = new FormData();
    fd.append('file', file);
    this.http
      .post(`${this.baseUrl}/uploadCSV`, fd, { responseType: 'text' })
      .subscribe({
        next: (msg) => {
          this.toastr.info(
            (msg || 'CSV uploaded successfully!').replace(/\n/g, '<br>'),
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
          this.loadEbikes();
        },
        error: (err) => this.toastr.error(err.error || 'CSV upload failed'),
      });
  }

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

    const customIcon = L.icon({
      iconUrl: 'assets/images/pin.png',
      iconSize: [50, 50],
      iconAnchor: [25, 50],
    });
    this.marker = L.marker([this.defaultLat, this.defaultLng], {
      draggable: true,
      icon: customIcon,
    }).addTo(this.leafletMap);
    this.marker.on('dragend', () => {
      const ll = this.marker.getLatLng();
      this.createForm.patchValue({ vehicleY: ll.lat, vehicleX: ll.lng });
    });
  }

  onImageSelected(ev: any) {
    const file: File = ev.target.files[0];
    if (file) {
      this.selectedImageFile = file;
      this.imageError = null;
    }
  }

  isInvalid(f: string) {
    const c = this.createForm.get(f);
    return !!(c && c.invalid && (c.dirty || c.touched));
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

    const pricePerHour = this.createForm.value.vehiclePricePerHour;
    const pricePerSecond = pricePerHour / 3600;

    const dto = {
      vehicleUid: this.createForm.value.vehicleUid,
      vehiclePurchasePrice: this.createForm.value.vehiclePurchasePrice,
      vehicleModel: this.createForm.value.vehicleModel,
      vehicleManufacturerId: this.createForm.value.vehicleManufacturerId,
      vehiclePricePerSecond: pricePerSecond,
      vehicleX: this.createForm.value.vehicleX,
      vehicleY: this.createForm.value.vehicleY,
      autonomy: this.createForm.value.autonomy,
    };

    const fd = new FormData();
    fd.append(
      'eBikeData',
      new Blob([JSON.stringify(dto)], { type: 'application/json' })
    );
    fd.append('image', this.selectedImageFile!);

    this.http.post(`${this.baseUrl}/createEBike`, fd).subscribe({
      next: (_) => {
        this.toastr.success('E‑bike created!');
        this.loadEbikes();
        this.closeCreateModal();
      },
      error: (err) =>{ 
        if(err.status === 500) {
          this.toastr.error('E-Bike with this UID already exists!');
          this.createForm.get('vehicleUid')?.setErrors({ duplicate: true });
          return;
        }
       }
    });
  }

  onViewDetails(bike: EbikeDto) {
    this.router.navigate(['/manager/ebike-details', bike.id]);
  }

  isInvalidField(fieldName: string): boolean {
    const field = this.createForm.get(fieldName);
    return !!(field && field.invalid && (field.dirty || field.touched));
  }

  onDelete(bike: EbikeDto) {
    Swal.fire({
      title: `Delete ${bike.vehicleUid}?`,
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
      allowOutsideClick: false,
    }).then((result) => {
      if (!result.isConfirmed) return;

      this.http
        .delete(`${this.baseUrl}/${bike.id}`, { responseType: 'text' })
        .subscribe({
          next: (msg) => {
            this.toastr.success(msg || 'E‑bike deleted');
            this.loadEbikes();
          },
          error: (err) => this.toastr.error(err.error || 'Delete failed'),
        });
    });
  }
}
