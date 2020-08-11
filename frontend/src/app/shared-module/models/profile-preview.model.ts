import {PictureModel} from './picture.model';
import {ProfileModel} from '../../profile-module/models/profile.model';

export class ProfilePreviewModel {
    constructor(
        public id?: number,
        public firstName?: string,
        public lastName?: string,
        public picture?: PictureModel,
        public birthDate?: Date,
        public gender?: string,
        public studyingAt?: string,
        public workingIn?: string
    ) {
    }

    public static buildFromProfileModel(profile: ProfileModel): ProfilePreviewModel {
        return new ProfilePreviewModel(
            profile.id,
            profile.firstName,
            profile.lastName,
            profile.profilePictures[0],
            profile.birthDate,
            profile.gender,
            profile.studyingAt,
            profile.workingIn
        )
    }
}
