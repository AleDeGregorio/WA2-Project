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

function expert(email, token) {
    return new Promise((resolve, reject) => {
        fetch('/API/expert' + email, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json',
            },
        }).then((response) => {
            if (response.ok) {
                const ok = true;
                resolve(ok);
            } else {
                // analyze the cause of error
                response.json()
                    .then((message) => {
                        reject(message);
                    }) // error message in the response body
                    .catch(() => {
                        reject({error: "Unable to elaborate server response"})
                    }); // something else
            }
        }).catch(() => {
            reject({error: "Server communication error"})
        }); // connection errors
    });
}
function expertTickets(expertId, token) {
    return new Promise((resolve, reject) => {
        fetch('/API/ticket/expert/' + expertId, {
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

function tickets(token) {
    return new Promise((resolve, reject) => {
        fetch('/API/ticket' , {
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
function ticketDetails(ticketId, token) {
    return new Promise((resolve, reject) => {
        fetch('/API/ticket/' + ticketId, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
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
function latestStatus(ticketId, token) {
    return new Promise((resolve, reject) => {
        fetch('/API/ticket/status/' + ticketId + '/latest', {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
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
function openTicket(status, token) {
    return new Promise((resolve, reject) => {
        fetch('/API/ticket/status/open', {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(status)
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
function progressTicket(status, token) {
    return new Promise((resolve, reject) => {
        fetch('/API/ticket/status/progress', {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(status)
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
function closeTicket(status, token) {

    return new Promise((resolve, reject) => {

        fetch('/API/ticket/status/close', {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(status)
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
function reopenTicket(status, token) {
    return new Promise((resolve, reject) => {
        fetch('/API/ticket/status/reopen', {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(status)
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
function resolveTicket(status, token) {
    return new Promise((resolve, reject) => {
        fetch('/API/ticket/status/resolve', {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(status)
        }).then((response) => {
            if (response.ok) {
                console.log(response.body)
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
function status(ticketId, token) {
    return new Promise((resolve, reject) => {
        fetch('/API/ticket/status/' + ticketId, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
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

function getCustomerTickets(id, token) {
    return new Promise((resolve, reject) => {
        fetch('/API/customer/' + id + '/tickets', {
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

function getLatestTicketStatus(id, token) {
    return new Promise((resolve, reject) => {
        fetch('/API/ticket/status/' + id + '/latest', {
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

function insertExpert(expert, token) {
    return new Promise((resolve, reject) => {
        fetch('/API/expert', {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(expert)
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

function setPriority(ticketID, payload, token) {
    return new Promise((resolve, reject) => {
        fetch('/API/ticket/'+ticketID+"/priority", {
            method: 'PUT',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: payload
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

function setExpertTicket(ticketID, payload, token) {
    return new Promise((resolve, reject) => {
        fetch('/API/ticket/'+ticketID+"/expert", {
            method: 'PUT',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(payload)
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

function getAllExperts(token) {
    return new Promise((resolve, reject) => {
        fetch('/API/expert', {
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

function getAllTickets(token) {
    return new Promise((resolve, reject) => {
        fetch('/API/ticket', {
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


const API = {
    products, productDetails,
    profileDetails: getCustomer, login, logout, insertProfile: signupCustomer, editProfile: updateCustomer,
    insertTicket, getCustomerTickets, getLatestTicketStatus,
    expert, expertTickets, tickets, ticketDetails, latestStatus,
    openTicket, closeTicket, progressTicket, resolveTicket, reopenTicket, status,
    insertExpert, setPriority, setExpertTicket, getAllExperts, getAllTickets};
export default API;