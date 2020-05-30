export class TokenModel {
    token: string;
    expirationDate: string;

    hasNotExpired(): boolean {
        return true;
    }

    expirationDuration(): number {
        return this.parseDate(this.expirationDate).getTime() - new Date().getTime();
    }

    merge(merge: Partial<TokenModel>): TokenModel {
        return Object.assign(this, merge);
    }

    public parseDate(dateString: string): Date {
        const dateArray = dateString.split(/[^0-9]/).map(s => +s);
        return new Date(dateArray[0], dateArray[1] - 1, dateArray[2], dateArray[3], dateArray[4], dateArray[5]);
    }
}
