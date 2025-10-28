export enum Role {
    ADMINISTRATOR = 'ADMINISTRATOR',
    MANAGER = 'MANAGER',
    OPERATOR = 'OPERATOR',
  }
  
  export interface User {
    username: string;
    role: Role;
  }