export const bransjemock = [
    {
        id: 0,
        navn: "Bygg og anlegg",
        antall: 7
    },
    {
        id: 1,
        navn: "Computer & Tech-wizardry",
        antall: 12
    },
    {
        id: 2,
        navn: "Management Consulting",
        antall: 10
    },
    {
        id: 3,
        navn: "Kybernetikk",
        antall: 4
    },
    {
        id: 4,
        navn: "Nanoteknologi",
        antall: 9
    },
    {
        id: 5,
        navn: "Hotell og reiseliv",
        antall: 14
    },
    {
        id: 6,
        navn: "Bartender",
        antall: 2
    },
    {
        id: 7,
        navn: "Butikkmedarbeider",
        antall: 1
    },
    {
        id: 8,
        navn: "PR-rådgivning",
        antall: 13
    }
];

bransjemock.sort(function(a, b) {
    if(a.antall > b.antall) {
        return -1;
    }
    if(a.antall < b.antall) {
        return 1;
    }
    return 0;
});

export default bransjemock;
