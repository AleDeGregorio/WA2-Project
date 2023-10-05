import {Client, Message} from "@stomp/stompjs";
import { WebSocket } from 'ws';
import * as StompJs from "@stomp/stompjs";
import {useContext, useEffect, useState} from "react";
import LoginContext from "../Profiles/LoginContext";
import {Button} from "react-bootstrap";


function ChatStomp() {

    const [userData, setUserData] = useState({
        username: '',
        receiver: '',
        messages: [],
        messageInput: '',
        connected: false,
    })
    const [stompClient, setStompClient] = useState(new StompJs.Client())
    const user = useContext(LoginContext);
    const chatId = 1;
    const [tab, setTab] = useState("")
    const [privateChats, setPrivateChats] = useState(new Map());
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
    useEffect(() => {
        setUserData({...userData, "username": user.name});
    }, [])
    const userJoin = () => {
        let chatMessage = {
            sender: "userData.username",
            status: 'JOIN'
        };
        stompClient.publish({destination: "/app/private-msg",
                                    body: JSON.stringify(chatMessage)})
    };
    const onConnected = (frame) => {
        console.log('WebSocket Connected to '+ frame);
        setUserData({...userData, "connected": true});
        stompClient.subscribe(`/user/${chatId}/private`, onMessageReceived);
        userJoin();
    }
    const onError = (error) => {
        console.log(error);
    };
    const connect = () => {
        setStompClient((client) => {
            //client.beforeConnect=getOldMessages;
            client.brokerURL = "ws://127.0.0.1:8080/ws"
            client.onConnect = onConnected;
            client.onStompError = onError;
            console.log("starting connection")
            client.activate();
            return client;
        })
    }
    return(
        <Button onClick={connect}>Apri socket</Button>
    )
}

export default ChatStomp