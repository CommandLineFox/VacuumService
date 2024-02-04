import { Component, OnInit } from '@angular/core';
import { ErrorService } from 'src/app/services/error.service';
import { ErrorDto } from 'src/app/dto/ErrorDto';
import { AuthService } from 'src/app/services/auth.service';
import { Permissions } from 'src/app/constants/Permissions';

@Component({
  selector: 'app-error-list',
  templateUrl: './error-list.component.html',
  styleUrls: ['./error-list.component.css']
})
export class ErrorListComponent implements OnInit {
  errors: ErrorDto[] = [];
  permissions: number;

  constructor(private errorService: ErrorService,
    private authService: AuthService) {
    this.permissions = this.authService.getPermissions();
  }

  ngOnInit(): void {
    this.loadErrors();
  }

  get Permissions(): typeof Permissions {
    return Permissions;
  }

  hasPermission(permission: number): boolean {
    return (this.permissions & permission) !== 0;
  }

  loadErrors(): void {
    this.errorService.list().subscribe(
      (data) => {
        this.errors = data;
      },
      (error) => {
        console.error('Error fetching errors:', error);
      }
    );
  }
}
