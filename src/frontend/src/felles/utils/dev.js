export const erDev = () => erDevUrl(window.location.href);

export function erDevUrl(url) {
    return url.includes('debug=true') || url.includes('devillo.no:8486') || url.includes('localhost:');
}
