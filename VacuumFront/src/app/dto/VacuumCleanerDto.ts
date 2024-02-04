export interface VacuumCleanerDto {
    cleanerId: number;
    name: string;
    status: number;
    nextStatus: number;
    active: boolean;
    userId: number;
    date: number;
    scheduledAction: number;
    cycleCount: number;
}