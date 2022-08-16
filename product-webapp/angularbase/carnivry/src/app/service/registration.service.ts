import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { PostUser } from '../model/post-user';
import { Event } from '../model/event';
import { Ticket } from '../model/ticket';

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {

  private githubAuthorizeEndpoint = '/registration/oauth2/authorization/github'
  private googleAuthorizeEndpoint = '/registration/oauth2/authorization/google'

  private githubTokenEndpoint = '/registration/login/oauth2/code/github';
  private googleTokenEndpoint = '/registration/login/oauth2/code/google';

  private baseUrl= environment.baseUrl;

  private controllerUrl="/api/v1";
  private registrationBaseUrl= environment.baseUrl+"/registration"+this.controllerUrl;



  constructor(private myClient: HttpClient) { }
  googleLogin(){
    window.open(this.baseUrl + this.googleAuthorizeEndpoint, '_self');
  }

  githubLogin() {
    window.open(this.baseUrl + this.githubAuthorizeEndpoint, '_self');
  }

  googleFetchToken(code: any, state: any): Observable<any> {
    return this.myClient.get(this.baseUrl + this.googleTokenEndpoint + '?code=' + code + '&state=' + state);
  }
  
  githubFetchToken(code: any, state: any): Observable<any> {
    return this.myClient.get(this.baseUrl + this.githubTokenEndpoint + '?code=' + code + '&state=' + state);
  }

  register(user:PostUser):Observable<any>{
    return this.myClient.post(this.registrationBaseUrl+"/registration",user);
  }

  registerSocialUser(user:PostUser):Observable<any>{
    return this.myClient.post(this.registrationBaseUrl+"/registration/socialLogin",user);
  }

  isVerified(email:String)
  {
    return this.myClient.get(this.registrationBaseUrl+"/emailVerifiedStatus/"+email);
  }
  resendVerificationEmail(email:String)
  {
    return this.myClient.get(this.registrationBaseUrl+"/resendVerificationToken"+'?email='+email);
  }

  isUserPresent(email:String): Observable<any>{
    return this.myClient.get(this.registrationBaseUrl+"/userCheck/"+email);
  }

  sendNewEmailVerificationLink(obj:any){
    return this.myClient.post(this.registrationBaseUrl+"/NewEmailAddition",obj, {responseType:'text'});
  }

  isNewEmailVerified(obj:any){
    return this.myClient.post(this.registrationBaseUrl+"/newEmailVerificationStatus",obj, {responseType:'text'});
  }

  fetchCarnivryName(email:String):Observable<any>{
    return this.myClient.get(this.registrationBaseUrl+"/username/"+email, {responseType: 'text'});
  }

  getAllGenres():Observable<string[]>
  {
    return this.myClient.get<string[]>(this.registrationBaseUrl+"/allGenres");
  }

  getGenres(email:string):Observable<string[]>
  {
    return this.myClient.get<string[]>(this.registrationBaseUrl+"/genres/"+email);
  }

  addGenre(myGenre:any)
  {
    return this.myClient.post(this.registrationBaseUrl+"/saveGenres",myGenre, {responseType: 'text'});
  }

  addAddress(address:any)
  {
    return this.myClient.post(this.registrationBaseUrl+"/addressAddition",address, {responseType: 'text'});
  }

  addDOB(dob:any)
  {
    return this.myClient.post(this.registrationBaseUrl+"/dobAddition",dob, {responseType: 'text'});
  }

  getPostedEvents(email:string):Observable<Event[]>
  {
    return this.myClient.get<Event[]>(this.registrationBaseUrl+"/getPostedEvents/"+email);
  }

  getPastEvents(email:string):Observable<Ticket[]>
  {
    return this.myClient.get<Ticket[]>(this.registrationBaseUrl+"/pastEvents/"+email);
  }

  getUpcomingEvents(email:string):Observable<Ticket[]>
  {
    return this.myClient.get<Ticket[]>(this.registrationBaseUrl+"/upcomingEvents/"+email);
  }


  addProfilePic(myProfilePic:any)
  {
    return this.myClient.post(this.registrationBaseUrl+"/ProfilePicAddition",myProfilePic, {responseType: 'text'});
  }

  getProfilePic(email:string){
    return this.myClient.get(this.registrationBaseUrl+"/getProfilePic/"+email,{responseType: 'text'} );
  }


  addEventToWishlist(obj:any){
    return this.myClient.post(this.registrationBaseUrl+"/WishlistAddition",obj),{responseType: 'text'};

  }

  getWishlist(email:string):Observable<Event[]>{
    return this.myClient.get<Event[]>(this.registrationBaseUrl+"/wishlist/"+email);
  }

  isLoggedIn(): boolean {
    const token = this.getToken();
    return token != null;
  }

  logout(): Observable<any> {
    return this.myClient.post(this.baseUrl+"/registration"+ '/logout', this.getToken());
  }

  updateToken(token: any) {
    localStorage.setItem('token', token);
  }

  getToken() {
    return localStorage.getItem('token');
  }

  removeToken() {
    localStorage.removeItem('token');
  }

  updateEmail(email:any){
    localStorage.setItem('email', email);
  }

  getEmail(){
    return localStorage.getItem('email');
  }

  removeEmail(){
    localStorage.removeItem('email');
  }

  updateName(name:any){
    localStorage.setItem('name', name);
  }

  getName(){
    return localStorage.getItem('name');
  }

  removeName(){
    localStorage.removeItem('name');
  }

  updateAuthProvider(authProvider:any){
    localStorage.setItem('authProvider', authProvider);
  }

  getAuthProvider(){
    return localStorage.getItem('authProvider');
  }

  removeAuthProvider(){
    localStorage.removeItem('authProvider');
  }

  updateAvatarUrl(url:any){
    localStorage.setItem('avatar_url',url);
  }

  getAvatarUrl(){
    return localStorage.getItem('avatar_url');
  }
  
  removeAvatarUrl(){
    localStorage.removeItem('avatar_url');
  }

}
