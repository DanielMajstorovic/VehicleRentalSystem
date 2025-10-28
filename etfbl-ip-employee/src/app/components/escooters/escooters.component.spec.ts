import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EscootersComponent } from './escooters.component';

describe('EscootersComponent', () => {
  let component: EscootersComponent;
  let fixture: ComponentFixture<EscootersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EscootersComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(EscootersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
