import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

  user = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [Validators.required]),
    firstName: new FormControl('', [Validators.required]),
    lastName: new FormControl('', [Validators.required]),
    age: new FormControl('', [Validators.required]),
    phoneNumber: new FormControl('', [Validators.required]),
  });

  passwordCheck = new FormControl('', [Validators.required])

  hide = true;

  submit() {
    if(this.passwordCheck.value === this.user.value.password)
      console.log(this.user.value);
    else alert("Passwords don't match!");
  };

}
