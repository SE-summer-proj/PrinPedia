import axios from 'axios';

/* Private */
function reportError(error) {
    console.log(error);
}

function httpError(status, statusText) {
    console.log(status, statusText);
}

/* Public */
export function POST(url, data, callback) {
    return axios
        .post(url, data)
        .then((res) => {
            if (res.status === 200) {
                callback(res.data);
            } else {
                httpError(res.status, res.statusText);
            }
        })
        .catch((error) => {
            reportError(error);
        });
}

export function GET(url, params, callback) {
    return axios
        .get(url, {params: params})
        .then((res) => {
            if (res.status === 200) {
                callback(res.data);
            } else {
                httpError(res.status, res.statusText);
            }
        })
        .catch((error) => {
            reportError(error);
        })
}
