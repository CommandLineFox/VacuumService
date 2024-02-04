import { Component } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { Permissions } from 'src/app/constants/Permissions';
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/user.service';
import { CreateUserDto } from 'src/app/dto/CreateUserDto';

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.css']
})
export class AddUserComponent {
  firstName: string = '';
  lastName: string = '';
  mail: string = '';
  password: string = '';
  permissions: number = 0;
  userPermissions: number;

  constructor(private authService: AuthService, private userService: UserService, private router: Router) {
    this.userPermissions = this.authService.getPermissions();
  }

  get Permissions(): typeof Permissions {
    return Permissions;
  }

  hasPermission(permission: number): boolean {
    return (this.userPermissions & permission) !== 0;
  }

  addUser() {
    if (this.hasPermission(Permissions.createUserPermission)) {
      const newUser: CreateUserDto = {
        firstName: this.firstName,
        lastName: this.lastName,
        mail: this.mail,
        password: this.password,
        permissions: this.permissions
      };
      this.userService.addUser(newUser).subscribe(() => {
        this.router.navigate(['/user-list']); // Navigate to user list after adding user
      });
    }
  }

  isFormValid(): boolean {
    return !!this.firstName && !!this.lastName && !!this.mail && !!this.password;
  }
}
