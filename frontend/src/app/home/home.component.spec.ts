import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { Observable } from 'rxjs';
import { of } from 'rxjs';

import { HomeComponent } from './home.component';
import { TravellogService } from '../services/travellog.service';
import { TravelLog } from '../shared/travelLog';

class MockTravellogService {

  logs: TravelLog[] = [
    {
        id: '852a751c-d99f-4eb8-aded-844ddb386ab8',
        date: '2022-12-02',
        registrationNumber: '121 ABT',
        owner: 'Dawidek',
        description: 'going to Tartu',
        route: 'Tallin-Tartu',
        startOdometer: 4568,
        endOdometer: 7513
    },
    {
        id: 'b3204c4c-c6f0-4db2-bc32-3ba138ef1f4d',
        date: '2022-12-22',
        registrationNumber: '121 ABT',
        owner: 'Dawidek',
        description: 'trip to Tallin zoo',
        route: 'Tartu-Tallin',
        startOdometer: 1234,
        endOdometer: 5645
    }]

  getTravelLogs(): Observable<TravelLog[]> {
    return of(this.logs)
  }

  editTravelLog(): Observable<TravelLog> {
    return of({
      id: '852a751c-d99f-4eb8-aded-844ddb386ab8',
      date: '2022-12-02',
      registrationNumber: '121 ABT',
      owner: 'Dawidek',
      description: 'going to Tartu',
      route: 'Tallin-Tartu',
      startOdometer: 777,
      endOdometer: 888
    })
  }

  createTravelLog(): Observable<TravelLog> {
    return of({
      id: 'created',
      date: '2022-12-02',
      registrationNumber: '121 ABT',
      owner: 'Dawidek',
      description: 'going to Tartu',
      route: 'Tallin-Tartu',
      startOdometer: 777,
      endOdometer: 888
    })
  }

  deleteTravelLog() {
    return of(1)
  }
}

describe('HomeComponent', () => {
  let component: HomeComponent;
  let fixture: ComponentFixture<HomeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RouterTestingModule, ReactiveFormsModule, HttpClientModule],
      declarations: [ HomeComponent ],
      providers: [{
        provide: TravellogService, useClass: MockTravellogService
      }]
    })
    .compileComponents()
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(HomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();

    component.ngOnInit()
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('form should be invalid', () => {
    component.travelLogForm.controls['owner'].setValue('')
    component.travelLogForm.controls['registrationnumber'].setValue('')
    component.travelLogForm.controls['date'].setValue('')
    component.travelLogForm.controls['route'].setValue('')
    component.travelLogForm.controls['description'].setValue('')
    component.travelLogForm.controls['id'].setValue('')
    component.travelLogForm.controls['startodometer'].setValue(0)
    component.travelLogForm.controls['endodometer'].setValue(0)

    expect(component.travelLogForm.valid).toBeFalsy()
  });

  it('form should be valid', () => {
    component.travelLogForm.controls['owner'].setValue('Nata')
    component.travelLogForm.controls['registrationnumber'].setValue('145 FR')
    component.travelLogForm.controls['date'].setValue('2022-01-01')
    component.travelLogForm.controls['route'].setValue('Somwhere to good place')
    component.travelLogForm.controls['description'].setValue('Bags... Bags everywhere...')
    component.travelLogForm.controls['id'].setValue('tested')
    component.travelLogForm.controls['startodometer'].setValue(123)
    component.travelLogForm.controls['endodometer'].setValue(456)

    expect(component.travelLogForm.valid).toBeTruthy()
  });

  it('form should be invalid', () => {
    component.searchForm.controls['owner'].setValue('AA')
    component.searchForm.controls['registrationnumber'].setValue('')
    component.searchForm.controls['begindate'].setValue('')
    component.searchForm.controls['enddate'].setValue('')

    expect(component.searchForm.valid).toBeFalsy()
  });

  it('form should be invalid', () => {
    component.searchForm.controls['owner'].setValue('AAA!')
    component.searchForm.controls['registrationnumber'].setValue('')
    component.searchForm.controls['begindate'].setValue('')
    component.searchForm.controls['enddate'].setValue('')

    expect(component.searchForm.valid).toBeFalsy()
  });

  it('form should be invalid', () => {
    component.searchForm.controls['owner'].setValue('Anastasia')
    component.searchForm.controls['registrationnumber'].setValue('')
    component.searchForm.controls['begindate'].setValue('')
    component.searchForm.controls['enddate'].setValue('')

    expect(component.searchForm.valid).toBeTruthy()
  });

  it('should should have two users', () => {
    expect(component.travelLogs.length).toEqual(2)
  });

  it('should set travellog form inputs to edit', () => {
    component.edit('852a751c-d99f-4eb8-aded-844ddb386ab8')

    expect(component.travelLogForm.value).toEqual({
      id: '852a751c-d99f-4eb8-aded-844ddb386ab8',
      date: '2022-12-02',
      registrationnumber: '121 ABT',
      owner: 'Dawidek',
      description: 'going to Tartu',
      route: 'Tallin-Tartu',
      startodometer: 4568,
      endodometer: 7513
    })
  });

  it('should edit travellog', () => {
    component.travelLogForm.controls['owner'].setValue('Dawidek')
    component.travelLogForm.controls['registrationnumber'].setValue('121 ABT')
    component.travelLogForm.controls['date'].setValue('2022-12-02')
    component.travelLogForm.controls['route'].setValue('Tallin-Tartu')
    component.travelLogForm.controls['description'].setValue('going to Tartu')
    component.travelLogForm.controls['id'].setValue('852a751c-d99f-4eb8-aded-844ddb386ab8')
    component.travelLogForm.controls['startodometer'].setValue(777)
    component.travelLogForm.controls['endodometer'].setValue(888)

    component.submitTravelLogForm()

    expect(component.travelLogs.find(e => e.id === '852a751c-d99f-4eb8-aded-844ddb386ab8')).toEqual({
      id: '852a751c-d99f-4eb8-aded-844ddb386ab8',
      date: '2022-12-02',
      registrationNumber: '121 ABT',
      owner: 'Dawidek',
      description: 'going to Tartu',
      route: 'Tallin-Tartu',
      startOdometer: 777,
      endOdometer: 888
    })
  });

  it('should create travellog', () => {
    component.travelLogForm.controls['owner'].setValue('Dawidek')
    component.travelLogForm.controls['registrationnumber'].setValue('121 ABT')
    component.travelLogForm.controls['date'].setValue('2022-12-02')
    component.travelLogForm.controls['route'].setValue('Tallin-Tartu')
    component.travelLogForm.controls['description'].setValue('going to Tartu')
    component.travelLogForm.controls['id'].setValue('')
    component.travelLogForm.controls['startodometer'].setValue(777)
    component.travelLogForm.controls['endodometer'].setValue(888)

    component.submitTravelLogForm()

    expect(component.travelLogs.length).toEqual(3)
  });

  it('should delete travellog', () => {
    component.delete('852a751c-d99f-4eb8-aded-844ddb386ab8')

    expect(component.travelLogs.length).toEqual(1)
  });
  
});
