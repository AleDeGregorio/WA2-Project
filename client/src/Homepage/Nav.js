import {Container, Navbar} from "react-bootstrap";
import {useNavigate} from "react-router-dom";
import {HouseDoorFill, BoxSeamFill, PersonLinesFill} from "react-bootstrap-icons";
import './Nav.css'
import {useContext} from "react";
import LoginContext from "../Profiles/LoginContext";

function Nav(props) {
    const { logout } = props

    const navigate = useNavigate()

    const user = useContext(LoginContext)

    const doLogout = () => {
        const token = {
            refresh_token: user.refresh_token
        }

        logout(token)
    }

    return (
        <Navbar bg='dark'>
            <Container fluid>
                <div className='p-2 bd-highlight'>
                    <span className='head-text' id='icon-home' onClick={() => navigate('/')}>
                        <HouseDoorFill className='nav-icon' /> Home-page
                    </span>
                </div>
                <div className='p-2 bd-highlight'>
                    {Object.keys(user).length > 0 ?
                        <span className='head-text' id='icon-profiles' onClick={() => doLogout()}>
                            <PersonLinesFill className='nav-icon' /> Logout
                        </span> :

                        <span className='head-text' id='icon-profiles' onClick={() => navigate('/login')}>
                            <PersonLinesFill className='nav-icon' /> Login
                        </span>
                    }
                </div>
            </Container>
        </Navbar>
    )
}

export default Nav;