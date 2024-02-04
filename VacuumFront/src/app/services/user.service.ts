import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, Subject } from 'rxjs';
import { environment } from 'src/environments/environment';
import { UserDto } from '../dto/UserDto';
import { CreateUserDto } from '../dto/CreateUserDto';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = `${environment.userApiUrl}/api/user`;
  private refreshUserListSource = new Subject<void>();
  refreshUserList$ = this.refreshUserListSource.asObservable();

  constructor(private http: HttpClient) { }

  private getHeaders(): HttpHeaders {
    const token = localStorage.getItem('token'); // Assuming token is stored in local storage
    return new HttpHeaders({
      Authorization: `Bearer ${token}`
    });
  }

  getUsers(): Observable<UserDto[]> {
    return this.http.get<UserDto[]>(`${this.apiUrl}/list`, { headers: this.getHeaders() });
  }

  addUser(user: CreateUserDto): Observable<UserDto> {
    return this.http.post<UserDto>(`${this.apiUrl}/add`, user, { headers: this.getHeaders() });
  }

  updateUser(user: CreateUserDto): Observable<CreateUserDto> {
    return this.http.post<CreateUserDto>(`${this.apiUrl}/update`, user, { headers: this.getHeaders() });
  }

  deleteUser(id: number): Observable<UserDto> {
    return this.http.delete<UserDto>(`${this.apiUrl}/delete/${id}`, { headers: this.getHeaders() });
  }

  getUserById(id: number): Observable<UserDto> {
    return this.http.get<UserDto>(`${this.apiUrl}/get/${id}`, { headers: this.getHeaders() });
  }

  notifyUserListRefresh() {
    this.refreshUserListSource.next();
  }
}
