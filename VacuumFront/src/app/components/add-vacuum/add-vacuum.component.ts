import { Component } from '@angular/core';
import { VacuumCleanerService } from 'src/app/services/vacuum.service';
import { AuthService } from 'src/app/services/auth.service';
import { Router } from '@angular/router';
import { Permissions } from 'src/app/constants/Permissions';
import { CreateVacuumCleanerDto } from 'src/app/dto/CreateVacuumCleanerDto';

@Component({
  selector: 'app-add-vacuum',
  templateUrl: './add-vacuum.component.html',
  styleUrls: ['./add-vacuum.component.css']
})
export class AddVacuumComponent {
  vacuumName: string = '';
  permissions: number;

  constructor(
    private vacuumCleanerService: VacuumCleanerService,
    private authService: AuthService,
    private router: Router
  ) {
    this.permissions = this.authService.getPermissions();
  }

  get Permissions(): typeof Permissions {
    return Permissions;
  }

  hasPermission(permission: number): boolean {
    return (this.permissions & permission) !== 0;
  }

  addVacuum(): void {
    if (this.vacuumName) {
      const createVacuumCleanerDto: CreateVacuumCleanerDto = {
        name: this.vacuumName,
        date: Date.now()
      };
      this.vacuumCleanerService.add(createVacuumCleanerDto).subscribe(() => {
        this.router.navigate(['/vacuum-list']);
      }, error => {
        console.error('Error adding vacuum cleaner:', error);
      });
    }
  }
}
