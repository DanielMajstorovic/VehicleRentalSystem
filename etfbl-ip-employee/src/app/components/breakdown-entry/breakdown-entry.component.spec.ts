import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BreakdownEntryComponent } from './breakdown-entry.component';

describe('BreakdownEntryComponent', () => {
  let component: BreakdownEntryComponent;
  let fixture: ComponentFixture<BreakdownEntryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BreakdownEntryComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(BreakdownEntryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
