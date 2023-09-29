import { Alert } from "react-bootstrap";

function ErrorAlert(props) {
    const { show, setShow, error } = props;

    let msg = error;

    if(error && error.title)
        msg = error.title;

    if(error && error.error)
        msg = error.error

    if (show) {
        window.scrollTo(0, 0);

        return (
            <Alert variant="danger" onClose={() => setShow(false)} dismissible>
                <Alert.Heading>Error</Alert.Heading>
                <p>
                    {msg}
                </p>
            </Alert>
        );
    }
}

export default ErrorAlert;