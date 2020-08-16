export class ProfileSearchCriteriaModel {
    constructor(
        public roomId?: number,
        public gender?: string,
        public minAge?: number,
        public maxAge?: number,
        public workAndUniversity?: string,
        public inclinations?: string[]
    ) {
    }
}
