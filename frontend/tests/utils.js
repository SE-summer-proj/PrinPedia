export function getParamValue(url) {
    return url.substr(url.indexOf('=') + 1);
}
