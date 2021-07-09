import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css'],
})
export class SignupComponent implements OnInit {
  constructor(private http: HttpClient) {}

  ngOnInit(): void {}

  user = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [Validators.required]),
    firstName: new FormControl('', [Validators.required]),
    lastName: new FormControl('', [Validators.required]),
    age: new FormControl('', [Validators.required]),
    phoneNumber: new FormControl('', [Validators.required]),
  });

  passwordCheck = new FormControl('', [Validators.required]);

  hide = true;

  readonly ROOT_URL = 'http://localhost:8080';

  submit() {
    if (this.user.valid) {
      if (this.passwordCheck.value === this.user.value.password) {
        this.http.post(this.ROOT_URL + "/register", this.user.value).subscribe(token => { console.log(token); } );
      } else alert("Passwords don't match!");
    } else {
      alert('Please provide valid credentials!');
    }
  }
}
