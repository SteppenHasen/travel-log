import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ProcessHTTPMsgService } from '../services/process-httpmsg.service';

@Component({
  selector: 'app-error-page',
  templateUrl: './error-page.component.html',
  styleUrls: ['./error-page.component.scss']
})
export class ErrorPageComponent implements OnInit {

  errorStatus = this.errorService.errorStatus
  errorStatusText = this.errorService.errorStatusText
  errorStatusMessage = this.errorService.errorStatusMessage

  constructor(
    private router: Router,
    private errorService: ProcessHTTPMsgService
    ) { }

  ngOnInit(): void {
  }

}
