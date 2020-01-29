import {SafeUrl} from "@angular/platform-browser";

export interface Picture {
    base64String: string | SafeUrl;
    name: string;
}
