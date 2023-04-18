import {useState} from "react";
import {Alert, Button, Card, Container, Form} from "react-bootstrap";
import './InsertProfile.css'
import API from "../API";
import {useNavigate} from "react-router-dom";

function InsertProfile(props) {
    const navigate = useNavigate()

    const { setError, setShow } = props;

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [name, setName] = useState("");
    const [surname, setSurname] = useState("");
    const [city, setCity] = useState("");
    const [address, setAddress] = useState("");

    const [success, setSuccess] = useState(false);

    const handleSubmit = (e) => {
        e.preventDefault();

        const profile = {
            email: email,
            password: password,
            name: name,
            surname: surname,
            city: city,
            address: address
        }

        if (
            email.length < 1 ||
            password.length < 1 ||
            name.length < 1 ||
            surname.length < 1 ||
            city.length < 1 ||
            address.length < 1
        ) {
            setError("Please, complete the missing fields");
            setShow(true);
        }

        else {
            API.insertProfile(profile)
                .then(() => {
                    setSuccess(true);
                    window.scrollTo(0, 0);

                    setTimeout(() => {
                        navigate('/profiles/' + email);
                    }, 2000);
                })
                .catch(err => { setError(err.detail); setShow(true); });
        }
    }

    return (
      <Container fluid>
          {success && <Alert variant="success">Operation completed successfully</Alert>}
          <h2>Insert your information here</h2>
          <Card>
              <Card.Body>
                  <Form onSubmit={(e) => handleSubmit(e)} className='form-profile'>
                      <div className='form-profile'>
                          <Form.Group className="mb-3" controlId="email">
                              <Form.Label>Email address</Form.Label>
                              <Form.Control type="email" placeholder="Enter email" onChange={(e) => setEmail(e.target.value)}/>
                          </Form.Group>

                          <Form.Group className="mb-3" controlId="password">
                              <Form.Label>Password</Form.Label>
                              <Form.Control type="password" placeholder="Enter password" onChange={(e) => setPassword(e.target.value)}/>
                          </Form.Group>

                          <Form.Group className="mb-3" controlId="name">
                              <Form.Label>Name</Form.Label>
                              <Form.Control type="text" placeholder="Enter name" onChange={(e) => setName(e.target.value)}/>
                          </Form.Group>

                          <Form.Group className="mb-3" controlId="surname">
                              <Form.Label>Surname</Form.Label>
                              <Form.Control type="text" placeholder="Enter surname" onChange={(e) => setSurname(e.target.value)}/>
                          </Form.Group>

                          <Form.Group className="mb-3" controlId="city">
                              <Form.Label>City</Form.Label>
                              <Form.Control type="text" placeholder="Enter city" onChange={(e) => setCity(e.target.value)}/>
                          </Form.Group>

                          <Form.Group className="mb-3" controlId="address">
                              <Form.Label>Address</Form.Label>
                              <Form.Control type="text" placeholder="Enter address" onChange={(e) => setAddress(e.target.value)}/>
                          </Form.Group>
                      </div>
                      <div className="d-grid gap-2">
                          <Button variant="danger" type="submit" size='lg'>
                              Submit
                          </Button>
                      </div>
                  </Form>
              </Card.Body>
          </Card>
      </Container>
    );
}

export default InsertProfile;