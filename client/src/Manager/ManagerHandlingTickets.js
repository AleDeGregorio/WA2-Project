import {useNavigate, Link} from "react-router-dom";
import {useContext, useState} from "react";
import LoginContext from "../Profiles/LoginContext";
import {Button, Container, CardGroup, Card, Form, FormGroup, Dropdown, Col, Row} from "react-bootstrap";
import DropdownItem from "react-bootstrap/DropdownItem";

const tickets = [
    {id : 1, customer: 1, expert: null, product: 1, status:"opened", issueType: "field1", description: "bad phone", priorityLevel: null, dateOfCreation: "11-07-2023"  },
    {id : 2, customer: 2, expert: null, product: 2, status:"opened", issueType: "field2", description: "bad laptop", priorityLevel: null, dateOfCreation: "11-07-2023"  },
    {id : 3, customer: 1, expert: null, product: 3, status:"opened", issueType: "field2", description: "bad dishwasher", priorityLevel: null, dateOfCreation: "11-07-2023"  }
]

const experts = [
    { id: 1, username: "username1", fields: ["field1", "field2"] },
    { id: 2, username: "username2", fields: ["field2", "field3"] },
    { id: 3, username: "username3", fields: ["field1", "field2"] },
    // ... other experts
];
function ManagerHandlingTickets() {
    const navigate = useNavigate()
    const user = useContext(LoginContext)

    if (user.role !== "manager")
        navigate('/wrongPrivileges')

    return(
        <Container className={"d-grid gap-3"}>
            <Row>
                <Col>
                    Ticket ID
                </Col>
                <Col>
                    STATUS
                </Col>
                <Col>
                    CREATION
                </Col>
                <Col>
                    PRODUCT
                </Col>
                <Col>
                    ISSUE
                </Col>
                <Col>
                    DESCRIPTION
                </Col>
                <Col>
                    PRIORITY
                </Col>
                <Col>
                    EXPERT
                </Col>
                <Col>
                    CHANGE STATUS
                </Col>
            </Row>
            {tickets.map((ticket) => (
                <ManagerViewSingleTicket key={ticket.id} ticket={ticket} />
            ))}
        </Container>
    )
}

function ManagerViewSingleTicket({ticket}) {
    const [selectedPriority, setSelectedPriority] = useState(null);

    const handlePriorityChange = (priority) => {
        setSelectedPriority(priority);
    };

    const handleSubmitPriority = (ticketId) => {
        if (selectedPriority !== null) {
            // Construct JSON payload
            const payload = { priorityLevel: selectedPriority };

            // Send the payload
            fetch(`http://localhost:8080/API/ticket/${ticketId}/priority`, {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(payload),
            })
                .then((response) => {
                    if (response.status === 202) {
                        // Handle success
                        console.log("Priority level set successfully!");
                    } else {
                        // Handle error
                        console.error("Error setting priority level.");
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
            const payload = { expertId: Number(selectedExpert) };

            // Send the payload
            fetch(`http://localhost:8080/API/ticket/${ticketId}/expert`, {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(payload),
            })
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



    return (
        <Row>
            <Col>
                {ticket.id}
            </Col>ManagerHandlingTickets.js
            <Col>
                {ticket.status}
            </Col>
            <Col>
                {ticket.dateOfCreation}
            </Col>
            <Col>
                {ticket.product}
            </Col>
            <Col>
                {ticket.issueType}
            </Col>
            <Col>
                {ticket.description}
            </Col>
            <Col>
                <Form>
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
                </Form>
                <Button onClick={() => handleSubmitPriority(ticket.id)}>SET</Button>
            </Col>
            <Col>
                <Form>
                    <Dropdown onSelect={(eventKey) => handleExpertChange(eventKey)}>
                        <Dropdown.Toggle variant="primary" id="expert-dropdown">
                            {selectedExpert !== null
                                ? `Assign Expert: ${
                                    experts.find((expert) => expert.id === Number(selectedExpert))
                                        ?.username || "Unknown Expert"
                                }`
                                : "Select Expert"}
                        </Dropdown.Toggle>
                        <Dropdown.Menu>
                            {experts
                                .filter((expert) => expert.fields.includes(ticket.issueType))
                                .map((expert) => (
                                    <Dropdown.Item key={expert.id} eventKey={expert.id}>
                                        {expert.username}
                                    </Dropdown.Item>
                                ))}
                        </Dropdown.Menu>
                    </Dropdown>
                </Form>
                <Button onClick={() => handleAssignExpert(ticket.id)}>
                    Assign Expert
                </Button>
            </Col>
            <Col>
                <Button>Change status</Button>
            </Col>
        </Row>
    )
}
export default ManagerHandlingTickets;