import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { Router } from '@angular/router';
import { VacuumCleanerDto } from 'src/app/dto/VacuumCleanerDto';
import { VacuumCleanerService } from 'src/app/services/vacuum.service';
import { Permissions } from 'src/app/constants/Permissions';
import { SearchDto } from 'src/app/dto/SearchDto';

@Component({
  selector: 'app-vacuum-list',
  templateUrl: './vacuum-list.component.html',
  styleUrls: ['./vacuum-list.component.css']
})
export class VacuumListComponent implements OnInit {
  vacuumCleaners: VacuumCleanerDto[] = [];
  permissions: number;
  userId: number;
  scheduleInput: { [key: number]: string } = {};

  searchName: string = '';
  searchStatus: string = '';
  dateFrom: string = '';
  dateTo: string = '';

  constructor(
    private vacuumCleanerService: VacuumCleanerService,
    private authService: AuthService,
    private router: Router
  ) {
    this.permissions = this.authService.getPermissions();
    this.userId = this.authService.getUserId();
  }
  get Permissions(): typeof Permissions {
    return Permissions;
  }

  ngOnInit(): void {
    this.loadVacuumCleaners();
  }

  loadVacuumCleaners(): void {
    this.vacuumCleanerService.list().subscribe(
      (data) => {
        this.vacuumCleaners = data;
      },
      (error) => {
        console.error('Error fetching vacuum cleaners:', error);
      }
    );
  }

  hasPermission(permission: number): boolean {
    return (this.permissions & permission) !== 0;
  }

  getStatusLabel(status: number): string {
    switch (status) {
      case 0: return 'Stopped';
      case 1: return 'Running';
      case 2: return 'Discharging';
      default: return 'Unknown';
    }
  }
  isSearchFormValid(): boolean {
    return !!this.searchName || !!this.searchStatus || !!this.dateFrom || !!this.dateTo;
  }

  searchVacuums(): void {
    const searchDto: SearchDto = {
      name: this.searchName !== "" ? this.searchName : null,
      status: this.searchStatus ? parseInt(this.searchStatus, 10) : null,
      dateFrom: this.dateFrom ? new Date(this.dateFrom).getTime() : null,
      dateTo: this.dateTo ? new Date(this.dateTo).getTime() : null
    };

    this.vacuumCleanerService.search(searchDto).subscribe(
      (data) => {
        this.vacuumCleaners = data; // Update the list with the search result
      },
      (error) => {
        console.error('Error searching vacuum cleaners:', error);
      }
    );
  }

  startCleaner(id: number): void {
    this.vacuumCleanerService.start(id).subscribe(() => this.loadVacuumCleaners());
  }

  stopCleaner(id: number): void {
    this.vacuumCleanerService.stop(id).subscribe(() => this.loadVacuumCleaners());
  }

  dischargeCleaner(id: number): void {
    this.vacuumCleanerService.discharge(id).subscribe(() => this.loadVacuumCleaners());
  }

  removeCleaner(id: number): void {
    this.vacuumCleanerService.remove(id).subscribe(() => this.loadVacuumCleaners());
  }

  scheduleAction(id: number, action: 'start' | 'stop' | 'discharge'): void {
    const scheduledTime = this.scheduleInput[id] ? new Date(this.scheduleInput[id]).getTime() : null;
    if (scheduledTime) {
      const serviceMethod = action === 'start' ? this.vacuumCleanerService.scheduleStart :
        action === 'stop' ? this.vacuumCleanerService.scheduleStop :
          this.vacuumCleanerService.scheduleDischarge;
      serviceMethod.call(this.vacuumCleanerService, id, scheduledTime).subscribe(() => this.loadVacuumCleaners());
    }
  }

  navigateToAddVacuum(): void {
    this.router.navigate(['/add-vacuum']);
  }

  navigateToUserList(): void {
    this.router.navigate(['/user-list']);
  }

  navigateToErrorList(): void {
    this.router.navigate(['/error-list']);
  }
}