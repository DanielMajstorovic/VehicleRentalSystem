import { Component } from '@angular/core';
import { NgIf } from '@angular/common';
import { LoadingService } from '../../core/services/loading.service';

@Component({
  selector: 'app-spinner',
  standalone: true,
  imports: [NgIf],
  template: `
  @if (loading()) {
    <div class="spinner-wrapper">
      <div class="custom-spinner">
        <div class="dot"></div>
        <div class="dot"></div>
        <div class="dot"></div>
      </div>
    </div>
  }
`,
  styles: [`
  .spinner-wrapper {
    position: fixed;
    bottom: 20px;
    left: 20px;
    z-index: 1050;
  }

  .custom-spinner {
    display: flex;
    align-items: center;
    justify-content: center;
    gap: 6px;
  }

  .custom-spinner .dot {
    width: 10px;
    height: 10px;
    border-radius: 50%;
    background-color: #0d6efd;
    animation: bounce 0.6s infinite ease-in-out;
  }

  .custom-spinner .dot:nth-child(2) {
    animation-delay: 0.1s;
  }

  .custom-spinner .dot:nth-child(3) {
    animation-delay: 0.2s;
  }

  @keyframes bounce {
    0%, 80%, 100% {
      transform: scale(0.8);
      opacity: 0.6;
    }
    40% {
      transform: scale(1.4);
      opacity: 1;
    }
  }
`]
})
export class SpinnerComponent {
  loading = this.loadingService.isLoading;

  constructor(private loadingService: LoadingService) { }
}
