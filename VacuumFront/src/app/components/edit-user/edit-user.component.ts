import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { Permissions } from 'src/app/constants/Permissions';
import { UserService } from 'src/app/services/user.service';
import { UserDto } from 'src/app/dto/UserDto';
import { CreateUserDto } from 'src/app/dto/CreateUserDto';

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.css']
})
export class EditUserComponent implements OnInit {
  originalUser: UserDto;
  editedUser: CreateUserDto;
  userPermissions: number;

  constructor(private userService: UserService, private authService: AuthService, private route: ActivatedRoute, private router: Router) {
    this.userPermissions = this.authService.getPermissions();
    this.originalUser = {} as UserDto;
    this.editedUser = {} as CreateUserDto;
  }

  ngOnInit() {
    if (!this.hasPermission(Permissions.updateUserPermission)) {
      this.router.navigate(['/']);
      return;
    }

    const userId = this.route.snapshot.params['id'];
    this.userService.getUserById(userId).subscribe(
      user => {
        this.originalUser = user;
        this.editedUser = { ...user };
      },
      error => {
        console.error('Error fetching user:', error);
        this.router.navigate(['/user-list']);
      }
    );
  }

  get Permissions(): typeof Permissions {
    return Permissions;
  }

  hasPermission(permission: number): boolean {
    return (this.userPermissions & permission) !== 0;
  }

  updateUser() {
    if (!this.isFormChanged()) {
      alert('At least one field must be changed.');
      return;
    }

    this.userService.updateUser(this.editedUser).subscribe(() => {
      this.router.navigate(['/user-list']);
    });
  }

  isFormChanged(): boolean {
    return JSON.stringify(this.originalUser) !== JSON.stringify(this.editedUser);
  }
}
