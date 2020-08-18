export function navigationAlertFactory(navigationMethod: () => any) {
    return {
        header: 'Zmiany nie zostały zapisane!',
        message: 'Czy na pewno chcesz przejść dalej?',
        buttons: [
            {text: 'Tak', handler: () => { navigationMethod(); }},
            'Nie'
        ]
    }
}
