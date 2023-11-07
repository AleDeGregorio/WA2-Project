import {Button, Container, Dropdown, Form, Table} from "react-bootstrap";
import {Link, useLocation, useNavigate, useParams} from "react-router-dom";
import {useContext, useEffect, useState} from "react";
import API from "../API";
import LoginContext from "../Profiles/LoginContext";
import {RxUpdate} from "react-icons/rx";
import {BiChat} from "react-icons/bi";
import {GoHistory} from "react-icons/go";

function CustomerTicketDetails(props) {
    const navigate = useNavigate()
    const location = useLocation()

    const { ticket } = location.state

    const user = useContext(LoginContext)

    const { setError, setShow } = props;

    const [loading, setLoading] = useState(true)
    const [loadContext, setLoadContext] = useState(true)

    const [ticketDetails, setTicketDetails] = useState([])

    useEffect(() => {
        if(loadContext && user) {
            setLoadContext(false)
        }
        else if(user.role === "customer") {
            API.getLatestTicketStatus(ticket.id, user.access_token)
                .then(t => {
                    setTicketDetails(t)

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

    return (
        <Container fluid style = {{display: "flex", flexDirection: "column", alignItems : "center", fontFamily : "system-ui", padding: "5%"}}>
            <h1>TICKET DETAILS</h1>
            {
                loading ?
                    <div>
                        <br />
                        <div className="spinner-border" role="status"></div>
                    </div> :

                    <>
                        <Table style={{
                            border: '2px solid grey',
                            borderRadius: "10px",
                            borderCollapse: "collapse",
                            width: '60%',
                        }}>
                            <tbody>
                            <tr>
                                <th className="text-success">Ticket id</th>
                                <td>{ticketDetails.tid.id}</td>
                            </tr>
                            <tr>
                                <th className="text-success">Customer </th>
                                <td>{ticketDetails.tid.customer?.id && ticketDetails.tid.customer?.username}</td>
                            </tr>
                            <tr>
                                <th className="text-success">Expert </th>
                                {
                                    ticketDetails.tid.expert ?
                                        <td>{ticketDetails.tid.expert?.id && ticketDetails.tid.expert?.username}</td> :
                                        <td>Ticket not assigned to expert yet</td>
                                }
                            </tr>
                            <tr>
                                <th className="text-success">Product </th>
                                <td>{ticketDetails.tid.product?.ean && ticketDetails.tid.product?.name}</td>
                            </tr>
                            <tr>
                                <th className="text-success">Issue Type</th>
                                <td>{ticketDetails.tid.issueType}</td>
                            </tr>
                            <tr>
                                <th className="text-success">Description</th>
                                <td>{ticketDetails.tid.description}</td>
                            </tr>
                            <tr>
                                <th className="text-success">Priority Level</th>
                                {
                                    ticketDetails.tid.priorityLevel ?
                                        <td>{ticketDetails.tid.priorityLevel}</td> :
                                        <td>Priority level not assigned yet</td>
                                }
                            </tr>
                            <tr>
                                <th className="text-success">Date Of Creation</th>
                                <td>{new Date(ticketDetails.tid.dateOfCreation).toLocaleString()}</td>
                            </tr>
                            <tr>
                                <th className="text-success">Status</th>
                                <td>{ticketDetails.status}</td>
                            </tr>
                            </tbody>
                        </Table>
                        <Container style = {{display : "flex", justifyContent: "space-evenly" }}>
                            {
                                ticketDetails.tid.expert ?
                                    <Link to='/viewChat' state={{ ticket: ticketDetails.tid }}>
                                        <Button style = {{backgroundColor: "#057F5F", border: "none"}}>Customer Service <BiChat/></Button>
                                    </Link> : false
                            }
                        </Container>
                    </>
            }
        </Container>
    )
}

export default CustomerTicketDetails;