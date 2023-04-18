import {Button, Container, InputGroup, Form} from "react-bootstrap";
import {useNavigate} from "react-router-dom";
import './MainProducts.css'

function MainProducts(props) {
    const { products, setError, setShow } = props

    const navigate = useNavigate()

    const handleSubmit = (e) => {
        e.preventDefault()
        const productId = e.target[0].value

        navigate('/products/' + productId)
    }

    return (
        <Container fluid>
            <Form onSubmit={(e) => handleSubmit(e)}>
                <InputGroup size="lg" className='search-bar'>
                    <Form.Control
                                  aria-describedby="inputGroup-sizing-sm"
                                  placeholder="Insert your product id here"
                                  aria-label="productId"
                    />
                    <Button variant="success" id="button-addon2" type='submit'>
                        Search
                    </Button>
                </InputGroup>
            </Form>
            <div className="d-grid gap-2">
                <Button variant="success" size="lg"
                        onClick={() => navigate('/products', {state:{products:products, setError:setError, setShow:setShow}})}>
                    Show all products
                </Button>
            </div>
        </Container>
    )
}

export default MainProducts;