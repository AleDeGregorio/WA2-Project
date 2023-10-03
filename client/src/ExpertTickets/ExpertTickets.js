import {useNavigate, Link} from "react-router-dom";
import {useContext, useEffect, useState} from "react";
import LoginContext from "../Profiles/LoginContext";
import {Button, Container, CardGroup, Card, Form, FormGroup, Dropdown} from "react-bootstrap";
import DropdownItem from "react-bootstrap/DropdownItem";
import {GiTicket}  from 'react-icons/gi'
import API from "../API";
import dayjs from 'dayjs'

function ExpertTickets() {
    const navigate = useNavigate()
    const user = useContext(LoginContext)
   // console.log(user.id)
    if (user.role !== "expert")
        navigate('/wrongPrivileges')
   // const [expert, setExpert] = useState();
    const [tickets, setTickets] = useState([{}])
    const [show, setShow] = useState(false);
    const [error, setError] = useState(false);

    //console.log(user.access_token);

    useEffect(() => {
        API.expertTickets(user.id, user.access_token)
            .then(t => {
                //console.log(t);
                setTickets(t);
                for (let i = 0; i < tickets.length; i++) {
                    //console.log(tickets[i]);

                }
            })
            .catch(error => {
                setError(error);
                setShow(true)
            });
    });



/*
    const tickets = [
        {id : 1, customer: 1, expert: 1, product: 1, status:"opened", issueType: "phone", description: "bad phone", priorityLevel: 2, dateOfCreation: "11-07-2023"  },
        {id : 2, customer: 1, expert: 1, product: 1, status:"opened", issueType: "phone", description: "bad phone", priorityLevel: 2, dateOfCreation: "11-07-2023"  },
        {id : 3, customer: 1, expert: 1, product: 1, status:"opened", issueType: "phone", description: "bad phone", priorityLevel: 2, dateOfCreation: "11-07-2023"  }
    ]

*/
        return (
            <>
                <h1 style={{ textAlign: "center" }}>Ticket List</h1>
        <CardGroup className="ticketList" style = {{ display: "flex", flexWrap: "wrap", justifyContent: "center" }}>
            {tickets.map((t) => {

                    return (
                    <Card key={t.id} style={{ width: "30%", margin: "10px" }}>
                        <Card.Body>
                            <Card.Title style={{ fontWeight: "900" }}>{t.description}</Card.Title>
                            <Container
                                style={{
                                    display: "flex",
                                    justifyContent: "space-between",
                                    flexDirection:"row",
                                    alignItems: "baseline",
                                    padding: "0px",
                                }}
                            >
                                <Card.Subtitle>
                                    Device : {t.product && t.product.name}
                                </Card.Subtitle>
                                <Card.Text style={{ display: "flex", justifyContent: "space-between" }}> Created By: {t.customer && t.customer.username}
                                </Card.Text>
                                <Card.Text> Served By: {t.expert && t.expert.username}
                                </Card.Text>
                            </Container>
                            <Card.Subtitle style={{ display: "flex", justifyContent: "space-between" }}>
                                <Card.Text>
                                    Creation Date: {dayjs(t.dateOfCreation).format('YYYY-MM-DD')}
                                </Card.Text>
                            </Card.Subtitle>
                        </Card.Body>
                        <Card.Footer style={{ display: "flex", justifyContent: "space-between", flexDirection: "row-reverse" }}>
                            <Link to={`/viewTicket/${t.id}`}>
                                <Button>Ticket Detail <GiTicket /> </Button>{" "}
                            </Link>
                        </Card.Footer>
                    </Card>

                );
            })}
        </CardGroup>
            </>
        );


}


export default ExpertTickets;

