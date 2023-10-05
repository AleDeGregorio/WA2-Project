import {Container, Table} from "react-bootstrap";
import {useLocation, useNavigate, useParams} from "react-router-dom";
import {useContext, useEffect, useState} from "react";
import API from "../API";
import LoginContext from "../Profiles/LoginContext";

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
        <Container fluid style = {{display: "flex", flexDirection: "column", alignItems : "center"}}>
            <h1>Ticket details</h1>
            {
                loading ?
                    <div>
                        <br />
                        <div className="spinner-border" role="status"></div>
                    </div> :

                    <Table style={{
                        border: '2px solid grey',
                        borderRadius: '10px', // Arrotondamento degli angoli
                        borderCollapse: 'collapse', // Per evitare spazi tra le celle
                        width: '80%', // Per allineare la tabella al 100% della larghezza del contenitore
                    }}>
                        <tbody>
                        <tr>
                            <th>Ticket id</th>
                            <td>{ticketDetails.tid.id}</td>
                        </tr>
                        <tr>
                            <th>Customer Id</th>
                            <td>{ticketDetails.tid.customer?.id}</td>
                        </tr>
                        <tr>
                            <th>Expert Id</th>
                            {
                                ticketDetails.tid.expert ?
                                    <td>{ticketDetails.tid.expert?.id}</td> :
                                    <td>Ticket not assigned to expert yet</td>
                            }
                        </tr>
                        <tr>
                            <th>Product Id</th>
                            <td>{ticketDetails.tid.product?.ean}</td>
                        </tr>
                        <tr>
                            <th>Status</th>
                            <td>{ticketDetails.status}</td>
                        </tr>
                        <tr>
                            <th>Issue Type</th>
                            <td>{ticketDetails.tid.issueType}</td>
                        </tr>
                        <tr>
                            <th>Description</th>
                            <td>{ticketDetails.tid.description}</td>
                        </tr>
                        <tr>
                            <th>Priority Level</th>
                            {
                                ticketDetails.tid.priorityLevel ?
                                    <td>{ticketDetails.tid.priorityLevel}</td> :
                                    <td>Priority level not assigned yet</td>
                            }
                        </tr>
                        <tr>
                            <th>Date Of Creation</th>
                            <td>{new Date(ticketDetails.tid.dateOfCreation).toLocaleString()}</td>
                        </tr>
                        </tbody>
                    </Table>
            }
        </Container>
    )
}

export default CustomerTicketDetails;