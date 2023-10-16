import {Container, Table} from "react-bootstrap";
import {useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import API from "../API";

function ProductDetails(props) {
    const { productId } = useParams()
    const { setError, setShow } = props

    const [productDetails, setProductDetails] = useState([])

    useEffect(() => {
        API.productDetails(productId)
            .then(product => setProductDetails(product))
            .catch(error => {
                setError(error);
                setShow(true)

                setTimeout(() => {
                    setShow(false)
                }, 3000)
            });
    }, [productDetails.size])

    return (
        <Container fluid>
            <h1>Product details</h1>
            <Table bordered hover>
                <thead>
                <tr style={{backgroundColor: "#198754", color: 'white'}}>
                    <th>Product id</th>
                    <th>Product name</th>
                    <th>Brand</th>
                    <th>Category</th>
                    <th>Price</th>
                </tr>
                </thead>
                <tbody>
                <tr key={productDetails.ean}>
                    <td style={{ 'fontWeight': 'bold' }} className="productId">{productDetails.ean}</td>
                    <td>{productDetails.name}</td>
                    <td>{productDetails.brand}</td>
                    <td>{productDetails.category}</td>
                    <td>€{productDetails.price}</td>
                </tr>
                </tbody>
            </Table>
        </Container>
    );
}

export default ProductDetails;