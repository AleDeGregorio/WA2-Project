import {useNavigate, Link} from "react-router-dom";
import {useContext, useEffect, useState} from "react";
import LoginContext from "../Profiles/LoginContext";
import {Button, Container, CardGroup, Card, Form, FormGroup, Dropdown, Col, Row} from "react-bootstrap";
import DropdownItem from "react-bootstrap/DropdownItem";
import API from "../API";
import Badge from 'react-bootstrap/Badge';

/*const tickets = [
    {id : 1, customer: 1, expert: null, product: 1, status:"opened", issueType: "field1", description: "bad phone", priorityLevel: null, dateOfCreation: "11-07-2023"  },
    {id : 2, customer: 2, expert: null, product: 2, status:"opened", issueType: "field2", description: "bad laptop", priorityLevel: null, dateOfCreation: "11-07-2023"  },
    {id : 3, customer: 1, expert: null, product: 3, status:"opened", issueType: "field2", description: "bad dishwasher", priorityLevel: null, dateOfCreation: "11-07-2023"  }
]

const experts = [
    { id: 1, username: "username1", fields: ["field1", "field2"] },
    { id: 2, username: "username2", fields: ["field2", "field3"] },
    { id: 3, username: "username3", fields: ["field1", "field2"] },
    // ... other experts
];*/
function ManagerHandlingTickets(props) {
    const navigate = useNavigate()
    const user = useContext(LoginContext)
    const [loadContext, setLoadContext] = useState(true)
    const [loading, setLoading] = useState(true)
    const { setError, setShow } = props;

    const [experts, setExperts] = useState([])
    const [tickets, setTickets] = useState([])

    if (user.role !== "manager")
        navigate('/wrongPrivileges')

    useEffect(() => {
        if(loadContext && user) {
            setLoadContext(false)
        }
        else if(user.role === "manager") {
            API.getAllExperts(user.access_token)
                .then(experts => {
                    setExperts(experts)

                    setLoading(false)
                })
                .catch(error => {
                    setError(error)
                    setShow(true)

                    setTimeout(() => {
                        setShow(false)
                    }, 3000)
                });

            API.getAllTickets(user.access_token)
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

    return(
        <Container className={"d-grid gap-3"}>
            <Row>
                <Col xs={1}>
                    Ticket ID
                </Col>
                <Col xs={1}>
                    STATUS
                </Col>
                <Col xs={1}>
                    CREATION
                </Col>
                <Col xs={1}>
                    PRODUCT
                </Col>
                <Col xs={1}>
                    ISSUE
                </Col>
                <Col xs={2}>
                    DESCRIPTION
                </Col>
                <Col xs={2}>
                    PRIORITY
                </Col>
                <Col xs={2}>
                    EXPERT
                </Col>

            </Row>
            {tickets.map((ticket) => (
                <ManagerViewSingleTicket key={ticket.id} ticket={ticket} user ={user} experts={experts} />
            ))}
        </Container>
    )
}

function ManagerViewSingleTicket(props) {
    const [selectedPriority, setSelectedPriority] = useState(null);

    //const { setError, setShow } = props
    const [success, setSuccess] = useState(false);

    const {ticket, user, experts} = props
    const [status, setStatus] = useState("")

    useEffect(() => {
        API.getLatestTicketStatus(ticket.id, user.access_token)
            .then( response => {
                    setStatus(response.status);

                }
            )
            .catch(error => {
                console.log("no status")
            });
    }, [])
    const handlePriorityChange = (priority) => {
        setSelectedPriority(priority);
    };

    const handleSubmitPriority = (ticketId) => {
        if (selectedPriority !== null) {
            const params = new URLSearchParams()
            params.append("priorityLevel",  Number(selectedPriority))
            const payload = params.toString();

            // Send the payload
           API.setPriority(ticketId, payload, user.access_token)
               .then((response) => {
                   if (response.status === 202) {
                       // Handle success
                       console.log("Priority assigned successfully!");
                   } else {
                       // Handle error
                       console.error("Error assigning priority.");
                   }
               })
               .catch((error) => {
                   console.error("Error:", error);
               });
        }
    };

    const [selectedExpert, setSelectedExpert] = useState(null);

    const handleExpertChange = (expertId) => {
        setSelectedExpert(expertId); // Update selectedExpert with the expertId
    };

    const handleAssignExpert = (ticketId) => {
        if (selectedExpert !== null) {
            // Construct JSON payload with expertId
            const expert = experts.find((e) => e.id === selectedExpert)
            const payload = expert;

            // Send the payload
            API.setExpertTicket(ticketId, payload, props.user.access_token)
                .then((response) => {
                    if (response.status === 202) {
                        // Handle success
                        console.log("Expert assigned successfully!");
                    } else {
                        // Handle error
                        console.error("Error assigning expert.");
                    }
                })
                .catch((error) => {
                    console.error("Error:", error);
                });
        }
    };

    const date = new Date(ticket.dateOfCreation);
    const options = { year: 'numeric', month: 'numeric', day: 'numeric' };
    const formattedDate = new Intl.DateTimeFormat('it-IT', options).format(date);

    return (
        <Row>
            <Col xs={1}>
                {ticket.id}
            </Col>
            <Col xs={1}>
                {status}
            </Col>
            <Col xs={1}>
                {formattedDate}
            </Col>
            <Col xs={1}>
                {ticket.product.ean}
            </Col>
            <Col xs={1}>
                {ticket.issueType}
            </Col>
            <Col xs={2}>
                {ticket.description}
            </Col>
            <Col xs={2}>
                {ticket.priorityLevel
                    ? <Badge bg="danger">{ticket.priorityLevel}</Badge>
                    :<Form>
                        <Dropdown onSelect={(eventKey) => handlePriorityChange(eventKey)}>
                            <Dropdown.Toggle variant="primary" id="priority-dropdown">
                                {selectedPriority !== null
                                    ? `Priority: ${selectedPriority}`
                                    : "Select Priority"}
                            </Dropdown.Toggle>
                            <Dropdown.Menu>
                                {[1, 2, 3, 4, 5].map((priority) => (
                                    <Dropdown.Item key={priority} eventKey={priority}>
                                        {priority}
                                    </Dropdown.Item>
                                ))}
                            </Dropdown.Menu>
                        </Dropdown>
                        <Button onClick={() => handleSubmitPriority(ticket.id)}>SET</Button>
                    </Form>}

            </Col>
            <Col xs={2}>
                {ticket.expert !== null
                    ? <Badge bg="success">{ticket.expert.username}</Badge>
                    : <Form>
                        <Dropdown onSelect={(eventKey) => handleExpertChange(eventKey)}>
                            <Dropdown.Toggle variant="primary" id="expert-dropdown">
                                {selectedExpert !== null
                                    ? `Exp.: ${
                                        
                                        experts.find((expert) => expert.id === selectedExpert)
                                            ?.username || "Unknown Expert"
                                    }`
                                    : "Select Expert"}
                            </Dropdown.Toggle>
                            <Dropdown.Menu>
                                {experts
                                    .filter((expert) => expert.fields.includes(ticket.product.category))
                                    .map((expert) => (
                                        <Dropdown.Item key={expert.id} eventKey={expert.id}>
                                            {expert.username}
                                        </Dropdown.Item>
                                    ))}
                            </Dropdown.Menu>
                        </Dropdown>
                        <Button onClick={() => handleAssignExpert(ticket.id)}>
                            Assign Expert
                        </Button>
                    </Form>}
            </Col>
        </Row>
    )
}
export default ManagerHandlingTickets;