import { Component, OnInit } from '@angular/core';
import {AuthService} from "../../services/auth.service";

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.css']
})
export class NavigationComponent implements OnInit {

  authenticated: boolean = false;
  isAdmin: boolean = false;

  constructor(private authService: AuthService) {
    this.authenticated = authService.isAuthenticated();
    this.isAdmin = authService.isAdmin();
  }

  logout() {
    this.authService.logout();
  }

  ngOnInit(): void {
    this.authService.subscribe((x: boolean) => {
      this.authenticated = x;
    });
  }

}
