import { Injectable } from '@angular/core';
import { throwError } from 'rxjs';
import { HttpErrorResponse } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ProcessHTTPMsgService {

  errorStatus!: number;
  errorStatusText!: string;
  errorStatusMessage!: string;

  constructor() { }

  public handleError(error: HttpErrorResponse | any) {

    this.errorStatus = error.status
    this.errorStatusText = error.errorStatusText
    this.errorStatusMessage = error.error.errorMessage

    return throwError("Ooops we have an error!");
  }
}