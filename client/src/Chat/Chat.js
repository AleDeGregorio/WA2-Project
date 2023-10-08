import React, {useEffect, useState} from 'react';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import {faUser} from '@fortawesome/free-solid-svg-icons';
import {Badge, Button, Container, Form, ListGroup} from 'react-bootstrap';
import API from "../API";
import './Chat.css'

const ticketData = {
    ticket: {
        id: 1,
        customer: {
            id: 1,
            username: "customer1",
            firstName: "Alice",
            lastName: "Johnson",
            email: "alice@example.com",
        },
        expert: {
            id: 1,
            username: "expert1",
            firstName: "John",
            lastName: "Doe",
            email: "john@example.com",
        },
        product: {
            id: 1,
            name: "Product A",
            description: "Description for Product A",
        },
        status: [
            {
                id: 1,
                status: "Open",
                timestamp: "2023-09-29T10:30:00",
            },
            {
                id: 2,
                status: "In Progress",
                timestamp: "2023-09-30T14:45:00",
            },
        ],
        chats: [
            {
                id: 1,
                messages: [
                    {
                        id: 1,
                        sender: {
                            id: 1,
                            username: "customer1",
                            firstName: "Alice",
                            lastName: "Johnson",
                            email: "alice@example.com",
                        },
                        content: "Hello, I have an issue with Product A.",
                        timestamp: "2023-09-29T11:15:00",
                    },
                    {
                        id: 2,
                        sender: {
                            id: 1,
                            username: "expert1",
                            firstName: "John",
                            lastName: "Doe",
                            email: "john@example.com",
                        },
                        content: "Sure, I can help you with that.",
                        timestamp: "2023-09-29T11:30:00",
                    },
                ],
                creationDate: "2023-09-29T10:45:00",
            },
            {
                id: 2,
                messages: [
                    {
                        id: 3,
                        sender: {
                            id: 1,
                            username: "customer1",
                            firstName: "Alice",
                            lastName: "Johnson",
                            email: "alice@example.com",
                        },
                        content: "Thank you for your help!",
                        timestamp: "2023-09-30T15:00:00",
                    },
                    {
                        id: 4,
                        sender: {
                            id: 1,
                            username: "expert1",
                            firstName: "John",
                            lastName: "Doe",
                            email: "john@example.com",
                        },
                        content: "You're welcome! Let me know if you have more questions.",
                        timestamp: "2023-09-30T15:15:00",
                    },
                ],
                creationDate: "2023-09-30T14:50:00",
            },
        ],
        issueType: "Technical Issue",
        description: "Description of the technical issue.",
        priorityLevel: 2,
        dateOfCreation: "2023-09-29T10:00:00",
    },
};
 const chatExample=[
     {
         "id": 1,
         "messages": [],
         "ticket": {
             "id": 20,
             "customer": {
                 "id": "056d235a-8eaa-41fc-8d2a-adaedcf317c7",
                 "username": "mariorossi",
                 "firstName": "Mario",
                 "lastName": "Rossi",
                 "password": null,
                 "email": "mariorossi@polito.it",
                 "city": "Torino",
                 "address": "Via Roma, 22"
             },
             "expert": {
                 "id": "a21c80cd-9c6c-4eed-b992-cd926a72ad4b",
                 "username": "expert1",
                 "firstName": "Expert",
                 "lastName": "Uno",
                 "password": null,
                 "email": "exp@polito.it",
                 "fields": "Smartphone"
             },
             "product": {
                 "ean": 1,
                 "name": "iPhone 11",
                 "brand": "Apple",
                 "category": "Smartphone",
                 "price": 800.0
             },
             "issueType": "Problem",
             "description": "Problem with smartphone",
             "priorityLevel": 3,
             "dateOfCreation": "2023-05-05T10:00:00.000+00:00"
         },
         "creationDate": "2023-05-06T09:00:00.000+00:00"
     }
 ]

