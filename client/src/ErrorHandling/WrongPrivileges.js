import {Container} from "react-bootstrap";

function WrongPrivileges() {
    return (
        <Container fluid>
            <h1>Error, you don't have the right privilege</h1>
            <h3>Please, select another page to continue</h3>
        </Container>
    )
}

export default WrongPrivileges;