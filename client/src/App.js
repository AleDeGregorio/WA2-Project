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
import ViewCustomerTickets from "./CustomerTickets/ViewCustomerTickets";
import CustomerTicketDetails from "./CustomerTickets/CustomerTicketDetails";
import ManagerTickets from "./ManagerTickets/ManagerTickets";


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
    const [loginLoading, setLoginLoading] = useState(false)

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
                const token = jwtDecode(user.access_token)

                const role = token.resource_access["springboot-keycloak-client"].roles[0]
                const name = token.name
                const email = token.email
                const id = token.sub
                const username = token.preferred_username
                const firstName = token.family_name
                const lastName = token.given_name
                const password = "password"
                const city = ""
                const address = ""

                user.role = role
                user.name = name
                user.email = email
                user.id = id
                user.username = username
                user.firstName = lastName
                user.lastName = firstName
                user.password = password
                user.city = city
                user.address = address

                setUser(user);
                localStorage.setItem("user", JSON.stringify(user))

                setShow(false);
                setError('');
                setLoginLoading(false)
                navigate('/');
            })
            .catch(err => {
                    setLoginLoading(false)
                    setShow(true);
                    setError("Incorrect username or password, please try again");

                    setTimeout(() => {
                        setShow(false)
                    }, 3000)
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

                setTimeout(() => {
                    setShowLogout(false)
                }, 3000)

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

                setTimeout(() => {
                    setShow(false)
                }, 3000)
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
                <Route path='login' element={<Login login={doLogin} setShow={setShow} setError={setError} loginLoading={loginLoading} setLoginLoading={setLoginLoading}/>}/>
                <Route path='/mainProducts' element={<MainProducts products={products} setError={setError} setShow={setShow}/>}/>} />
                <Route path='/products' element={<Products products={products} setError={setError} setShow={setShow}/>} />
                <Route path='/products/:productId' element={<ProductDetails setError={setError} setShow={setShow}/>} />
                <Route path='/mainProfiles' element={<MainProfiles />} />
                <Route path='/profiles/:email' element={<ProfileDetails setError={setError} setShow={setShow}/>} />
                <Route path='/insertProfile' element={<InsertProfile setError={setError} setShow={setShow}/>} />
                <Route path='/editProfile/:email' element={<EditProfile setError={setError} setShow={setShow} logout={doLogout}/>}/>
                <Route path='/customerTickets' element={<CustomerTickets />} />
                <Route path='/viewCustomerTickets' element={<ViewCustomerTickets setError={setError} setShow={setShow}/>} />} />
                <Route path='/customerTicketDetails' element={<CustomerTicketDetails setError={setError} setShow={setShow} />} />
                <Route path='/expertTickets' element={<ExpertTickets setError={setError} setShow={setShow} />} />
                <Route path='/managerTickets' element={<ManagerTickets />} />
                <Route path='/createTicket' element={<CreateTicket products={products}/>} />
                <Route path='/viewTicket/:id' element={<TicketDetails setError={setError} setShow={setShow} />}/>
                <Route path='/wrongPrivileges' element={<WrongPrivileges />} />
                <Route path='*' element={<PageNotFound />} />
            </Routes>
        </LoginContext.Provider>
    </>
  );
}

export default App;
