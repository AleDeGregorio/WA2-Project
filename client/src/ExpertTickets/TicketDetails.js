import {Container, Table, Button, Form, Dropdown, Row, Col, Alert} from "react-bootstrap";
import {Link, useParams} from "react-router-dom";
import {RxUpdate} from "react-icons/rx"
import {BiChat} from "react-icons/bi"
import {useContext, useEffect, useState} from "react";
import API from "../API";
import dayjs from "dayjs"
import LoginContext from "../Profiles/LoginContext";
import {GoHistory} from "react-icons/go";

function TicketDetails(props) {
    const {id} = useParams();
    const user = useContext(LoginContext)
    const [ticket, setTicket] = useState([]);
    const [waitingTicket, setWaitingTicket] = useState(true);
    const [waitingStatus, setWaitingStatus] = useState(true);
    const [status, setStatus] = useState([]);
     const [error, setError] =useState();
     const [show, setShow] = useState(false);

    useEffect(() => {
        API.ticketDetails(id,  user.access_token).then((resultTicket) => {
           // console.log(resultTicket)
            setTicket(resultTicket);
            setWaitingTicket(false);
            setError(error);
            setShow(true);
        });
    }, [id]);

    useEffect(() => {
        API.latestStatus(id,  user.access_token)
            .then(s => {
                setStatus(s)
            });
    }, [id, status]);

    useEffect(()=>{
       // console.log("status:", status);
        setWaitingStatus(false)
    },[status])

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

    const handleOpenTicket = async (s) => {
        const newStatus = {
            tid: s.tid,
            lastModifiedDate: new Date(),
            status: "OPEN",
        }
        await API.openTicket(newStatus, user.access_token)
            .then( () => {
                    setStatus(newStatus);
                }
            )
            .catch(error => {
                setError(error);
                setShow(true)
            })
    };
    const handleProgressTicket = async (s) => {
        const newStatus = {
            tid: s.tid,
            lastModifiedDate: new Date(),
            status: "IN_PROGRESS",
        }
        await API.progressTicket(newStatus, user.access_token)
            .then( () => {
                    setStatus(newStatus);
                }
            )
            .catch(error => {
                setError(error);
                setShow(true)
            })
    };

    const handleCloseTicket = async (s) => {
        const newStatus = {
            tid: s.tid,
            lastModifiedDate: new Date(),
            status: "CLOSED",
        }
       // console.log(newStatus);
       // console.log(status)
        try {
            await API.closeTicket(newStatus, user.access_token);
            setStatus(newStatus);
            //console.log(newStatus);
        } catch (error) {
            setError(error);
            setShow(true);
        }
    }
    const handleReopenTicket = async (s) => {
        const newStatus = {
            tid: s.tid,
            lastModifiedDate: new Date(),
            status: "REOPENED",
        }
        //console.log("prima : ",newStatus);
        API.reopenTicket(newStatus, user.access_token)
            .then( () => {
                setStatus(newStatus);
               // console.log("dopo : ",newStatus);
                }
            )
            .catch(error => {
                setError(error);
                setShow(true)
            })
    };
    const handleResolveTicket = async (s) => {
        const newStatus = {
            tid: s.tid,
            lastModifiedDate: new Date(),
            status: "RESOLVED",
        }
        API.resolveTicket(newStatus, user.access_token)
            .then( () => {
                    setStatus(newStatus);
                    //console.log("dopo : ",newStatus);
                }
            )
            .catch(error => {
                setError(error);
                setShow(true)
            })
    };

    return (
        ticket &&
            <Container fluid style = {{display: "flex", flexDirection: "column", alignItems : "center", fontFamily : "system-ui", padding: "5%"}}>
                <h1 style={{fontWeight: "700"}}>TICKET DETAILS</h1>
                <Table style={{
                    border: '2px solid grey',
                    borderRadius: "10px",
                    borderCollapse: "collapse",
                    width: '60%',
                }}>
                    <tbody>
                    <tr>
                        <th className="text-success">Ticket id</th>
                        <td>{ticket.id}</td>
                    </tr>
                    <tr>
                        <th className="text-success">Customer </th>
                        <td>{ticket.customer && ticket.customer.username}</td>
                    </tr>
                    <tr>
                        <th className="text-success">Expert </th>
                        <td>{ticket.expert && ticket.expert.username}</td>
                    </tr>
                    <tr>
                        <th className="text-success">Product </th>
                        <td>{ticket.product && ticket.product.name}</td>
                    </tr>
                    <tr>
                        <th className="text-success">Issue Type</th>
                        <td>{ticket.issueType}</td>
                    </tr>
                    <tr>
                        <th className="text-success">Description</th>
                        <td>{ticket.description}</td>
                    </tr>
                    <tr>
                        <th className="text-success">Priority Level</th>
                        <td>{ticket.priorityLevel}</td>
                    </tr>
                    <tr>
                        <th className="text-success">Date Of Creation</th>
                        <td>{dayjs(ticket.dateOfCreation).format("YYYY-MM-DD")}</td>
                    </tr>
                        <tr>
                            <th className="text-success">Status</th>
                            <td>
                                {waitingStatus ? (
                                    "Loading..."
                                ) : (
                                    status ? getStatusLabel(status.status) : "N/A"
                                )}
                            </td>
                        </tr>
                    </tbody>
                </Table>
                <Container style = {{display : "flex", justifyContent: "space-evenly" }}>
                    <Form >
                    <Form.Group controlId="status">
                        <Dropdown>
                            <Dropdown.Toggle style = {{backgroundColor: "#057F5F", border: "none"}}> Change Status <RxUpdate /> </Dropdown.Toggle>
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
                    {user.role == "expert" ?
                    <Link to={`/viewChat/`}>
                        <Button style = {{backgroundColor: "#057F5F", border: "none"}}>Customer Service <BiChat/></Button>
                    </Link>
                        : user.role == "manager" ?
                            <Link to={`/viewStatus/${ticket.id}`}>
                                <Button style={{ backgroundColor: "#057F5F", border: "none" }}>
                                    Status History <GoHistory />
                                </Button>
                            </Link> : ''
                    }
            </Container>
            </Container>

            );

}
export default TicketDetails;