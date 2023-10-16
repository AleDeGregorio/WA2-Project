import {useNavigate, Link} from "react-router-dom";
import {useContext, useState} from "react";
import LoginContext from "../Profiles/LoginContext";
import {Button, Container, CardGroup, Card } from "react-bootstrap";
import {useEffect} from "react";
import API from "../API";
import {GiTicket} from "react-icons/gi";

function ViewCustomerTickets(props) {
    const navigate = useNavigate()

    const user = useContext(LoginContext)

    const { setError, setShow } = props;

    const [loading, setLoading] = useState(true)
    const [loadContext, setLoadContext] = useState(true)

    const [tickets, setTickets] = useState([])

    useEffect(() => {
        if(loadContext && user) {
            setLoadContext(false)
        }
        else if(user.role === "customer") {
            API.getCustomerTickets(user.id, user.access_token)
                .then(tickets => {
                    setTickets(tickets)

                    setLoading(false)
                })
                .catch(error => {
                    setError(error)
                    setShow(true)

                    setTimeout(() => {
                        setShow(false)
                    }, 3000)
                });
        }
        else {
            navigate('/wrongPrivileges')
        }
    }, [loadContext])

    return (
        <>
            <h1 style={{ textAlign: "center" }}>Ticket List</h1>

            {
                loading ?
                    <div>
                        <br />
                        <div className="spinner-border" role="status"></div>
                    </div> :

                    <CardGroup className="ticketList" style = {{ display: "flex", flexWrap: "wrap", justifyContent: "center" }}>
                        {
                            loading ?
                                <div>
                                    <br />
                                    <div className="spinner-border" role="status"></div>
                                </div> :

                                tickets.map((t) => {
                                    return (
                                        <Card key={t.id} style={{ width: "30%", margin: "5%" }}>
                                            <Card.Body>
                                                <Card.Title style={{ fontWeight: "900" }}>{t.description}</Card.Title>
                                                <Container
                                                    style={{
                                                        display: "flex",
                                                        justifyContent: "space-between",
                                                        flexDirection:"column",
                                                        padding: "0px",
                                                        alignItems: "flex-start"
                                                    }}
                                                >
                                                    <Card.Text>
                                                        Device : {t.product && t.product.name}
                                                    </Card.Text>
                                                    <Card.Text style={{ display: "flex", justifyContent: "space-between" }}> Created By: {t.customer.firstName} {t.customer.lastName}
                                                    </Card.Text>
                                                    {
                                                        t.expert ?
                                                            <Card.Text>
                                                                Served By: {t.expert?.firstName} {t.expert?.lastName}
                                                            </Card.Text> :
                                                            <Card.Text>
                                                                Served By: not served yet
                                                            </Card.Text>
                                                    }
                                                </Container>
                                            </Card.Body>
                                            <Card.Footer style={{ display: "flex", justifyContent: "space-between" }}>
                                                <Card.Subtitle style={{ display: "flex", justifyContent: "space-between" }}>
                                                    <Card.Text>
                                                        Creation Date: {new Date(t.dateOfCreation).toLocaleString()}
                                                    </Card.Text>
                                                </Card.Subtitle>
                                                <Link to='/customerTicketDetails' state={{ ticket: t }}>
                                                    <Button style = {{backgroundColor: "#057F5F", border: "none"}}>Ticket Detail <GiTicket /> </Button>{" "}
                                                </Link>
                                            </Card.Footer>
                                        </Card>

                                    );
                                })}
                    </CardGroup>
            }
        </>
    )
}

export default ViewCustomerTickets;