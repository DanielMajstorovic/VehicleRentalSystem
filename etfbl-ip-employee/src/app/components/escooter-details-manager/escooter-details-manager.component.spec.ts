import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EscooterDetailsManagerComponent } from './escooter-details-manager.component';

describe('EscooterDetailsManagerComponent', () => {
  let component: EscooterDetailsManagerComponent;
  let fixture: ComponentFixture<EscooterDetailsManagerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EscooterDetailsManagerComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(EscooterDetailsManagerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
