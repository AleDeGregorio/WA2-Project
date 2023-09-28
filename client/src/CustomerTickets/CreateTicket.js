import {Alert, Button, Container, Form} from "react-bootstrap";

import './CreateTicket.css'
import {useContext, useState} from "react";
import LoginContext from "../Profiles/LoginContext";

function RenderProducts(props) {
    const { products } = props

    const listProducts = products.map((product) => {
        return (
            <option value={product.ean}>{product.ean} - {product.brand} {product.name}</option>
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

    const [show, setShow] = useState(false)

    /*
    const doLogin = (credentials) => {
        API.login(credentials)
            .then(user => {
                let token = jwtDecode(user.access_token)

                let role = token.resource_access["springboot-keycloak-client"].roles[0]
                let name = token.name
                let email = token.email

                user.role = role
                user.name = name
                user.email = email

                setUser(user);
                localStorage.setItem("user", JSON.stringify(user))

                setShow(false);
                setError('');
                navigate('/');
            })
            .catch(err => {
                    setShow(true);
                    setError(err.error);
                }
            )
    }
     */

    const handleSubmit = (e) => {
        e.preventDefault()

        console.log(user)

        //handle new ticket
        if(isNaN(Number(product)) || issueType.length < 1 || description.length < 1) {
            //error
            return
        }

        const currentDate = new Date()

        const year = currentDate.getFullYear()
        const month = String(currentDate.getMonth() + 1).padStart(2, '0')
        const day = String(currentDate.getDate()).padStart(2, '0')
        const hours = String(currentDate.getHours()).padStart(2, '0')
        const minutes = String(currentDate.getMinutes()).padStart(2, '0')
        const seconds = String(currentDate.getSeconds()).padStart(2, '0')
        const milliseconds = String(currentDate.getMilliseconds()).padStart(6, '0')

        const formattedDate = year + "-" + month + "-" + day + " " + hours + ":" + minutes + ":" + seconds + "." + milliseconds

        const ticket = {
            date_of_creation: formattedDate,
            description: description,
            issue_type: issueType,
            priority_level: null,
            customer_id: "cu",
            expert_id: null,
            product_ean: product
        }

        window.scrollTo(0, 0)

        setShow(true)
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