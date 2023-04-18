import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';

import ErrorAlert from "./ErrorHandling/ErrorAlert";
import API from "./API";
import Products from "./Products/Products";
import ProductDetails from "./Products/ProductDetails";

import {useEffect, useState} from "react";
import {BrowserRouter as Router, Route, Routes} from "react-router-dom";
import {Container} from "react-bootstrap";
import Homepage from "./Homepage/Homepage";
import Nav from "./Homepage/Nav";
import MainProducts from "./Products/MainProducts";
import MainProfiles from "./Profiles/MainProfiles";
import ProfileDetails from "./Profiles/ProfileDetails";
import InsertProfile from "./Profiles/InsertProfile";
import EditProfile from "./Profiles/EditProfile";
import PageNotFound from "./ErrorHandling/PageNotFound";

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
    const [initialLoading, setInitialLoading] = useState(true);
    const [products, setProducts] = useState([]);

    //error handling
    const [error, setError] = useState("");
    const [show, setShow] = useState(false);

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
        <Nav />
        <ErrorAlert show={show} setShow={setShow} error={error} />
        {initialLoading ? <Loading /> : false}
      <Routes>
          <Route path='/' element={<Homepage />} />
          <Route path='/mainProducts' element={<MainProducts products={products} setError={setError} setShow={setShow}/>}/>} />
          <Route path='/products' element={<Products products={products} setError={setError} setShow={setShow}/>} />
          <Route path='/products/:productId' element={<ProductDetails setError={setError} setShow={setShow}/>} />
          <Route path='/mainProfiles' element={<MainProfiles />} />
          <Route path='/profiles/:email' element={<ProfileDetails setError={setError} setShow={setShow}/>} />
          <Route path='/insertProfile' element={<InsertProfile setError={setError} setShow={setShow}/>} />
          <Route path='/editProfile/:email' element={<EditProfile setError={setError} setShow={setShow}/>} />
          <Route path='*' element={<PageNotFound />} />
      </Routes>
    </>
  );
}

export default App;
