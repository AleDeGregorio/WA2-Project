import {Button, Container, InputGroup, Form} from "react-bootstrap";
import {useNavigate} from "react-router-dom";
import './MainProfiles.css'

function MainProfiles() {
    const navigate = useNavigate()

    const handleSubmit = (e) => {
        e.preventDefault()
        const email = e.target[0].value

        navigate('/profiles/' + email)
    }

    return (
        <Container fluid>
            <Form onSubmit={(e) => handleSubmit(e)}>
                <InputGroup size="lg" className='search-bar'>
                    <Form.Control
                        aria-describedby="inputGroup-sizing-sm"
                        placeholder="Insert your email here"
                        aria-label="email"
                    />
                    <Button variant="danger" id="button-addon2" type='submit'>
                        Search
                    </Button>
                </InputGroup>
            </Form>
            <div className="d-grid gap-2">
                <Button variant="danger" size="lg"
                        onClick={() => navigate('/insertProfile')}>
                    Insert a new profile
                </Button>
            </div>
        </Container>
    )
}

export default MainProfiles;