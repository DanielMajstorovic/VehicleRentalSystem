import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EscooterDetailsComponent } from './escooter-details.component';

describe('EscooterDetailsComponent', () => {
  let component: EscooterDetailsComponent;
  let fixture: ComponentFixture<EscooterDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EscooterDetailsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(EscooterDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
