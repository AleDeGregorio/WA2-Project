import {Container, Table} from "react-bootstrap";
import {useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import API from "../API";

function TicketDetails() {

       // const { productId } = useParams()
        //const { setError, setShow } = props

        //const [productDetails, setProductDetails] = useState([])
        const ticket = {id : 1, customer: 1, expert: 1, product: 1, status:"opened", issueType: "phone", description: "bad phone", priorityLevel: 2, dateOfCreation: "11-07-2023"  }
/*
useEffect(() => {
            API.productDetails(productId)
                .then(product => setProductDetails(product))
                .catch(error => {
                    setError(error);
                    setShow(true)
                });
        }, [productDetails.size])
*/
        return (
        <Container fluid style = {{display: "flex", flexDirection: "column", alignItems : "center"}}>
            <h1>Ticket details</h1>
            <Table style={{
                border: '2px solid grey',
                borderRadius: '10px', // Arrotondamento degli angoli
                borderCollapse: 'collapse', // Per evitare spazi tra le celle
                width: '80%', // Per allineare la tabella al 100% della larghezza del contenitore
            }}>
                <tbody>
                <tr>
                    <th>Ticket id</th>
                    <td>{ticket.id}</td>
                </tr>
                <tr>
                    <th>Customer Id</th>
                    <td>{ticket.customer}</td>
                </tr>
                <tr>
                    <th>Expert Id</th>
                    <td>{ticket.expert}</td>
                </tr>
                <tr>
                    <th>Product Id</th>
                    <td>{ticket.product}</td>
                </tr>
                <tr>
                    <th>Status</th>
                    <td>{ticket.status}</td>
                </tr>
                <tr>
                    <th>Issue Type</th>
                    <td>{ticket.issueType}</td>
                </tr>
                <tr>
                    <th>Description</th>
                    <td>{ticket.description}</td>
                </tr>
                <tr>
                    <th>Priority Level</th>
                    <td>{ticket.priorityLevel}</td>
                </tr>
                <tr>
                    <th>Date Of Creation</th>
                    <td>{ticket.dateOfCreation}</td>
                </tr>
                </tbody>
            </Table>
        </Container>

        );

}/*
return (
    <div className="App">
      <div className='table-container'>
      <Table  className={classes.table} >
          <TableRow  className='row-style'>
              <TableCell variant="head">Full Name</TableCell>
              <TableCell>Cell 1</TableCell>
              <TableCell>Cell 2</TableCell>
          </TableRow>
          <TableRow  className='row-style'>
              <TableCell variant="head">Email</TableCell>
              <TableCell>Cell 1</TableCell>
              <TableCell>Cell 2</TableCell>
          </TableRow>
      </Table>
      </div>
    </div>
  );
*/
export default TicketDetails;