import {Container, Table, Button, Form, Dropdown, DropdownButton, DropdownItem} from "react-bootstrap";
import {useParams} from "react-router-dom";
import {RxUpdate} from "react-icons/rx"
import {useContext, useEffect, useState} from "react";
import API from "../API";
import dayjs from "dayjs"
import LoginContext from "../Profiles/LoginContext";

function TicketDetails(props) {
    const {id} = useParams();
    const user = useContext(LoginContext)
    const [ticket, setTicket] = useState([]);
    const [waiting, setWaiting] = useState(true);
    const [status, setStatus] = useState();
    // const { setError, setShow } = props

    useEffect(() => {
        API.ticketDetails(id,  user.access_token).then((resultTicket) => {
            console.log(resultTicket)
            setTicket(resultTicket);
            setWaiting(false);
            //setError(error);
            //setShow(true);
            //console.log(page);
        });
    }, [id]);

    useEffect(() => {
        API.latestStatus(id,  user.access_token)
            .then(s => {
                setStatus(s)
            });
    }, [id]);

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
            default:
                return "IN PROGRESS";
        }
    }

    const handleResolveTicket = async (status) => {
            await API.resolveTicket(status, user.access_token);
            const newStatus = await API.latestStatus(status.ticket.id);
            setStatus(newStatus)

    };
    const handleProgressTicket = async (status) => {
            await API.progressTicket(status, user.access_token);
            const newStatus = await API.latestStatus(status.ticket.id);
            setStatus(newStatus)
        };

    const handleOpenTicket= async (status) => {
        await API.openTicket(status, user.access_token);
        const newStatus = await API.latestStatus(status.tid.id);
        setStatus(newStatus)
    };
    const handleCloseTicket= async (status) => {
        console.log(status);
            await API.closeTicket(status, user.access_token);
            const newStatus = await API.latestStatus(status.ticket.id);
            setStatus(newStatus)
        };
    const handleReopenTicket = async (status) => {
            await API.reopenTicket(status, user.access_token);
            const newStatus = await API.latestStatus(status.ticket.id);
            setStatus(newStatus)
        };

    return (
        ticket &&
            <Container fluid style = {{display: "flex", flexDirection: "column", alignItems : "center"}}>
                <h1>Ticket details</h1>

                <Table style={{
                    border: '2px solid grey',
                    borderRadius: "10px",
                    borderCollapse: "collapse",
                    width: '60%',
                }}>
                    <tbody>
                    <tr>
                        <th>Ticket id</th>
                        <td>{ticket.id}</td>
                    </tr>
                    <tr>
                        <th>Customer </th>
                        <td>{ticket.customer && ticket.customer.username}</td>
                    </tr>
                    <tr>
                        <th>Expert </th>
                        <td>{ticket.expert && ticket.expert.username}</td>
                    </tr>
                    <tr>
                        <th>Product </th>
                        <td>{ticket.product && ticket.product.name}</td>
                    </tr>
                    <tr>
                        <th>Issue Type</th>
                        <td>{ticket.issueType}</td>
                    </tr>
                    <tr>
                        <th>Description</th>
                        <td>{ticket.description}</td>
                    </tr>
                    <tr>
                        <th>Priority Level</th>
                        <td>{ticket.priorityLevel}</td>
                    </tr>
                    <tr>
                        <th>Date Of Creation</th>
                        <td>{dayjs(ticket.dateOfCreation).format("YYYY-MM-DD")}</td>
                    </tr>
                    <tr>
                        <th>Status</th>
                        <td>
                            {status ? getStatusLabel(status.status) : "N/A"}
                        </td>
                    </tr>
                    </tbody>
                </Table>
                <Form>
                    <Form.Group controlId="status">
                        <Dropdown>
                            <Dropdown.Toggle> Change Status <RxUpdate /> </Dropdown.Toggle>
                            <Dropdown.Menu>
                                <Dropdown.Item onClick={() => {
                                    handleOpenTicket(status);
                                }}> OPEN  </Dropdown.Item>
                                <Dropdown.Item onClick={() => {
                                    handleCloseTicket(status);
                                }}> CLOSED  </Dropdown.Item>
                                <Dropdown.Item onClick={() => {
                                    handleReopenTicket(status);
                                }}> REOPENED  </Dropdown.Item>
                                <Dropdown.Item onClick={() => {
                                    handleResolveTicket(status);
                                }}> RESOLVED  </Dropdown.Item>
                                <Dropdown.Item onClick={() => {
                                    handleProgressTicket(status);
                                }}> IN PROGRESS  </Dropdown.Item>
                            </Dropdown.Menu>
                        </Dropdown>
                    </Form.Group>
                </Form>

            </Container>

            );

}
export default TicketDetails;