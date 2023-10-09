import {useNavigate, Link} from "react-router-dom";
import {useContext, useEffect, useState} from "react";
import LoginContext from "../Profiles/LoginContext";
import {Button, Container, CardGroup, Card, Form, FormGroup, Dropdown} from "react-bootstrap";
import {GiTicket}  from 'react-icons/gi'
import API from "../API";
import dayjs from 'dayjs'

function ManagerTickets(props) {
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
        else if(user.role === "manager") {
            API.tickets(user.access_token)
                .then(t => {
                    setTickets(t)

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
                        {tickets.map((t) => {
                            return (
                                <Card key={t.id} style = {{margin: "5%", border: " solid 2px"}} >
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
                                            <Card.Text style={{ display: "flex", justifyContent: "space-between" }}> Created By: {t.customer && t.customer.username}
                                            </Card.Text>
                                            <Card.Text> Served By: {t.expert && t.expert.username}
                                            </Card.Text>
                                        </Container>

                                    </Card.Body>

                                    <Card.Footer style={{ display: "flex", justifyContent: "space-between", alignItems: "center"}}>
                                        <Card.Subtitle style={{ display: "flex", justifyContent: "space-between" }}>
                                            <Card.Text>
                                                Creation Date: {dayjs(t.dateOfCreation).format('YYYY-MM-DD')}
                                            </Card.Text>
                                        </Card.Subtitle>
                                        <Link to={`/viewTicket/${t.id}`}>
                                            <Button style = {{backgroundColor: "#057F5F", border: "none"}}>Ticket Detail <GiTicket /> </Button>{" "}
                                        </Link>
                                    </Card.Footer>
                                </Card>

                            );
                        })}
                    </CardGroup>
            }
        </>
    );
}


export default ManagerTickets;

