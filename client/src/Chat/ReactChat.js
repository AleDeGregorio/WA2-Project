import React from "react";
import SockJsClient from "react-stomp";
import UsernameGenerator from "username-generator";
import { TalkBox } from "react-talk";
import Fetch from "json-fetch";
import randomstring from "randomstring"; // Importing randomstring properly

class ReactChat extends React.Component {
    constructor(props) {
        super(props);
        this.randomUserName = UsernameGenerator.generateUsername("-");
        this.randomUserId = randomstring.generate();
        this.state = {
            clientConnected: false,
            messages: [],
        };
    }

    onMessageReceive = (msg, topic) => {
        this.setState((prevState) => ({
            messages: [...prevState.messages, msg],
        }));
    };

    sendMessage = (msg, selfMsg) => {
        try {
            this.clientRef.sendMessage("/app/", JSON.stringify(selfMsg));
            return true;
        } catch (e) {
            return false;
        }
    };

    componentDidMount() { // Use componentDidMount instead of componentWillMount
        Fetch("/API/product", {
            method: "GET",
        }).then((response) => {
            this.setState({ messages: response.body });
        });
    }


    render() {
        const wsSourceUrl = "http://localhost:8080/ws"
        return (
            <div>
                <TalkBox
                    topic="/topic"
                    currentUserId={this.randomUserId}
                    currentUser={this.randomUserName}
                    messages={this.state.messages}
                    onSendMessage={this.sendMessage}
                    connected={this.state.clientConnected}
                />

                <SockJsClient
                    url={wsSourceUrl}
                    topics={["/topic/"]}
                    onMessage={this.onMessageReceive}
                    ref={(client) => {
                        this.clientRef = client;
                    }}
                    onConnect={() => {
                        this.setState({ clientConnected: true });
                    }}
                    onDisconnect={() => {
                        this.setState({ clientConnected: false });
                    }}
                    debug={false}
                />
            </div>
        );
    }
}

export default ReactChat;
