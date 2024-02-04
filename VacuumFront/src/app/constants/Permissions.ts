export class Permissions {
    public static readonly readUserPermission = 0b1;
    public static readonly createUserPermission = 0b10;
    public static readonly updateUserPermission = 0b100;
    public static readonly deleteUserPermission = 0b1000;
    public static readonly searchVacuumPermission = 0b10000;
    public static readonly startVacuumPermission = 0b100000;
    public static readonly stopVacuumPermission = 0b1000000;
    public static readonly dischargeVacuumPermission = 0b10000000;
    public static readonly addVacuumPermission = 0b100000000;
    public static readonly removeVacuumPermission = 0b1000000000;
}