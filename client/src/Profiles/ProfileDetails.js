import {Container, Table} from "react-bootstrap";
import {useNavigate, useParams} from "react-router-dom";
import {useContext, useEffect, useState} from "react";
import API from "../API";
import {PencilFill} from "react-bootstrap-icons";
import LoginContext from "./LoginContext";

function ProfileDetails(props) {
    const navigate = useNavigate();

    const user = useContext(LoginContext)

    const { setError, setShow } = props;

    const [profileDetails, setProfileDetails] = useState([]);

    useEffect(() => {
        API.profileDetails(user.email, user.access_token)
            .then(profile => setProfileDetails(profile))
            .catch(error => {
                setError(error)
                setShow(true)

                setTimeout(() => {
                    setShow(false)
                }, 3000)
            });
    }, []);

    return (
        <Container fluid>
            <h1>Profile details</h1>
            <Table bordered hover>
                <thead>
                <tr style={{backgroundColor: "#bb2d3b", color: 'white'}}>
                    <th>Email</th>
                    <th>Password</th>
                    <th>Name</th>
                    <th>Surname</th>
                    <th>City</th>
                    <th>Address</th>
                </tr>
                </thead>
                <tbody>
                <tr key={user.email}>
                    <td style={{ 'fontWeight': 'bold' }} className="email"
                        onClick={() => navigate("/editProfile/" + user.email, { state: {profileDetails, setError, setShow }})}>
                        <span style={{ 'marginRight': '10px' }}><PencilFill /></span>
                        {user.email}
                    </td>
                    <td>{user.password}</td>
                    <td>{user.firstName}</td>
                    <td>{user.lastName}</td>
                    <td>{profileDetails.city}</td>
                    <td>{profileDetails.address}</td>
                </tr>
                </tbody>
            </Table>
        </Container>
    );
}

export default ProfileDetails;