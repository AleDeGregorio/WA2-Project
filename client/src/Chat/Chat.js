import React, { useEffect, useState } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faUser } from '@fortawesome/free-solid-svg-icons';
import { Container, ListGroup, Badge, Form, Button } from 'react-bootstrap';
import API from "../API"; // Import Form

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
function Chat() {
    const [chats, setChats] = useState(chatExample);

    const [selectedChatId, setSelectedChatId] = useState(null);

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


    const openChat = (chatId) => {
        // Imposta l'ID della chat selezionata quando si fa clic su "Apri Chat"
        setSelectedChatId(chatId);
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
                        <Button variant="primary" onClick={() => openChat(chat.id)}>
                            Apri Chat
                        </Button>
                    </ListGroup.Item>
                ))}
            </ListGroup>
            {selectedChatId && <ChatMessages chatId={selectedChatId} />}
        </Container>
    );
}

function ChatMessages({ chatId }) {
    const [messages, setMessages] = useState([]);
    const [newMessage, setNewMessage] = useState('');

    const sendMessage = () => {
        if (newMessage.trim() === '') {
            return; // Don't send empty messages
        }

        // Simulate sending a message to the API
        // Replace this with your actual API call
        const simulatedMessage = {
            id: messages.length + 1,
            sender: {
                username: 'customer1', // Replace with the actual sender's username
            },
            content: newMessage,
            timestamp: new Date().toISOString(),
        };

        setMessages([...messages, simulatedMessage]);
        setNewMessage('');
    };
    const fetchMessages = () => {
        // Esegui la richiesta API per caricare i messaggi
        // fetch(`/API/ticket/chat/${chatId}/messages`)
        //     .then(response => response.json())
        //     .then(data => setMessages(data))
        //     .catch(error => console.error('Errore nella richiesta API:', error));
       /* API.getChatMessages(chatId)
            .then(data => setMessages(data))
            .catch(error => console.error('Errore nella richiesta API:', error));*/
        setMessages(ticketData.ticket.chats.find(chat => chat.id === chatId).messages)
    };

    // Esegui la prima richiesta API al mount del componente
    useEffect(() => {
        fetchMessages();
    }, [chatId]);

    // Esegui il polling ogni tot millisecondi per aggiornare i messaggi
    useEffect(() => {
        const pollingInterval = setInterval(() => {
            fetchMessages();
        }, 5000); // Esempio: effettua il polling ogni 5 secondi

        return () => {
            // Pulisci l'interval quando il componente viene smontato
            clearInterval(pollingInterval);
        };
    }, [chatId]);

    return (
        <div className="chat-messages">
            <h2>Chat Messages for Chat ID: {chatId}</h2>
            <ul>
                {messages.map(message => (
                    <li key={message.id}>
                        <Badge variant="primary">
                            <FontAwesomeIcon icon={faUser} />{' '}
                            <span>{message.sender.username}</span>
                        </Badge>
                        <span>: {message.content}</span>
                        <br />
                        <small className="text-muted">
                            <em>{message.timestamp}</em>
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
                    <Button variant="primary" onClick={sendMessage}>
                        Send
                    </Button>
                </Form>
            </div>
        </div>
    );
}

export default Chat;