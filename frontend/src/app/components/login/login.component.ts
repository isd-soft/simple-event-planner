import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
  }
  
  user = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('',[Validators.required]),
  });

  hide = true;

  //==== HTTP request ====//

  readonly ROOT_URL = "http://localhost:8080";

  submit() {
    if(this.user.valid) {
      this.http.post(this.ROOT_URL + "/login", this.user.value).subscribe(token => { console.log(token); } );
   } else {
     alert("Please provide valid credentials!")
   }
   
  }

}