const messagesExample=[
    {
        "id": 1,
        "chat": {
            "id": 1,
            "messages": [],
            "ticket": {
                "id": 7,
                "customer": {
                    "id": "056d235a-8eaa-41fc-8d2a-adaedcf317c7",
                    "username": "mariorossi",
                    "firstName": "Mario",
                    "lastName": "Rossi",
                    "password": null,
                    "email": "mariorossi@polito.it",
                    "city": "Torino",
                    "address": "Via Roma, 22"
                },
                "expert": {
                    "id": "a21c80cd-9c6c-4eed-b992-cd926a72ad4b",
                    "username": "expert1",
                    "firstName": "Expert",
                    "lastName": "Uno",
                    "password": null,
                    "email": "exp@polito.it",
                    "fields": "Smartphone"
                },
                "product": {
                    "ean": 1,
                    "name": "iPhone 11",
                    "brand": "Apple",
                    "category": "Smartphone",
                    "price": 800.0
                },
                "issueType": "Problem",
                "description": "Problem with smartphone",
                "priorityLevel": 3,
                "dateOfCreation": "2023-05-05T10:00:00.000+00:00"
            },
            "creationDate": "2023-05-06T09:00:00.000+00:00"
        },
        "attachments": [
            {
                "id": 1,
                "message": {
                    "id": 1,
                    "chat": {
                        "id": 1,
                        "messages": [],
                        "ticket": {
                            "id": 7,
                            "customer": {
                                "id": "056d235a-8eaa-41fc-8d2a-adaedcf317c7",
                                "username": "mariorossi",
                                "firstName": "Mario",
                                "lastName": "Rossi",
                                "password": null,
                                "email": "mariorossi@polito.it",
                                "city": "Torino",
                                "address": "Via Roma, 22"
                            },
                            "expert": {
                                "id": "a21c80cd-9c6c-4eed-b992-cd926a72ad4b",
                                "username": "expert1",
                                "firstName": "Expert",
                                "lastName": "Uno",
                                "password": null,
                                "email": "exp@polito.it",
                                "fields": "Smartphone"
                            },
                            "product": {
                                "ean": 1,
                                "name": "iPhone 11",
                                "brand": "Apple",
                                "category": "Smartphone",
                                "price": 800.0
                            },
                            "issueType": "Problem",
                            "description": "Problem with smartphone",
                            "priorityLevel": 3,
                            "dateOfCreation": "2023-05-05T10:00:00.000+00:00"
                        },
                        "creationDate": "2023-05-06T09:00:00.000+00:00"
                    },
                    "attachments": [],
                    "sentBy": "CUSTOMER",
                    "content": "Hello, I have a problem with my smartphone",
                    "sendingDate": "2023-05-06T09:30:00.000+00:00"
                },
                "name": "googleIcon",
                "type": "png",
                "imageData": "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"
            },
        ],
        "sentBy": "CUSTOMER",
        "content": "Hello, I have a problem with my smartphone",
        "sendingDate": "2023-05-06T09:30:00.000+00:00"
    },
    {
        "id": 2,
        "chat": {
            "id": 1,
            "messages": [],
            "ticket": {
                "id": 7,
                "customer": {
                    "id": "056d235a-8eaa-41fc-8d2a-adaedcf317c7",
                    "username": "mariorossi",
                    "firstName": "Mario",
                    "lastName": "Rossi",
                    "password": null,
                    "email": "mariorossi@polito.it",
                    "city": "Torino",
                    "address": "Via Roma, 22"
                },
                "expert": {
                    "id": "a21c80cd-9c6c-4eed-b992-cd926a72ad4b",
                    "username": "expert1",
                    "firstName": "Expert",
                    "lastName": "Uno",
                    "password": null,
                    "email": "exp@polito.it",
                    "fields": "Smartphone"
                },
                "product": {
                    "ean": 1,
                    "name": "iPhone 11",
                    "brand": "Apple",
                    "category": "Smartphone",
                    "price": 800.0
                },
                "issueType": "Problem",
                "description": "Problem with smartphone",
                "priorityLevel": 3,
                "dateOfCreation": "2023-05-05T10:00:00.000+00:00"
            },
            "creationDate": "2023-05-06T09:00:00.000+00:00"
        },
        "attachments": [],
        "sentBy": "EXPERT",
        "content": "Hello, try to turn it of and on again",
        "sendingDate": "2023-05-06T10:30:00.000+00:00"
    },
    {
        "id": 3,
        "chat": {
            "id": 1,
            "messages": [],
            "ticket": {
                "id": 7,
                "customer": {
                    "id": "056d235a-8eaa-41fc-8d2a-adaedcf317c7",
                    "username": "mariorossi",
                    "firstName": "Mario",
                    "lastName": "Rossi",
                    "password": null,
                    "email": "mariorossi@polito.it",
                    "city": "Torino",
                    "address": "Via Roma, 22"
                },
                "expert": {
                    "id": "a21c80cd-9c6c-4eed-b992-cd926a72ad4b",
                    "username": "expert1",
                    "firstName": "Expert",
                    "lastName": "Uno",
                    "password": null,
                    "email": "exp@polito.it",
                    "fields": "Smartphone"
                },
                "product": {
                    "ean": 1,
                    "name": "iPhone 11",
                    "brand": "Apple",
                    "category": "Smartphone",
                    "price": 800.0
                },
                "issueType": "Problem",
                "description": "Problem with smartphone",
                "priorityLevel": 3,
                "dateOfCreation": "2023-05-05T10:00:00.000+00:00"
            },
            "creationDate": "2023-05-06T09:00:00.000+00:00"
        },
        "attachments": [],
        "sentBy": "CUSTOMER",
        "content": "Thank you, it worked",
        "sendingDate": "2023-05-06T13:30:00.000+00:00"
    }
]




