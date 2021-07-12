import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { UserModel } from '../../models/user.model';
import { AuthService } from "../../services/auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css'],
})
export class SignupComponent implements OnInit {
  constructor(private authService: AuthService,
              private router: Router) {}

  err: string | null = null;

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


  submit() {
    if (this.user.valid && this.passwordCheck.value === this.user.value.password) {
      this.authService.register(this.user.getRawValue())
        .then(x => {
          this.router.navigate(["/"]);
        })
        .catch(err => {
          this.err = err.error;
        })
    } else {
      this.err = 'Please provide valid credentials!';
    }
  }
}
