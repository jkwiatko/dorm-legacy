import { Component, OnInit } from '@angular/core';
import {ToastrService} from "ngx-toastr";
import {timeout} from "rxjs/operators";

@Component({
  selector: 'app-welcome-screen',
  templateUrl: './welcome-screen.component.html',
  styleUrls: ['./welcome-screen.component.scss'],
})
export class WelcomeScreenComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

}
