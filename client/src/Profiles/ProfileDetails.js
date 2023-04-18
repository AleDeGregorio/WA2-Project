import {Container, Table} from "react-bootstrap";
import {useNavigate, useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import API from "../API";
import {PencilFill} from "react-bootstrap-icons";

function ProfileDetails(props) {
    const navigate = useNavigate();

    const { email } = useParams();
    const { setError, setShow } = props;

    const [profileDetails, setProfileDetails] = useState([]);

    useEffect(() => {
        API.profileDetails(email)
            .then(profile => setProfileDetails(profile))
            .catch(error => {
                setError(error);
                setShow(true)
            });
    }, [profileDetails.size]);

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
                <tr key={profileDetails.email}>
                    <td style={{ 'fontWeight': 'bold' }} className="email" onClick={() => navigate("/editProfile/" + profileDetails.email)}>
                        <span style={{ 'marginRight': '10px' }}><PencilFill /></span>
                        {profileDetails.email}
                    </td>
                    <td>{profileDetails.password}</td>
                    <td>{profileDetails.name}</td>
                    <td>{profileDetails.surname}</td>
                    <td>{profileDetails.city}</td>
                    <td>{profileDetails.address}</td>
                </tr>
                </tbody>
            </Table>
        </Container>
    );
}

export default ProfileDetails;