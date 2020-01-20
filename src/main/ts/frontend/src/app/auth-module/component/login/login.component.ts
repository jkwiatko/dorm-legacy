import {Component, OnInit} from '@angular/core';
import {MatDialogRef} from '@angular/material';
import {AuthService} from '../../provider/auth.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';

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
        this.authService.login(this.loginForm.value).subscribe(tokenDto => {
            this.authService.addAccessToken(tokenDto.token);
            this.dialogRef.close('success');
        });
    }

    closeDialog() {
        this.dialogRef.close();
    }
}
