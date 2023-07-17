import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';

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
                setUser(user);
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
                <Route path='*' element={<PageNotFound />} />
            </Routes>
        </LoginContext.Provider>
    </>
  );
}

export default App;
