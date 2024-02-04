import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { UserDto } from 'src/app/dto/UserDto';
import { UserService } from 'src/app/services/user.service';
import { Permissions } from 'src/app/constants/Permissions';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {
  users: UserDto[] = [];
  permissions: number;

  constructor(
    private userService: UserService,
    private authService: AuthService,
    private router: Router
  ) {
    this.permissions = this.authService.getPermissions();
  }

  ngOnInit() {
    this.permissions = this.authService.getPermissions();
    this.refreshUserList();
  }

  get Permissions(): typeof Permissions {
    return Permissions;
  }

  refreshUserList() {
    if (this.hasPermission(Permissions.readUserPermission)) {
      this.userService.getUsers().subscribe((users: UserDto[]) => {
        this.users = users;
      });
    }
  }

  deleteUser(userId: number) {
    if (this.hasPermission(Permissions.deleteUserPermission) && confirm('Are you sure you want to delete this user?')) {
      this.userService.deleteUser(userId).subscribe(() => {
        this.refreshUserList();
      });
    }
  }

  navigateToAddUser() {
    if (this.hasPermission(Permissions.createUserPermission)) {
      this.router.navigate(['/add-user']);
    }
  }

  navigateToEditUser(userId: number) {
    if (this.hasPermission(Permissions.updateUserPermission)) {
      this.router.navigate(['/edit-user', userId]);
    }
  }

  hasPermission(permission: number): boolean {
    return (this.permissions & permission) !== 0;
  }
}
