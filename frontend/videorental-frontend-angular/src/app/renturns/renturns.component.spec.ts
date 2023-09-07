import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RenturnsComponent } from './renturns.component';

describe('RenturnsComponent', () => {
  let component: RenturnsComponent;
  let fixture: ComponentFixture<RenturnsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RenturnsComponent]
    });
    fixture = TestBed.createComponent(RenturnsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