function Chat() {
    const [chats, setChats] = useState(chatExample);

    const [selectedChat, setSelectedChat] = useState(null);

    useEffect(() => {
        // Esegui la richiesta API iniziale quando il componente viene montato
        // fetch('/API/ticket/chat')
        //     .then(response => response.json())
        //     .then(data => setChats(data))
        //     .catch(error => console.error('Errore nella richiesta API:', error));
        API.getChats(7)
            .then(data => setChats(data))
            .catch(error => console.error('Errore nella richiesta API:', error));
        //setChats(ticketData.ticket.chats)
    }, []);


    const openChat = (chat) => {
        // Imposta l'ID della chat selezionata quando si fa clic su "Apri Chat"
        setSelectedChat(chat);
    };

    return (
        <Container>
            <h1>Chat for ticket {chats[0].ticket.id}</h1>
            <ListGroup>
                {chats.map(chat => (
                    <ListGroup.Item key={chat.id}>
                        <div>
                            <strong>ID Chat:</strong> {chat.id}
                        </div>
                        <div>
                            <strong>Data Chat:</strong> {chat.date}
                        </div>
                        <Button variant="primary" onClick={() => openChat(chat)}>
                            Apri Chat
                        </Button>
                    </ListGroup.Item>
                ))}
            </ListGroup>
            {selectedChat && <ChatMessages chat={selectedChat} />}
        </Container>
    );
}

function ChatMessages({ chat }) {
    const [messages, setMessages] = useState(messagesExample);
    const [newMessage, setNewMessage] = useState('');

    const sendMessage = (chat) => {
        if (newMessage.trim() === '') {
            return; // Don't send empty messages
        }

        // Simulate sending a message to the API
        // Replace this with your actual API call
        /*const simulatedMessage = {
            id: messages.length + 1,
            sender: {
                username: 'customer1', // Replace with the actual sender's username
            },
            content: newMessage,
            timestamp: new Date().toISOString(),
        };*/

        let idlast=messages[messages.length-1].id
        let sendMessage={
            id: idlast+1,
            chat : chat,
            attachments: [],
            sentBy: 'CUSTOMER', //todo da cambiare quando entri con il ruolo
            content: newMessage,
            sendingDate: new Date().toISOString()
        }
        //salvo con id per differenziarli nella pagina
        setMessages([...messages, sendMessage]);
        setNewMessage('');
        //rimetto null per evitare problemi nell'inserimento, copio poichè altrimenti mi cambia anche in array
        var copySendMessage={...sendMessage}
        copySendMessage.id=null;
        API.insertMessage(copySendMessage)
            .catch(error => console.error('Errore nella richiesta API:', error, copySendMessage));
    };
    const fetchMessages = async () => {
        // Esegui la richiesta API per caricare i messaggi
        /*API.getChatMessages(chat.id)
            .then(data => setMessages(data))
            .catch(error => console.error('Errore nella richiesta API:', error));
        for (let message of messages) {
            API.getAttachments(message.id)
                .then(data => message.attachments=data)
                .catch(error => console.error('Errore nella richiesta API:', error));
            console.log(message);
        }*/
        try {
            // Fetch messages for the chat
            const messagesData = await API.getChatMessages(chat.id);

            // Create an array of promises to fetch attachments for each message
            const attachmentPromises = messagesData.map(async (message) => {
                try {
                    message.attachments = await API.getAttachments(message.id);
                } catch (error) {
                    console.error('Error fetching attachments:', error);
                }
            });

            // Wait for all attachment fetch promises to resolve
            await Promise.all(attachmentPromises);

            // Set the messages state with attachments
            setMessages(messagesData);
        } catch (error) {
            console.error('Error fetching messages:', error);
        }
    };

    // Esegui la prima richiesta API al mount del componente
    useEffect(() => {
        fetchMessages();
    }, [chat.id]);

    // Esegui il polling ogni tot millisecondi per aggiornare i messaggi
    useEffect(() => {
        const pollingInterval = setInterval(() => {
            fetchMessages();
        }, 500000); // Esempio: effettua il polling ogni 5 secondi

        return () => {
            // Pulisci l'interval quando il componente viene smontato
            clearInterval(pollingInterval);
        };
    }, [chat.id]);

    return (
        <div className="chat-messages">
            <h2>Chat Messages for Chat ID: {chat.id}</h2>
            <ul>
                {messages.map(message => (
                    <li key={message.id}>
                        <Badge variant="primary">
                            <FontAwesomeIcon icon={faUser} />{' '}
                            <span>{message.sentBy}</span>
                        </Badge>
                        <span>: {message.content}</span>
                        {message.attachments.length > 0 && (
                            <ul>
                                {message.attachments.map((att, index) => (
                                    <li key={index}>
                                        <img src={`data:image/${att.type};base64,${att.imageData}`}
                                             alt="Attached Image"
                                        className="chat-image"/>
                                    </li>
                                ))}
                            </ul>
                        )}
                        <br />
                        <small className="text-muted">
                            <em>{message.sendingDate}</em>
                        </small>
                    </li>
                ))}
            </ul>
            <div>
                <Form>
                    <Form.Group>
                        <Form.Control
                            type="text"
                            placeholder="Type your message..."
                            value={newMessage}
                            onChange={e => setNewMessage(e.target.value)}
                        />
                    </Form.Group>
                    <Button variant="primary" onClick={()=>sendMessage(chat)}>
                        Send
                    </Button>
                </Form>
            </div>
        </div>
    );
}

export default Chat;