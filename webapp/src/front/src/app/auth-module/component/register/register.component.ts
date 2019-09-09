import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AuthService} from '../../provider/auth.service';

@Component({
    selector: 'app-register',
    templateUrl: './register.component.html',
    styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
    registerForm: FormGroup;

    constructor(private fb: FormBuilder,
                private authService: AuthService) {
    }

    ngOnInit() {
        this.registerForm = this.fb.group({
            email: ['', Validators.required],
            password: ['', Validators.required],
        });
    }

    register() {
        this.authService.register(this.registerForm.value)
            .subscribe(
                success => console.log(success),
                error =>  console.log(error)
            );
    }
}
