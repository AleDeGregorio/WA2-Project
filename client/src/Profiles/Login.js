import {Button, Col, Container, Form, Row} from "react-bootstrap";
import {useState} from "react";

function Login(props) {
    const { login, setShow, setError } = props;

    const [username, setUsername] = useState("mariorossi")
    const [password, setPassword] = useState("password")

    const handleSubmit = (e) => {
        e.preventDefault();
        const credentials = { username, password };

        let valid = true;

        if (username === '' || password === '')
            valid = false;

        if (valid) {
            login(credentials);
        }
        else {
            setShow(true);
            setError('Wrong data format. Please try again');
        }
    }

    return (
        <Container>
            <Row>
                <Col>
                    <br />
                    <h2>Login</h2>
                    <Form onSubmit={handleSubmit}>
                        <br />
                        <br />
                        <Form.Group controlId='username'>
                            <Form.Label style={{'fontWeight':'bold'}}>Username</Form.Label>
                            <Form.Control type='username' autoFocus value={username} onChange={e => setUsername(e.target.value)}/>
                        </Form.Group>
                        <br />
                        <Form.Group controlId='password'>
                            <Form.Label style={{'fontWeight':'bold'}}>Password</Form.Label>
                            <Form.Control type='password' value={password} onChange={e => setPassword(e.target.value)}/>
                        </Form.Group>
                        <br />
                        <div className="d-grid gap-2">
                            <Button variant="danger" size="lg" type='submit'>
                                Login
                            </Button>
                        </div>
                    </Form>
                </Col>
            </Row>
        </Container>
    )
}

export default Login;