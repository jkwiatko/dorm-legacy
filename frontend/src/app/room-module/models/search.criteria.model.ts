import {SearchType} from '../../shared-module/models/searchType.model';

export class SearchCriteriaModel {
    constructor(
        public cityName,
        public roomName,
        public startingDate,
        public duration,
        public maxPrice,
        public searchType: SearchType) {
    }
}
