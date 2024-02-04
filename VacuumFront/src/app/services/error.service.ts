import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ErrorDto } from '../dto/ErrorDto';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ErrorService {
  private errorApiUrl = `${environment.vacuumApiUrl}/api/error`;

  constructor(private http: HttpClient) { }

  private getHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders({
      Authorization: `Bearer ${token}`
    });
  }
  list(): Observable<ErrorDto[]> {
    return this.http.get<ErrorDto[]>(`${this.errorApiUrl}/list`, { headers: this.getHeaders() });
  }
}
