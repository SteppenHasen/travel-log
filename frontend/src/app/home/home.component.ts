import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { TravellogService } from '../services/travellog.service';
import { TravelLog } from '../shared/travelLog';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  minDate!: Date;
  maxDate!: Date;
  travelLogs!: TravelLog[];

  travelLogForm = this.fb.group({
    id: [''],
    route: [
      '', 
      [
        Validators.required,
        Validators.minLength(5),
      ],
    ],
    owner: [
      '',
      [
        Validators.required,
        Validators.minLength(3),
        Validators.pattern('^[_A-z0-9]*((-|s)*[_A-z0-9])*$'),
      ],
    ],
    description: ['', [Validators.required, Validators.minLength(10)]],
    registrationnumber: ['', Validators.required],
    date: ['', Validators.required],
    startodometer: ['', Validators.required],
    endodometer: ['', Validators.required],
  });

  searchForm = this.fb.group({
    owner: [
      '',
      [
        Validators.minLength(3),
        Validators.pattern('^[_A-z0-9]*((-|s)*[_A-z0-9])*$'),
      ],
    ],
    registrationnumber: [''],
    begindate: [''],
    enddate: ['']
  });

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private tlservice: TravellogService
    ) {
    const currentYear = new Date().getFullYear();
    this.minDate = new Date(currentYear - 20, 0, 1);
    this.maxDate = new Date(currentYear + 1, 11, 31);
  }

  ngOnInit(): void {
  this.tlservice.getTravelLogs()
     .subscribe( travellogs => this.travelLogs = travellogs);
  }

  delete(id: string) {
    this.tlservice.deleteTravelLog(id)
      .subscribe(log => {
        this.travelLogs.forEach(logs => {
          if (logs.id == id) {
            let index = this.travelLogs.indexOf(logs)
            this.travelLogs.splice(index, 1)
          }
        })
      })
  }

  edit(id: string) {
    let log: TravelLog = this.travelLogs.find(element => element.id === id)!
    console.log(this.travelLogs)

    this.travelLogForm.controls['owner'].setValue(log.owner)
    this.travelLogForm.controls['registrationnumber'].setValue(log.registrationNumber)
    this.travelLogForm.controls['date'].setValue(log.date)
    this.travelLogForm.controls['route'].setValue(log.route)
    this.travelLogForm.controls['description'].setValue(log.description)
    this.travelLogForm.controls['id'].setValue(id)
    this.travelLogForm.controls['startodometer'].setValue(log.startOdometer)
    this.travelLogForm.controls['endodometer'].setValue(log.endOdometer)
  }

  submitTravelLogForm() {
    let date = new Date(this.travelLogForm.value['date'])
    this.travelLogForm.controls['date'].setValue(date.toISOString().slice(0, 10))

    if (this.travelLogForm.value['id'] == '') {
      this.travelLogForm.controls['id'].setValue('create')
      this.tlservice.createTravelLog(this.travelLogForm.value)
        .subscribe(log => this.travelLogs.push(log))
    } else {
      let id = this.travelLogForm.value['id']
      this.tlservice.editTravelLog(this.travelLogForm.value)
        .subscribe(log => {
          for (let i=0; i < this.travelLogs.length; i++) {
            if (this.travelLogs[i].id == id) {
              this.travelLogs[i].date = log.date
              this.travelLogs[i].registrationNumber = log.registrationNumber
              this.travelLogs[i].route = log.route
              this.travelLogs[i].owner = log.owner
              this.travelLogs[i].description = log.description
              this.travelLogs[i].startOdometer = log.startOdometer
              this.travelLogs[i].endOdometer = log.endOdometer

              return
            }
          }
        })
    }

    this.travelLogForm.reset()
  }

  submitSearchForm() {
    this.tlservice.searchparams.owner = this.searchForm.value.owner
    this.tlservice.searchparams.registrationnumber = this.searchForm.value.registrationnumber
    if (this.searchForm.value.begindate != '' && this.searchForm.value.enddate != '') {
      this.tlservice.searchparams.beginDate = new Date(this.searchForm.value.begindate).toISOString().slice(0, 10)
      this.tlservice.searchparams.endDate = new Date(this.searchForm.value.enddate).toISOString().slice(0, 10)
    }
    
    this.searchForm.reset()
    this.router.navigateByUrl('/report')
  }
}
