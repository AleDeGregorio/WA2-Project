import {Button, Container} from "react-bootstrap";
import {useNavigate} from "react-router-dom";
import LoginContext from "../Profiles/LoginContext";
import {useContext} from "react";

function Homepage() {
    const navigate = useNavigate()

    const user = useContext(LoginContext)
    const loggedUser = Object.keys(user).length > 0

    return (
        <Container fluid>
            <h1 style={{textAlign: 'center', padding:'20px'}}>Welcome {loggedUser ? user.name : false}</h1>
            <h2>Select your service</h2>
            <div className="d-grid gap-2" style={{padding: '30px'}}>
                <Button variant="success" size="lg"
                        style={{marginBottom: '30px'}}
                        onClick={() => navigate('/mainProducts')}
                >
                    Products
                </Button>
                {!loggedUser ?
                    <Button variant="danger" size="lg" onClick={() => navigate('/login')}>
                    Login
                    </Button> : false
                }
                {loggedUser && user.role === "customer" ?
                    <Button variant="primary" size="lg" onClick={() => navigate('/customerTickets')}>
                        Tickets
                    </Button> : false
                }
                {loggedUser && user.role === "expert" ?
                    <Button variant="primary" size="lg" onClick={() => navigate('/expertTickets')}>
                        Tickets
                    </Button> : false
                }

                {loggedUser && user.role === "manager" ?
                    <Button variant="primary" size="lg" style={{marginBottom: '30px'}} onClick={() => navigate('/managerTickets')}>
                        View ticket details
                    </Button> : false
                }
                {loggedUser && user.role === "manager" ?
                    <Button variant="primary" size="lg" style={{marginBottom: '30px'}} onClick={() => navigate('/ticketsManager')}>
                        Assign priority and expert
                    </Button> : false
                }
                {loggedUser && user.role === "manager" ?
                    <Button variant="danger" size="lg" onClick={() => navigate('/insertExpert')}>
                        Insert new Expert
                    </Button> : false
                }
            </div>
        </Container>
    )
}

export default Homepage;