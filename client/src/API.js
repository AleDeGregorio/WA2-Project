function products() {
    return new Promise((resolve, reject) => {
        fetch('/API/products/', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        }).then((response) => {
            if (response.ok) {
                resolve(response.json());
            } else {
                // analyze the cause of error
                response.json()
                    .then((message) => { reject(message); }) // error message in the response body
                    .catch(() => { reject({ error: "Unable to elaborate server response" }) }); // something else
            }
        }).catch(() => { reject({ error: "Server communication error" }) }); // connection errors
    });
}

function productDetails(product) {
    return new Promise((resolve, reject) => {
        fetch('/API/products/' + product, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        }).then((response) => {
            if (response.ok) {
                resolve(response.json());
            } else {
                // analyze the cause of error
                response.json()
                    .then((message) => { reject(message); }) // error message in the response body
                    .catch(() => { reject({ error: "Unable to elaborate server response" }) }); // something else
            }
        }).catch(() => { reject({ error: "Server communication error" }) }); // connection errors
    });
}

function profileDetails(email) {
    return new Promise((resolve, reject) => {
        fetch('/API/profiles/' + email, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        }).then((response) => {
            if (response.ok) {
                resolve(response.json());
            } else {
                // analyze the cause of error
                response.json()
                    .then((message) => { reject(message); }) // error message in the response body
                    .catch(() => { reject({ error: "Unable to elaborate server response" }) }); // something else
            }
        }).catch(() => { reject({ error: "Server communication error" }) }); // connection errors
    });
}

function insertProfile(profile) {
    return new Promise((resolve, reject) => {
        fetch('/API/profiles', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(profile),
        }).then((response) => {
            if (response.ok) {
                const ok = true;
                resolve(ok);
            } else {
                // analyze the cause of error
                response.json()
                    .then((message) => { reject(message); }) // error message in the response body
                    .catch(() => { reject({ error: "Unable to elaborate server response" }) }); // something else
            }
        }).catch(() => { reject({ error: "Server communication error" }) }); // connection errors
    });
}

function editProfile(profile) {
    return new Promise((resolve, reject) => {
        fetch('/API/profiles/' + profile.email, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(profile),
        }).then((response) => {
            if (response.ok) {
                const ok = true;
                resolve(ok);
            } else {
                // analyze the cause of error
                response.json()
                    .then((message) => { reject(message); }) // error message in the response body
                    .catch(() => { reject({ error: "Unable to elaborate server response" }) }); // something else
            }
        }).catch(() => { reject({ error: "Server communication error" }) }); // connection errors
    });
}

const API = { products, productDetails, profileDetails, insertProfile, editProfile };
export default API;