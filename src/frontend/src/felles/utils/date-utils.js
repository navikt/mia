export const datoStigende = (a, b) => {
    if(a == null) {
        return 1;
    }
    if(b == null) {
        return -1;
    }
    return new Date(a) < new Date(b) ? -1 : 1;
};
