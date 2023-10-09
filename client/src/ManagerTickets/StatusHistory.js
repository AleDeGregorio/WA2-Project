import {Link, useNavigate, useParams} from "react-router-dom";
import {Button, Container, Dropdown, Form, Table} from "react-bootstrap";
import {useContext, useEffect, useState} from "react";
import LoginContext from "../Profiles/LoginContext";
import API from "../API";
import dayjs from "dayjs"



function StatusHistory(props){
    const {id} = useParams();
    const user = useContext(LoginContext)
    const [status, setStatus] = useState([])
    const [show, setShow] = useState(false);
    const [error, setError] = useState(false);

    useEffect(() => {
        API.status(id, user.access_token)
            .then(t => {
                    setStatus(t)

                    //console.log(t)
                }
            )
            .catch(error => {
                setError(error);
                setShow(true)
            });
    }, [id, status])

    function getStatusLabel(status) {
        switch (status) {
            case "OPEN":
                return "OPEN";
            case "RESOLVED":
                return "RESOLVED";
            case "CLOSED":
                return "CLOSED";
            case "REOPENED":
                return "REOPENED";
            case "IN_PROGRESS":
                return "IN PROGRESS";

        }
    }
    return (
        status  && <ontainer fluid style = {{display: "flex", flexDirection: "column", alignItems: "center", fontFamily : "system-ui", padding: "5%" }}>
            <h1 style={{fontWeight: "700"}}>STATUS HISTORY</h1>
            <Table bordered hover style = {{width: "60%"}}>
                <thead>
                <tr style={{backgroundColor: "#057F5F", color: 'white'}}>
                    <th>Ticket Id</th>
                    <th>Last Modified Date</th>
                    <th>Status</th>
                </tr>
                </thead>
                <tbody>
                {status.map((s, index) => (
                    <tr key={index + 1}>
                        <td>{s.tid.id}</td>
                        <td>{dayjs(s.lastModifiedDate).format("YYYY-MM-DD HH:mm:ss")}</td>
                        <td>{getStatusLabel(s.status)}</td>
                    </tr>
                ))}
                </tbody>
            </Table>

        </ontainer>
    );
}

export default StatusHistory;