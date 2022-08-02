export class PostUser {
    name:string;
    email:string;
    dob:Date;
    password:string;
    matchingPassword:string;

    public getName(){
        return this.name;
    }

    public setName(name: string): void {
        this.name = name;
    }

    public getEmail(){
        return this.email;
    }

    public setEmail(email: string): void {
        this.email = email;
    }

    public getDob(){
        return this.dob;
    }

    public setDob(dob: Date){
        this.dob= dob;
    }

    public getPassword(){
        return this.password;
    }

    public setPassword(password: string): void {
        this.password = password;
    }

    public getMatchingPassword(): string {
        return this.matchingPassword;
    }

    public setMatchingPassword(matchingPassword: string): void {
        this.matchingPassword = matchingPassword;
    }


    constructor(name:string, email:string,dob:Date, password:string, matchingPassword:string){
        this.name= name;
        this.email= email;
        this.dob= dob;
        this.password= password;
        this.matchingPassword= matchingPassword;
    }
}
