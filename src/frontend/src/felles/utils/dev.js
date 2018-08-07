export const erDev = () => erDevUrl(window.location.href);

export function erDevUrl(url) {
  let lokalIpRegex = /^http:\/\/((10\.[0-255])|(172\.)|(192\.168\.))/;
  return url.includes('debug=true') || url.includes('devillo.no:8486') || url.includes('localhost:') || lokalIpRegex.test(url);
}
