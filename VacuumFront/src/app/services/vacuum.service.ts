import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { VacuumCleanerDto } from '../dto/VacuumCleanerDto';
import { environment } from 'src/environments/environment';
import { CreateVacuumCleanerDto } from '../dto/CreateVacuumCleanerDto';
import { SearchDto } from '../dto/SearchDto';

@Injectable({
  providedIn: 'root'
})
export class VacuumCleanerService {
  private vacuumApiUrl = `${environment.vacuumApiUrl}/api/vacuum`;

  constructor(private http: HttpClient) { }

  private getHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders({
      Authorization: `Bearer ${token}`
    });
  }

  list(): Observable<VacuumCleanerDto[]> {
    return this.http.get<VacuumCleanerDto[]>(`${this.vacuumApiUrl}/list`, { headers: this.getHeaders() });
  }

  add(createVacuumCleanerDto: CreateVacuumCleanerDto): Observable<VacuumCleanerDto> {
    return this.http.put<VacuumCleanerDto>(`${this.vacuumApiUrl}/add`, createVacuumCleanerDto, { headers: this.getHeaders() });
  }

  remove(id: number): Observable<any> {
    return this.http.delete(`${this.vacuumApiUrl}/remove/${id}`, { headers: this.getHeaders() });
  }

  start(id: number): Observable<any> {
    return this.http.get(`${this.vacuumApiUrl}/start/${id}`, { headers: this.getHeaders() });
  }

  stop(id: number): Observable<any> {
    return this.http.get(`${this.vacuumApiUrl}/stop/${id}`, { headers: this.getHeaders() });
  }

  discharge(id: number): Observable<any> {
    return this.http.get(`${this.vacuumApiUrl}/discharge/${id}`, { headers: this.getHeaders() });
  }

  scheduleStart(id: number, scheduledAction: number): Observable<any> {
    return this.http.get(`${this.vacuumApiUrl}/start/${id}/${scheduledAction}`, { headers: this.getHeaders() });
  }

  scheduleStop(id: number, scheduledAction: number): Observable<any> {
    return this.http.get(`${this.vacuumApiUrl}/stop/${id}/${scheduledAction}`, { headers: this.getHeaders() });
  }

  scheduleDischarge(id: number, scheduledAction: number): Observable<any> {
    return this.http.get(`${this.vacuumApiUrl}/discharge/${id}/${scheduledAction}`, { headers: this.getHeaders() });
  }

  search(searchDto: SearchDto): Observable<VacuumCleanerDto[]> {
    return this.http.post<VacuumCleanerDto[]>(`${this.vacuumApiUrl}/search`, searchDto, { headers: this.getHeaders() });
  }

  private getToken(): string {
    return localStorage.getItem('token') || '';
  }
}
