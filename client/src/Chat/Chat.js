import {React, useContext, useEffect, useState} from "react";
import {Container, Row} from "react-bootstrap";
import {over} from 'stompjs';
import SockJS from 'sockjs-client';
import LoginContext from "../Profiles/LoginContext";

function Chat() {
    const user = useContext(LoginContext);
    const chatId = 1;
    let stompClient = null;

    const [userData, setUserData] = useState({
        username: '',
        receiver: '',
        messages: [],
        messageInput: '',
        connected: false,
    })

    const [tab, setTab] = useState("")
    const [privateChats, setPrivateChats] = useState(new Map());

    //one time
    useEffect(() => {
        setUserData({...userData, "username": user.name});
        handleConnect();
    }, [])
    const onError = (error) => {
        console.log(error);
    };
    const onMessageReceived = (payload) => {
        let payloadData = JSON.parse(payload.body);
        if (privateChats.get(payloadData.sender)) {
            privateChats.get(payloadData.sender).push(payloadData);
            setPrivateChats(new Map(privateChats))
        } else {
            let list = [];
            list.push(payloadData);
            privateChats.set(payloadData.sender, list);
            setPrivateChats(new Map(privateChats))
        }
    };
    const userJoin = () => {
        let chatMessage = {
            sender: userData.username,
            status: 'JOIN'
        };
        stompClient.send('/app/private-msg', {}, JSON.stringify(chatMessage))
    };
    const onConnected = () => {
        console.log('WebSocket Connected');
        setUserData({...userData, "connected": true});
        stompClient.subscribe(`/user/${chatId}/private`, onMessageReceived);
        userJoin();
    }
    const handleConnect = () => {
        let Sock = new SockJS("http://localhost:8080/ws");
        console.log("Websocket connecting...")
        const stompClient = over(Sock)
        stompClient.connect({}, onConnected, onError);
    }


    const sendMessage = () => {
        if (stompClient) {
            let chatMessage = {
                sender: userData.username,
                receiver: tab,
                message: userData.messageInput,
                status: 'MESSAGE'
            };
            if (userData.username !== tab) {
                privateChats.set(tab).push(chatMessage);
                setPrivateChats(new Map(privateChats))
            }
            stompClient.send('/app/private-msg', {}, JSON.stringify(chatMessage))
            setUserData({...userData, "messageInput": ""});
        }
    };

    const handleMessage = (event) => {
        const {value} = event.target;
        setUserData({...userData,"messageInput":value });
    };

    return(
        <Container>
            {userData.connected ?
                <>
                    <Row><h1>Chat ID #{chatId}</h1></Row>
                    <Container className={'chat-content'}>
                        {[...privateChats.get(tab)].map((chat, index) => (
                            <li className={'message'} key={index}>
                                {chat.sender !== userData.username && <div className={'avatar'}>{chat.sender}</div>}
                                <div className={'message-data'}>{chat.message}</div>
                                {chat.sender === userData.username && <div className={'avatar self'}>{chat.sender}</div>}
                            </li>
                        ))}

                        <div className={'send-message'}>
                            <input type={'text'} value={userData.messageInput} placeholder={'send message to chat'} name='message' onChange={handleMessage}/>
                            <button type={'button'} className={'send-button'} onClick={sendMessage}>SEND</button>
                        </div>
                    </Container>
                </>
            : <></>}
        </Container>
    )
}

export default Chat;