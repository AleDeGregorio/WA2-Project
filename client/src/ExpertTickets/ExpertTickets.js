import {useNavigate, Link} from "react-router-dom";
import {useContext} from "react";
import LoginContext from "../Profiles/LoginContext";
import {Button, Container, CardGroup, Card, Form, FormGroup, Dropdown} from "react-bootstrap";
import DropdownItem from "react-bootstrap/DropdownItem";

function ExpertTickets() {
    const navigate = useNavigate()
    const user = useContext(LoginContext)

    if (user.role !== "expert")
        navigate('/wrongPrivileges')

    const tickets = [
        {id : 1, customer: 1, expert: 1, product: 1, status:"opened", issueType: "phone", description: "bad phone", priorityLevel: 2, dateOfCreation: "11-07-2023"  },
        {id : 2, customer: 1, expert: 1, product: 1, status:"opened", issueType: "phone", description: "bad phone", priorityLevel: 2, dateOfCreation: "11-07-2023"  },
        {id : 3, customer: 1, expert: 1, product: 1, status:"opened", issueType: "phone", description: "bad phone", priorityLevel: 2, dateOfCreation: "11-07-2023"  }
    ]
        return (
        <CardGroup className="ticketList" style = {{display: "flex", flexDirection: "column", alignContent: "space-around", alignItems: "center" }}>
            <h1 >
                Ticket List
            </h1>
            {tickets.map((t) => {
                return (
                    <Card key={t.id} style = {{width: "70%", margin: "20px"}}>
                        <Card.Body>
                            <Card.Title style={{ fontWeight: "900" }}>{t.description}</Card.Title>
                            <Container
                                style={{
                                    display: "flex",
                                    justifyContent: "space-between",
                                    alignItems: "baseline",
                                    padding: "0px",
                                }}
                            >

                                <Card.Text style={{ display: "flex", justifyContent: "space-between" }}> Created By: {t.customer}
                                </Card.Text>
                                <Card.Text> Served By: {t.expert}
                                </Card.Text>
                            </Container>
                            <Card.Subtitle style={{ display: "flex", justifyContent: "space-between" }}>
                                <Card.Text>
                                    Creation Date: {t.dateOfCreation}
                                </Card.Text>
                                <Card.Text>
                                    State:{t.status}
                                </Card.Text>
                            </Card.Subtitle>
                        </Card.Body>
                        <Card.Footer style={{ display: "flex", justifyContent: "space-between" }}>
                            <Link to={'/viewTicket'}>
                                <Button>Ticket Detail</Button>{" "}
                            </Link>
                            <Form>
                                <Form.Group controlId="status">
                                    <Dropdown>
                                        <Dropdown.Toggle>Change Status</Dropdown.Toggle>
                                        <Dropdown.Menu>
                                            <DropdownItem> Opened</DropdownItem>
                                            <DropdownItem> Closed</DropdownItem>
                                            <DropdownItem> Resolved</DropdownItem>
                                            <DropdownItem> Reopened</DropdownItem>
                                        </Dropdown.Menu>
                                    </Dropdown>
                                </Form.Group>
                            </Form>
                        </Card.Footer>
                    </Card>

                );
            })}
        </CardGroup>

        );
}


export default ExpertTickets;

