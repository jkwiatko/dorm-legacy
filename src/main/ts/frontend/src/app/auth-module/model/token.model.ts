export class TokenModel {
    token: string;
    expirationDate: string;

    hasNotExpired() : boolean {
        return true;
    }

    expirationDuration() : number {
       return new Date(this.expirationDate).getTime() - new Date().getTime();
    }

    merge(merge: Partial<TokenModel>) : TokenModel {
        return Object.assign(this, merge);
    }
}
