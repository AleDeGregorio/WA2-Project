import React, {useContext, useState} from 'react';
import {Form, Button, Alert, Container, Card} from 'react-bootstrap';
import API from "../API";
import LoginContext from "../Profiles/LoginContext";
import {useNavigate} from "react-router-dom";

function InsertExpert(props)  {
    const user = useContext(LoginContext)

    const navigate = useNavigate()

    const { setError, setShow } = props

    const [success, setSuccess] = useState(false);

    const [formData, setFormData] = useState({
        username: '',
        firstName: '',
        lastName: '',
        password: '',
        email: '',
        fields: '',
    });

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({
            ...formData,
            [name]: value,
        });
    };

    const handleSubmit = (e) => {
        e.preventDefault();

        if (
            formData.email.length < 1 ||
            formData.password.length < 1 ||
            formData.firstName.length < 1 ||
            formData.lastName.length < 1 ||
            formData.username.length < 1 ||
            formData.fields.length < 1
        ) {
            setError("Please, complete the missing fields");
            setShow(true);
        }

        else {
            API.insertExpert(formData, user.access_token)
                .then( () => {
                    console.log("ok")

                    setSuccess(true);
                    window.scrollTo(0, 0);

                    setTimeout(() => {
                        setSuccess(false)
                    }, 3000);
                })
                .catch((error) => {
                    console.error("Error:", error);
                    setError(error);
                    setShow(true);
                });
        }
    };

    return (
        <Container fluid>
            {success && <Alert variant="success">Operation completed successfully</Alert>}
            <h2>Registration</h2>
            <Card>
                <Card.Body>
                    <Form onSubmit={(e) => handleSubmit(e)} className='form-profile'>
                        <div className='form-profile'>
                            <Form.Group className="mb-3" controlId="email">
                                <Form.Label>Email address</Form.Label>
                                <Form.Control
                                    type="email"
                                    name="email"
                                    placeholder={"Enter email"}
                                    value={formData.email}
                                    onChange={handleChange}
                                    required
                                />
                            </Form.Group>
                            <Form.Group className="mb-3" controlId="password">
                                <Form.Label>Password</Form.Label>
                                <Form.Control
                                    type="password"
                                    name="password"
                                    placeholder="Enter password"
                                    value={formData.password}
                                    onChange={handleChange}
                                    required
                                />
                            </Form.Group>
                            <Form.Group className="mb-3" controlId="username">
                                <Form.Label>Username</Form.Label>
                                <Form.Control
                                    type="text"
                                    name="username"
                                    placeholder="Enter username"
                                    value={formData.username}
                                    onChange={handleChange}
                                    required
                                />
                            </Form.Group>
                            <Form.Group className="mb-3" controlId="firstName">
                                <Form.Label>Name</Form.Label>
                                <Form.Control
                                    type="text"
                                    name="firstName"
                                    placeholder="Enter name"
                                    value={formData.firstName}
                                    onChange={handleChange}
                                    required
                                />
                            </Form.Group>
                            <Form.Group className="mb-3" controlId="lastName">
                                <Form.Label>Surname</Form.Label>
                                <Form.Control
                                    type="text"
                                    name="lastName"
                                    placeholder="Enter surname"
                                    value={formData.lastName}
                                    onChange={handleChange}
                                    required
                                />
                            </Form.Group>
                            <Form.Group className="mb-3" controlId="fields">
                                <Form.Label>Fields</Form.Label>
                                <Form.Control
                                    type="text"
                                    name="fields"
                                    placeholder="Enter fields"
                                    value={formData.fields}
                                    onChange={handleChange}
                                    required
                                />
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

export default InsertExpert;
