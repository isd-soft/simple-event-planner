import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { AuthService } from "../../services/auth.service";
import { Router } from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  err: string | null = null;
  hide = true;

  constructor(private authService: AuthService,
              private router: Router) {
    if (authService.isAuthenticated()) {
      this.redirect();
    }
  }

  ngOnInit(): void {
  }

  user = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('',[Validators.required]),
  });


  redirect(): void {
    this.router.navigate(["/"]);
  }

  submit() {
    if (this.user.valid) {
      this.authService.login(this.user.getRawValue())
        .then(() => {
          this.redirect();
        })
        .catch(err => {
          // console.log(err);
          // console.log(err.error);
          this.err = err.error;
        })
    }
  }

}
