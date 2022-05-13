import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TravellogService } from '../services/travellog.service';

@Component({
  selector: 'app-report',
  templateUrl: './report.component.html',
  styleUrls: ['./report.component.scss']
})
export class ReportComponent implements OnInit {

  totalAmount!: number
  reports!: [string, any][]

  constructor(
    private tlservice: TravellogService,
    private router: Router
    ) { }

  ngOnInit(): void {
    this.tlservice.getLogsForReport()
      .subscribe(response =>{
        this.reports=Object.entries(response[0]), 
        this.totalAmount = response[1]
      },
      err => this.router.navigateByUrl('/error')
      ); 
  }


}
