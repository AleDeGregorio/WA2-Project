import {Container, Table} from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import "./Products.css"

function RenderList(props) {
    const navigate = useNavigate()

    const { products, setError, setShow } = props

    const listProducts = products.map((product) => {
        return (
            <tr key={product.productId}>
                <td style={{ 'fontWeight': 'bold' }}
                    className="productId"
                    onClick={() => navigate('/products/' + product.productId, { state: {setError: setError, setShow: setShow} })}>
                    {product.productId}
                </td>
                <td>{product.name}</td>
                <td>{product.brand}</td>
                <td>{product.category}</td>
                <td>€{product.price}</td>
            </tr>
        );
    });

    return (<>{listProducts}</>);
}

function Products(props) {
    const { products, setError, setShow } = props

    return (
        <Container fluid>
            <h1>List of products</h1>
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
                    <RenderList products={products} setError={setError} setShow={setShow}/>
                    </tbody>
                </Table>
        </Container>
    );
}

export default Products;