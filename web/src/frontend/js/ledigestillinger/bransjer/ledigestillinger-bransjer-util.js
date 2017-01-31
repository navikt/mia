export function findTotaltAntallJobber(data) {
    return data.reduce(function(a, b) {return a + b.antallStillinger;}, 0);
}