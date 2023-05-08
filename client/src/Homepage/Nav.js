import {Container, Navbar} from "react-bootstrap";
import {useNavigate} from "react-router-dom";
import {HouseDoorFill, BoxSeamFill, PersonLinesFill} from "react-bootstrap-icons";
import './Nav.css'

function Nav() {
    const navigate = useNavigate()

    return (
        <Navbar bg='dark'>
            <Container fluid>
                <div className='p-2 bd-highlight'>
                    <span className='head-text' id='icon-home' onClick={() => navigate('/')}>
                        <HouseDoorFill className='nav-icon' /> Home-page
                    </span>
                </div>
                <div className='p-2 bd-highlight'>
                    <span className='head-text' id='icon-products' onClick={() => navigate('/mainProducts')}>
                        <BoxSeamFill className='nav-icon' /> Products
                    </span>
                </div>
                <div className='p-2 bd-highlight'>
                    <span className='head-text' id='icon-profiles' onClick={() => navigate('/mainProfiles')}>
                        <PersonLinesFill className='nav-icon' /> Profiles
                    </span>
                </div>
            </Container>
        </Navbar>
    )
}

export default Nav;