import React, {useContext, useEffect, useState} from 'react';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import {faUser} from '@fortawesome/free-solid-svg-icons';
import {Badge, Button, Container, Form, ListGroup} from 'react-bootstrap';
import API from "../API";
import './Chat.css'
import LoginContext from "../Profiles/LoginContext";
import {useLocation, useNavigate, useParams} from "react-router-dom";


function ViewChat(props) {
    const navigate = useNavigate()
    const location = useLocation()

    const { ticket } = location.state

    const user = useContext(LoginContext)

    const { setError, setShow } = props;

    const [loading, setLoading] = useState(true)
    const [loadContext, setLoadContext] = useState(true)

    const [chat, setChat] = useState({});
    const [selectedChat, setSelectedChat] = useState(null);
    const [userTickets, setUserTickets] = useState([]);

    useEffect(() => {
        if(loadContext && user) {
            setLoadContext(false)
        }
        if(user.role === "customer") {
            API.getChats(ticket.id, user.access_token)
                .then(c => {
                    setChat(c[0])

                    setLoading(false)
                })
                .catch(error => {
                    setError(error)
                    setShow(true)

                    setTimeout(() => {
                        setShow(false)
                    }, 3000)
                })
        }
        else {
            navigate('/wrongPrivileges')
        }
    }, [loadContext])

    /*
    const fetchChats = async () => {
        // Esegui la richiesta API iniziale quando il componente viene montato
        let ticketsTemp=[]
        if (user.role === "customer") {
            await API.getTicketsByCustomer(user.id, user.access_token)
                .then(data=>{ticketsTemp=data})
        } else {
            //manager e admin?
            await API.expertTickets(user.id, user.access_token)
                .then(data=> {ticketsTemp=[...data]})
        }

        let partialChats=[]
        console.log(ticketsTemp)
        if (ticketsTemp.length > 0) {
            for (let i = 0; i < ticketsTemp.length; i++) {
                await API.getChats(ticketsTemp[i].id, user.access_token)
                    .then(data => {
                        partialChats=[...partialChats, ...data]
                    })
                    .catch(error => console.error('Errore nella richiesta API:', error))
            }
        }
        setChats(partialChats)
        setUserTickets(ticketsTemp)
    }

    useEffect( () => {
        if(chats.length==0){
            fetchChats()
        }
    }, []);

     */

    const openChat = (chat) => {
        // Imposta l'ID della chat selezionata quando si fa clic su "Apri Chat"
        setSelectedChat(chat);
    };

    const startChat = async(ticket) => {
        let chat={
            id: null,
            messages: [],
            ticket: ticket,
            creationDate: new Date()
        }
        let idGenerated=await API.insertChat(chat,user.access_token)
            .catch(error => console.error('Errore nella richiesta API:', error, chat));
        chat.id=idGenerated
        setChat(chat)
    };

    return (
        <Container>
            <h1>Chat for selected ticket</h1>
            {
                loading ?
                    <div>
                        <br />
                        <div className="spinner-border" role="status"></div>
                    </div> :
                    <>
                        <ListGroup>
                            <ListGroup.Item key={chat?.id}>
                                <div>
                                    <strong>ID Ticket:</strong> {ticket.id}
                                </div>
                                <div>
                                    <strong>Chat creation date:</strong> {chat ? new Date(chat.creationDate).toLocaleString() : "chat not created yet"}
                                </div>
                                {
                                    chat ?
                                        <Button variant="success" onClick={() => openChat(chat)}>
                                            Open chat
                                        </Button> :
                                        <Button variant="success" onClick={() => startChat(ticket)} disabled={user.role !== "customer"}>
                                            Start Chat
                                        </Button>
                                }
                            </ListGroup.Item>
                        </ListGroup>
                        {selectedChat && selectedChat.ticket.id== ticket.id && <ChatMessages chat={selectedChat} setSelectedChat={setSelectedChat} />}
                    </>
            }
        </Container>
    );
}

