function products() {
    return new Promise((resolve, reject) => {
        fetch('/API/product', {
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
        fetch('/API/product/' + product, {
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

function getCustomer(email, token) {
    return new Promise((resolve, reject) => {
        fetch('/API/customer/' + email, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
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

function login(credentials) {
    return new Promise((resolve, reject) => {
        fetch('/API/auth/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(credentials)
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

function logout(token) {
    return new Promise((resolve, reject) => {
        fetch('/API/auth/logout', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(token)
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

function signupCustomer(customer) {
    return new Promise((resolve, reject) => {
        fetch('/API/customer/signup', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(customer),
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

function updateCustomer(profile, token) {
    return new Promise((resolve, reject) => {
        fetch('/API/customer/' + profile.email, {
            method: 'PUT',
            headers: {
                'Authorization': `Bearer ${token}`,
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

function insertTicket(ticket, token) {
    return new Promise((resolve, reject) => {
        fetch('/API/ticket', {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(ticket),
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

const API = { products, productDetails, profileDetails: getCustomer, login, logout, insertProfile: signupCustomer, editProfile: updateCustomer, insertTicket };
export default API;