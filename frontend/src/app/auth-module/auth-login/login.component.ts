import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AuthService} from "../providers/auth.service";
import {TokenModel} from "../models/token.model";
import {MatDialogRef} from "@angular/material/dialog";


@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
    loginForm: FormGroup;

    constructor(private dialogRef: MatDialogRef<LoginComponent>,
                private fb: FormBuilder,
                private authService: AuthService) {
    }

    ngOnInit() {
        this.loginForm = this.fb.group({
            email: ['', Validators.required],
            password: ['', Validators.required],
        });
    }

    login() {
        this.authService.login(this.loginForm.value).subscribe(token => {
            this.authService.addAccessToken(new TokenModel().merge(token));
            this.dialogRef.close('success');
        });
    }

    closeDialog() {
        this.dialogRef.close();
    }
}
