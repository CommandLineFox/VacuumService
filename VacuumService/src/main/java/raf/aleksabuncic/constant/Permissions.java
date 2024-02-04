package raf.aleksabuncic.constant;

public class Permissions {
    public static final int readUserPermission = 0b1;
    public static final int createUserPermission = 0b10;
    public static final int updateUserPermission = 0b100;
    public static final int deleteUserPermission = 0b1000;
    public static final int searchVacuumPermission = 0b10000;
    public static final int startVacuumPermission = 0b100000;
    public static final int stopVacuumPermission = 0b1000000;
    public static final int dischargeVacuumPermission = 0b10000000;
    public static final int addVacuumPermission = 0b100000000;
    public static final int removeVacuumPermission = 0b1000000000;
}
