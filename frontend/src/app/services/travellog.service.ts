import { Injectable } from '@angular/core';
import { TravelLog } from '../shared/travelLog';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { ProcessHTTPMsgService } from './process-httpmsg.service'; 

@Injectable({
  providedIn: 'root'
})
export class TravellogService {
  server_url = 'http://localhost:8080/api/'

  searchparams: {
    owner: string;
    registrationnumber: string;
    beginDate: string;
    endDate: string
  } = { 
    owner: '',
    registrationnumber: '',
    beginDate: '',
    endDate: ''
  }

  constructor(private http: HttpClient,
    private processHTTPMsgService: ProcessHTTPMsgService) { }

  getTravelLogs(): Observable<TravelLog[]> { 
    return this.http.get<TravelLog[]>(this.server_url+'travel-logs')
      .pipe(catchError(this.processHTTPMsgService.handleError));
  }

  getTravelLog(id: string): Observable<TravelLog> { 
    return this.http.get<TravelLog>(this.server_url+id)
    .pipe(catchError(this.processHTTPMsgService.handleError));
  }

  editTravelLog(travelLog: TravelLog): Observable<TravelLog> { 
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type':  'application/json'
      })
    };
    return this.http.post<TravelLog>(this.server_url+travelLog.id+':patch', travelLog, httpOptions)
      .pipe(catchError(this.processHTTPMsgService.handleError));
  }

  createTravelLog(travelLog: TravelLog): Observable<TravelLog> {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type':  'application/json'
      })
    };
    return this.http.post<TravelLog>(this.server_url+'save', travelLog, httpOptions)
      .pipe(catchError(this.processHTTPMsgService.handleError));
  }

  deleteTravelLog(id: string) {
    return this.http.delete(this.server_url+id+':destroy')
      .pipe(catchError(this.processHTTPMsgService.handleError));
  }

  getLogsForReport(): Observable<[Object, number]> {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type':  'application/json'
      })
    };
    return this.http.post<[Object, number]>(this.server_url+'report', this.searchparams, httpOptions)
      .pipe(catchError(this.processHTTPMsgService.handleError));
  }
}
