import {useNavigate} from "react-router-dom";
import {useContext} from "react";
import LoginContext from "../Profiles/LoginContext";
import {Button, Container} from "react-bootstrap";

function CustomerTickets() {
    const navigate = useNavigate()

    const user = useContext(LoginContext)

    if (user.role !== "customer")
        navigate('/wrongPrivileges')

    return (
        <Container fluid>
            <h1 style={{textAlign: 'center', padding:'20px'}}>Select your service</h1>
            <div className="d-grid gap-2" style={{padding: '30px'}}>
                <Button variant="primary" size="lg" style={{marginBottom: '30px'}}>
                    Check your tickets
                </Button>
                <Button variant="success" size="lg" onClick={() => navigate('/createTicket')}>
                    Open new ticket
                </Button>
            </div>
        </Container>
    )
}

export default CustomerTickets;