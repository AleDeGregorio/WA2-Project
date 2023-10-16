import {Alert, Button, Container, Form} from "react-bootstrap";

import './CreateTicket.css'
import {useContext, useState} from "react";
import LoginContext from "../Profiles/LoginContext";
import API from "../API";
import ErrorAlert from "../ErrorHandling/ErrorAlert";

function RenderProducts(props) {
    const { products } = props

    const listProducts = products.map((product) => {
        return (
            <option key={product.ean} value={product.ean}>{product.ean} - {product.brand} {product.name}</option>
        )
    })

    return (<>{listProducts}</>)
}

function CreateTicket(props) {
    const user = useContext(LoginContext)

    const { products } = props

    const [product, setProduct] = useState("")
    const [issueType, setIssueType] = useState("")
    const [description, setDescription] = useState("")

    //success handling
    const [show, setShow] = useState(false)

    //error handling
    const [error, setError] = useState("");
    const [showError, setShowError] = useState(false);

    const handleSubmit = (e) => {
        e.preventDefault()

        if(!product || issueType.length < 1 || description.length < 1) {
            window.scrollTo(0, 0)

            setError("Please, complete all the fields before submitting")
            setShowError(true)

            setTimeout(() => {
                setShowError(false)
            }, 3000)

            return
        }

        const customer = {
            id: user.id,
            username: user.username,
            firstName: user.firstName,
            lastName: user.lastName,
            password: user.password,
            email: user.email,
            city: user.city,
            address: user.address
        }

        const ticket = {
            dateOfCreation: new Date(),
            description: description,
            issueType: issueType,
            priorityLevel: null,
            customer: customer,
            expert: null,
            product: products.filter(it => it.ean == product)[0]
        }

        API.insertTicket(ticket, user.access_token)
            .then(() => {
                window.scrollTo(0, 0)

                setShow(true)

                setTimeout(() => {
                    setShow(false)
                }, 3000)
            })
            .catch(err => {
                window.scrollTo(0, 0)

                setError(err)
                setShowError(true)

                setTimeout(() => {
                    setShowError(false)
                }, 3000)
            })
    }

    return (
        <>
            {show ?
                <Alert variant="success" onClose={() => setShow(false)} dismissible>
                    <Alert.Heading>Result</Alert.Heading>
                    <p>
                        Ticket submitted successfully
                    </p>
                </Alert>
                : false
            }

            <ErrorAlert show={showError} setShow={setShowError} error={error} />

            <h1 className='createTicketTitle'>Enter the details of your ticket</h1>
            <Container fluid className='createTicketContainer'>
                <Form onSubmit={e => handleSubmit(e)}>
                    <Form.Group className="mb-3" controlId="product">
                        <Form.Label style={{fontWeight: 'bold'}}>Product</Form.Label>
                        <Form.Select aria-label="Select your product" onChange={(event) => setProduct(event.target.value)}>
                            <option>Select your product</option>
                            <RenderProducts products={products}/>
                        </Form.Select>
                    </Form.Group>

                    <Form.Group className="mb-3" controlId="issueType">
                        <Form.Label style={{fontWeight: 'bold'}}>Issue type</Form.Label>
                        <Form.Control type="text" placeholder="Briefly describe your problem" onChange={(event) => setIssueType(event.target.value)}/>
                    </Form.Group>

                    <Form.Group className="mb-3" controlId="description">
                        <Form.Label style={{fontWeight: 'bold'}}>Description</Form.Label>
                        <Form.Control as="textarea" rows={3} placeholder="Insert details here" onChange={(event) => setDescription(event.target.value)}/>
                    </Form.Group>

                    <div className="d-grid gap-2" style={{padding: '30px'}}>
                        <Button variant="danger" type="submit" size="lg" style={{marginBottom: '30px'}}>
                            Submit your ticket
                        </Button>
                    </div>
                </Form>
            </Container>
        </>
    );
}

export default CreateTicket