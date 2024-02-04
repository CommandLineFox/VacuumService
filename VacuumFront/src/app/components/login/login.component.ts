import { Component } from '@angular/core';
import { TokenRequestDto } from "../../dto/TokenRequestDto";
import { AuthService } from 'src/app/services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})

export class LoginComponent {
  mail: string = '';
  password: string = '';
  errorMessage: string = '';
  loggedIn: boolean = false;

  constructor(private authService: AuthService, private router: Router) {
  }

  ngOnInit() {
    if (localStorage.getItem('token')) {
      this.loggedIn = true;
      this.router.navigate(['/vacuum-list']);
    }
  }

  login() {
    if (!this.mail.trim() || !this.password.trim()) {
      this.errorMessage = 'Please enter both email and password.';
      return;
    }

    const tokenRequest: TokenRequestDto = { mail: this.mail.trim(), password: this.password.trim() };
    this.authService.login(tokenRequest).subscribe((data) => {
      localStorage.setItem('token', data.token);
      this.router.navigate(['/vacuum-list']);
    }, (error) => {
      console.error('Login error:', error);
      this.errorMessage = 'Login failed. Please check your credentials.';
    });
  };
}
