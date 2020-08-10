import {PictureModel} from './picture.model';

export class ProfilePreviewModel {
    id: number;
    firstName: string;
    lastName: string;
    picture: PictureModel;
    birthDate: Date;
    gender: string;
    studyingAt: string;
    workingIn: string;
}
