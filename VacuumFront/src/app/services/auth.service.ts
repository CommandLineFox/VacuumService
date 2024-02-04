import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { TokenRequestDto } from '../dto/TokenRequestDto';
import { TokenResponseDto } from '../dto/TokenResponseDto';
import { environment } from 'src/environments/environment';
import { jwtDecode } from 'jwt-decode';
import { CustomTokenPayload } from '../security/CustomTokenPayload';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private userApiUrl = `${environment.userApiUrl}/api/user`;

  constructor(private http: HttpClient) {
  }

  getPermissions(): number {
    const token = localStorage.getItem("token");
    if (!token) {
      return 0;
    }
    const decodedToken = jwtDecode<CustomTokenPayload>(token);
    return decodedToken.permissions;
  }

  getUserId(): number {
    const token = localStorage.getItem("token");
    if (!token) {
      return 0;
    }
    const decodedToken = jwtDecode<CustomTokenPayload>(token);
    return decodedToken.id;
  }

  login(tokenRequest: TokenRequestDto): Observable<TokenResponseDto> {
    console.log(tokenRequest);
    return this.http.post<TokenResponseDto>(`${this.userApiUrl}/login`, tokenRequest);
  }
}
