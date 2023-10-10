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

function getChats(ticketId,token) {
    return new Promise((resolve, reject) => {
        fetch('API/chat/ticket/'+ticketId, {
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

function getChatMessages(chatId, token) {
    return new Promise((resolve, reject) => {
        fetch('API/chat/messages/'+chatId, {
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

function insertMessage(message, token) {
    return new Promise((resolve, reject) => {
        fetch('API/ticket/chat/message', {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(message),
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

function getAttachments(messageId,token) {
    return new Promise((resolve, reject) => {
        fetch('API/ticket/chat/message/attachments/'+messageId, {
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

function insertAttachment(attachment,token) {
    return new Promise((resolve, reject) => {
        fetch('API/attachment', {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(attachment),
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

function getTicketsByCustomer(customerId, token) {
    return new Promise((resolve, reject) => {
        fetch('API/ticket/customer/' + customerId, {
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

function insertChat(chat,token) {
    return new Promise((resolve, reject) => {
        fetch('API/chat', {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(chat),
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

const API = { products, productDetails, profileDetails, login, logout, insertProfile, editProfile, expert, expertTickets, ticketDetails, latestStatus,
    openTicket, closeTicket, progressTicket, resolveTicket, reopenTicket, getChats, getChatMessages, insertMessage, getAttachments, insertAttachment, getTicketsByCustomer,insertChat};
export default API;