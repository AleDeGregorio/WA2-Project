import { Alert } from "react-bootstrap";

function SuccessLogout(props) {
    const { showLogout, setShowLogout } = props;

    if (showLogout) {
        window.scrollTo(0, 0);

        return (
            <Alert variant="success" onClose={() => setShowLogout(false)} dismissible>
                <Alert.Heading>Result</Alert.Heading>
                <p>
                    Logout performed successfully
                </p>
            </Alert>
        );
    }
}

export default SuccessLogout;