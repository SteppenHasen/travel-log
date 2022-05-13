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
  server_url = 'http://localhost:8080/api/travel-logs'

  searchparams: {
    owner: string;
    regnumber: string;
    begindate: string;
    enddate: string
  } = {
    owner: '',
    regnumber: '',
    begindate: '',
    enddate: ''
  }

  constructor(private http: HttpClient,
    private processHTTPMsgService: ProcessHTTPMsgService) { }

  getTravelLogs(): Observable<TravelLog[]> { 
    return this.http.get<TravelLog[]>(this.server_url+'/getTravelLogs')
      .pipe(catchError(this.processHTTPMsgService.handleError));
  }

  getTravelLog(id: string): Observable<TravelLog> { 
    return this.http.get<TravelLog>(this.server_url+'/find/'+id)
    .pipe(catchError(this.processHTTPMsgService.handleError));
  }

  editTravelLog(travelLog: TravelLog): Observable<TravelLog> { 
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type':  'application/json'
      })
    };
    return this.http.post<TravelLog>(this.server_url+'/edit/'+travelLog.id, travelLog, httpOptions)
      .pipe(catchError(this.processHTTPMsgService.handleError));
  }

  createTravelLog(travelLog: TravelLog): Observable<TravelLog> {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type':  'application/json'
      })
    };
    return this.http.post<TravelLog>(this.server_url+'/saveTravelLog', travelLog, httpOptions)
      .pipe(catchError(this.processHTTPMsgService.handleError));
  }

  deleteTravelLog(id: string) {
    return this.http.delete(this.server_url+'/delete/'+id)
      .pipe(catchError(this.processHTTPMsgService.handleError));
  }

  getLogsForReport(serchparams: object): Observable<Object> {
    return this.http.get<Object>(this.server_url+'/TravelLogsForReport')
      .pipe(catchError(this.processHTTPMsgService.handleError));
  }
}
