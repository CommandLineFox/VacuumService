export interface CreateUserDto {
    firstName: string;
    lastName: string;
    mail: string;
    password: string;
    permissions: number;
}