function ChatMessages({ chat , setSelectedChat}) {

    const user = useContext(LoginContext)

    const [loadingMessages, setLoadingMessages] = useState(true)
    const [messages, setMessages] = useState(null);
    const [newMessage, setNewMessage] = useState('');

    const [selectedFile, setSelectedFile] = useState(null);

    const sendMessage = async(chat, event) => {
        event.preventDefault()

        let input=document.getElementById('fileInput')
        if (newMessage.trim() === '' && input.files.length==0) return; // Don't send empty messages

        const fileInput = document.getElementById('fileInput');
        if (fileInput) fileInput.files = new DataTransfer().files;

        let attach=[]
        let copyAttachment=null
        if(selectedFile!=null){
            for (let i = 0; i < selectedFile.length; i++) {
                attach.push({
                    id: null,
                    message :null,
                    name: selectedFile[i].name,
                    type: selectedFile[i].type,
                    imageData: selectedFile[i].data
                })
            }
        }else{
            copyAttachment=[]
        }
        setSelectedFile(null)

        let sendMessage={
            id: null,
            chat : chat,
            attachments: [],
            sentBy: user.role.toUpperCase(),
            content: newMessage,
            sendingDate: new Date().toISOString()
        }

        //Invio messaggio e aspetto che abbia finito
        let idGenerated= await API.insertMessage(sendMessage,user.access_token)
            .catch(error => console.error('Errore nella richiesta API:', error, sendMessage));

        //invio attachment conl'id generato ritornato
        if(copyAttachment==null){
            for (let i = 0; i < attach.length; i++){
                attach[i].message=sendMessage
                attach[i].message.id=idGenerated
                await API.insertAttachment(attach[i],user.access_token)
                    .catch(error => console.error('Errore nella richiesta API:', error, attach));
            }
        }


        //creo copia altrimenti loop ricorsivo con attachment
        let copySendMessage={...sendMessage}
        copySendMessage.id=idGenerated;
        if(copyAttachment==null){
            copySendMessage.attachments=attach
        }
        setMessages([...messages, copySendMessage]);
        setNewMessage('');
    };
    const fetchMessages = async () => {
        // Esegui la richiesta API per caricare i messaggi
        try {
            // Fetch messages for the chat
            const messagesData = await API.getChatMessages(chat.id, user.access_token);

            // Create an array of promises to fetch attachments for each message
            const attachmentPromises = messagesData.map(async (message) => {
                try {
                    message.attachments = await API.getAttachments(message.id, user.access_token);
                } catch (error) {
                    console.error('Error fetching attachments:', error);
                }
            });

            // Wait for all attachment fetch promises to resolve
            await Promise.all(attachmentPromises);

            // Set the messages state with attachments
            setMessages(messagesData);

            setLoadingMessages(false)
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
        }, 50000); // Esempio: effettua il polling ogni 5 secondi

        return () => {
            // Pulisci l'interval quando il componente viene smontato
            clearInterval(pollingInterval);
        };
    }, [chat.id]);

    const handleFileSelection = (e) => {
        const files = e.target.files;

        if (files && files.length > 0) {
            const newSelectedFile = [];

            for (let i = 0; i < files.length; i++) {
                const file = files[i];

                const reader = new FileReader();

                reader.onload = (event) => {
                    const base64File = event.target.result;
                    const fileType = file.type;
                    const fileName = file.name;
                    const base64DataOnly = base64File.split(',')[1];

                    newSelectedFile.push({ data: base64DataOnly, type: fileType, name: fileName });
                    setSelectedFile(newSelectedFile);
                };

                reader.readAsDataURL(file);
            }
        }
    };

    const closeChat=() => {
        setSelectedChat(null);
    };



    return (
        <Container style = {{display : "flex", padding: "10px"}}>
            {
                loadingMessages ?
                    <div>
                        <br />
                        <div className="spinner-border" role="status"></div>
                    </div> :

                    <div className="chat-messages">
                        {messages!=null && (
                            <ul>
                                {messages.map(message => (
                                    <li key={message.id}>
                                        {
                                            message.sentBy === "CUSTOMER" ?

                                                <Badge bg="primary">
                                                    <FontAwesomeIcon icon={faUser} />{' '}
                                                    <span>{chat.ticket.customer.username}</span>
                                                </Badge> :

                                                <Badge bg="danger">
                                                    <FontAwesomeIcon icon={faUser} />{' '}
                                                    <span>{chat.ticket.expert.username}</span>
                                                </Badge>
                                        }
                                        <span>: {message.content}</span>
                                        {message.attachments.length > 0 && (
                                            <ul>
                                                {message.attachments.map((att, index) => (
                                                    <li key={index}>
                                                        {att.type.startsWith("image/") ? (
                                                            <a
                                                                href={"data:" + att.type + ";base64," + att.imageData}
                                                                download={att.name}
                                                            >
                                                                <img
                                                                    src={"data:" + att.type + ";base64," + att.imageData}
                                                                    alt={att.name}
                                                                    className="chat-image"
                                                                />
                                                            </a>
                                                        ) : (
                                                            <a
                                                                href={"data:" + att.type + ";base64," + att.imageData}
                                                                download={att.name}
                                                            >
                                                                {att.name}
                                                            </a>
                                                        )}
                                                    </li>
                                                ))}
                                            </ul>
                                        )}
                                        <br />
                                        <small className="text-muted">
                                            <em>{new Date(message.sendingDate).toLocaleString()}</em>
                                        </small>
                                    </li>
                                ))}
                            </ul>) }
                        <div>
                            <Form onSubmit={(event) => sendMessage(chat, event)}>
                                <Form.Group>
                                    <Form.Control
                                        type="text"
                                        placeholder="Type your message..."
                                        value={newMessage}
                                        onChange={e => setNewMessage(e.target.value)}
                                    />
                                    <input
                                        type="file"
                                        accept="*"
                                        onChange={handleFileSelection}
                                        id="fileInput"
                                        multiple
                                        style={{padding: "20px"}}
                                    />
                                </Form.Group>
                                <Container style = {{display : "flex", justifyContent: "space-evenly" }}>
                                    <Button variant="success" onClick={(event)=>sendMessage(chat, event)}>
                                        Send
                                    </Button>
                                    <Button variant="success" onClick={()=>closeChat()}>
                                        Close Chat
                                    </Button>
                                </Container>
                            </Form>
                        </div>
                    </div>
            }
        </Container>
    );
}

export default ViewChat;