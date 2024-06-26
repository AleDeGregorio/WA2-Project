import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';

import jwtDecode from "jwt-decode";

import ErrorAlert from "./ErrorHandling/ErrorAlert";
import API from "./API";
import Products from "./Products/Products";
import ProductDetails from "./Products/ProductDetails";

import {useEffect, useState} from "react";
import {BrowserRouter as Router, Route, Routes, useNavigate} from "react-router-dom";
import {Container} from "react-bootstrap";
import Homepage from "./Homepage/Homepage";
import Nav from "./Homepage/Nav";
import MainProducts from "./Products/MainProducts";
import MainProfiles from "./Profiles/MainProfiles";
import ProfileDetails from "./Profiles/ProfileDetails";
import InsertProfile from "./Profiles/InsertProfile";
import EditProfile from "./Profiles/EditProfile";
import PageNotFound from "./ErrorHandling/PageNotFound";
import Login from "./Profiles/Login";
import LoginContext from "./Profiles/LoginContext";
import SuccessLogout from "./Profiles/SuccessLogout";
import CustomerTickets from "./CustomerTickets/CustomerTickets";
import WrongPrivileges from "./ErrorHandling/WrongPrivileges";
import CreateTicket from "./CustomerTickets/CreateTicket";
import ExpertTickets from "./ExpertTickets/ExpertTickets";
import TicketDetails from "./ExpertTickets/TicketDetails"



function Loading() {
    return (
        <Container fluid>
            <h2>Loading data, please wait...</h2>
        </Container>
    );
}

function App() {
    return (
        <Router>
            <App2 />
        </Router>
    )
}

function App2() {
    const navigate = useNavigate()

    const [initialLoading, setInitialLoading] = useState(true);

    const [user, setUser] = useState({});

    const [products, setProducts] = useState([]);

    //error handling
    const [error, setError] = useState("");
    const [show, setShow] = useState(false);

    //logout message
    const [showLogout, setShowLogout] = useState(false)

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

    const doLogout = (token) => {
        //API.logout(token)
            //.then(() => {
                setUser({});
                localStorage.removeItem("user")

                setShow(false);
                setError('');
                setShowLogout(true)
                navigate('/')
            /*})
            .catch(err => {
                setShow(true)
                setError(err.error)
            })

             */
    }

    useEffect(() => {
        API.products()
            .then(products => setProducts(products))
            .catch(error => {
                setError(error);
                setShow(true)
            });

        const user = JSON.parse(localStorage.getItem("user"))

        if (user) {
            setUser(user)
        }

        setInitialLoading(false);
    }, []);

  return (
    <>
        <LoginContext.Provider value={user}>
            <Nav logout={doLogout} />
            <ErrorAlert show={show} setShow={setShow} error={error} />
            <SuccessLogout showLogout={showLogout} setShowLogout={setShowLogout} />
            {initialLoading ? <Loading /> : false}
            <Routes>
                <Route path='/' element={<Homepage />} />
                <Route path='login' element={<Login login={doLogin} setShow={setShow} setError={setError} />}/>
                <Route path='/mainProducts' element={<MainProducts products={products} setError={setError} setShow={setShow}/>}/>} />
                <Route path='/products' element={<Products products={products} setError={setError} setShow={setShow}/>} />
                <Route path='/products/:productId' element={<ProductDetails setError={setError} setShow={setShow}/>} />
                <Route path='/mainProfiles' element={<MainProfiles />} />
                <Route path='/profiles/:email' element={<ProfileDetails setError={setError} setShow={setShow}/>} />
                <Route path='/insertProfile' element={<InsertProfile setError={setError} setShow={setShow}/>} />
                <Route path='/editProfile/:email' element={<EditProfile setError={setError} setShow={setShow}/>} />
                <Route path='/customerTickets' element={<CustomerTickets />} />
                <Route path='/expertTickets' element={<ExpertTickets />} />
                <Route path='/createTicket' element={<CreateTicket products={products}/>} />
                <Route path='/viewTicket' element={<TicketDetails />}/>
                <Route path='/wrongPrivileges' element={<WrongPrivileges />} />
                <Route path='*' element={<PageNotFound />} />
            </Routes>
        </LoginContext.Provider>
    </>
  );
}

export default App;
