import {Component, Inject, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AuthService} from '../../provider/auth.service';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {switchMap} from 'rxjs/operators';
import {Subscription} from 'rxjs';

@Component({
    selector: 'app-register',
    templateUrl: './register.component.html',
    styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit, OnDestroy {
    private registerSubscription: Subscription;
    registerForm: FormGroup;

    constructor(private dialogRef: MatDialogRef<RegisterComponent>,
                @Inject(MAT_DIALOG_DATA) public data: any,
                private fb: FormBuilder,
                private authService: AuthService) {
    }

    ngOnInit() {
        this.registerForm = this.fb.group({
            email: ['', Validators.required],
            password: ['', Validators.required],
        });
    }

    register() {
        this.registerSubscription = this.authService.register(this.registerForm.value)
            .pipe(
                switchMap(() => this.authService.login(this.registerForm.value))
            ).subscribe(tokenDto => {
                this.authService.addAccessToken(tokenDto.token);
                this.dialogRef.close('success');
            });
    }

    ngOnDestroy(): void {
        if (this.registerSubscription) {
            this.registerSubscription.unsubscribe();
        }
    }

    closeDialog() {
        this.dialogRef.close();
    }
}
