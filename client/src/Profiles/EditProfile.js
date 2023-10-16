import {useContext, useEffect, useState} from "react";
import {Alert, Button, Card, Container, Form} from "react-bootstrap";
import './InsertProfile.css'
import API from "../API";
import {useNavigate, useParams} from "react-router-dom";
import LoginContext from "./LoginContext";

function EditProfile(props) {
    const navigate = useNavigate()

    const user = useContext(LoginContext)
    const { email } = useParams()

    const { setError, setShow, logout } = props;

    const [loading, setLoading] = useState(true)
    const [loadContext, setLoadContext] = useState(true)

    const [name, setName] = useState("");
    const [surname, setSurname] = useState("");
    const [city, setCity] = useState("");
    const [address, setAddress] = useState("");

    const [success, setSuccess] = useState(false);

    useEffect(() => {
        if(loadContext && user) {
            setLoadContext(false)
        }
        else if(email === user.email) {
            API.profileDetails(user.email, user.access_token)
                .then(profile => {
                    setName(user.firstName)
                    setSurname(user.lastName)
                    setCity(profile.city)
                    setAddress(profile.address)

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
            navigate("/wrongPrivileges")
        }
    }, [loadContext])

    const handleSubmit = (e) => {
        e.preventDefault();

        const customer = {
            id: user.id,
            username: user.username,
            email: user.email,
            password: user.password,
            firstName: name,
            lastName: surname,
            city: city,
            address: address
        }

        if (
            name.length < 1 ||
            surname.length < 1 ||
            city.length < 1 ||
            address.length < 1
        ) {
            setError("Please, complete the missing fields");
            setShow(true);
        }

        else {
            API.editProfile(customer, user.access_token)
                .then(() => {
                    setSuccess(true);
                    window.scrollTo(0, 0);

                    setTimeout(() => {
                        setSuccess(false)
                        logout(user.access_token)
                        navigate("/login")
                    }, 3000);
                })
                .catch(err => { setError(err); setShow(true); });
        }
    }

    return (
        <Container fluid>
            {success && <Alert variant="success">Operation completed successfully</Alert>}
            <h2>Insert your information here</h2>
            <Card>
                <Card.Body>
                    {
                        loading ?

                            <div>
                                <br />
                                <div className="spinner-border" role="status"></div>
                            </div> :

                            <Form onSubmit={(e) => handleSubmit(e)} className='form-profile'>
                                <div className='form-profile'>
                                    <Form.Group className="mb-3" controlId="email">
                                        <Form.Label>Email address</Form.Label>
                                        <Form.Control type="email" value={user.email}/>
                                    </Form.Group>

                                    <Form.Group className="mb-3" controlId="name">
                                        <Form.Label>Name</Form.Label>
                                        <Form.Control type="text" placeholder={name} onChange={(e) => setName(e.target.value)}/>
                                    </Form.Group>

                                    <Form.Group className="mb-3" controlId="surname">
                                        <Form.Label>Surname</Form.Label>
                                        <Form.Control type="text" placeholder={surname} onChange={(e) => setSurname(e.target.value)}/>
                                    </Form.Group>

                                    <Form.Group className="mb-3" controlId="city">
                                        <Form.Label>City</Form.Label>
                                        <Form.Control type="text" placeholder={city} onChange={(e) => setCity(e.target.value)}/>
                                    </Form.Group>

                                    <Form.Group className="mb-3" controlId="address">
                                        <Form.Label>Address</Form.Label>
                                        <Form.Control type="text" placeholder={address} onChange={(e) => setAddress(e.target.value)}/>
                                    </Form.Group>
                                </div>
                                <div className="d-grid gap-2">
                                    <Button variant="danger" type="submit" size='lg'>
                                        Submit
                                    </Button>
                                </div>
                            </Form>
                    }
                </Card.Body>
            </Card>
        </Container>
    );
}

export default EditProfile;