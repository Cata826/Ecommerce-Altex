
// import React, { useEffect, useState } from 'react';
// import axios from 'axios';
// import { useParams } from 'react-router-dom';
// import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
// import { useNavigate } from 'react-router-dom';
// import { faShoppingCart} from '@fortawesome/free-solid-svg-icons';
// import { FaShoppingCart } from 'react-icons/fa';
// export default function Cart() {
//     const [products, setProducts] = useState([]); 
//     const { id } = useParams(); 
//     useEffect(() => {
//         console.log('UserID from URL:', id);
//         loadProductsForOrder();
//     }, [id]);
//     const navigate = useNavigate(); // Use useNavigate hook
//     const loadProductsForOrder = async () => {
//         try {
//             const response = await axios.get(`http://localhost:8080/api/v1/cart/user/${id}/products`);
//             setProducts(response.data); // Setting the products returned from the API
//             console.log(response.data);
//         } catch (error) {
//             console.error("Error loading products for wishlist:", error);
//         }
//     };
//     const submitDetails = () => {
//         navigate(`/payment/${id}`); // Navigate to the payment page with the user id
//     };

//     const placeOrder = async () => {
//         try {
//             await axios.get(`http://localhost:8080/api/v1/cart/user/${id}/products/move`);
//             alert('Order placed successfully!');
//             loadProductsForOrder(); // Reload the products to reflect any changes after placing the order
//         } catch (error) {
//             console.error("Error placing order:", error);
//         }
//         // try {
//         //     await axios.get(`http://localhost:8080/api/v1/cart/user/${id}/products/move`);
//         //     alert('Order placed successfully!');
//         //     loadProductsForOrder(); // Reload the products to reflect any changes after placing the order
//         // } catch (error) {
//         //     console.error("Error placing order:", error);
//         // }
//     };
//     return (
//         <div className="container">
//             <h2>User's Cart Products</h2>
//             <table className="table border shadow">
//                 <thead>
//                     <tr>
//                         <th scope="col">Product ID</th>
//                         <th scope="col">Product Name</th>
//                         <th scope="col">Price</th>
//                         <th scope="col">Short Description</th>
//                         {/* ... Include other product details as necessary ... */}
//                         <th scope="col">Actions</th>
//                     </tr>
//                 </thead>
//                 <tbody>
//                     {products.map((product, index) => (
//                         <tr key={index}>
//                             <td>{product.id}</td>
//                             <td>{product.name}</td>
//                             <td>{product.short_description}</td>
//                             <td>{product.pret}</td>
//                             {/* Render other product details as necessary */}
//                             <td>
//                                 {/* Actions here, like remove from wishlist */}
//                             </td>
//                         </tr>
//                     ))}
//                 </tbody>
//             </table>
//             <button  onClick={placeOrder}style={{ backgroundColor: 'yellow', color: 'black', fontSize: '16px', padding: '10px', marginTop: '20px', border: 'none', borderRadius: '5px', cursor: 'pointer' }}>
//               Place Order
//             </button>
//             <button  onClick={submitDetails}style={{ backgroundColor: 'yellow', color: 'black', fontSize: '16px', padding: '10px', marginTop: '20px', border: 'none', borderRadius: '5px', cursor: 'pointer' }}>
//               Start completing the shipping process
//             </button>
//         </div>
//     );
// }



import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useParams, useNavigate } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faShoppingCart } from '@fortawesome/free-solid-svg-icons';
import { FaShoppingCart } from 'react-icons/fa';

export default function Cart() {
    const [products, setProducts] = useState([]);
    const { id } = useParams();
    const navigate = useNavigate();

    useEffect(() => {
        console.log('UserID from URL:', id);
        loadProductsForOrder();
    }, [id]);

    const loadProductsForOrder = async () => {
        try {
            const response = await axios.get(`http://localhost:8080/api/v1/cart/user/${id}/products`);
            setProducts(response.data);
            console.log(response.data);
        } catch (error) {
            console.error("Error loading products for wishlist:", error);
        }
    };

    const submitDetails = () => {
        navigate(`/payment/${id}`);
    };
    return (
        <div className="container-fluid" style={{ width: '100vw', height: 'auto', overflowY: 'auto' }}>
            <h2>Product that were added to the cart</h2>
            <table className="table border shadow" style={{ width: '100%' }}>
                <thead>
                    <tr>
                        {/* <th scope="col">Product ID</th> */}
                        <th scope="col">Product Name</th>
                        <th scope="col">Price</th>
                        <th scope="col">Short Description</th>
                        <th scope="col">Image</th>
                        {/* <th scope="col">Actions</th>W */}
                    </tr>
                </thead>
                <tbody>
                    {products.map((product, index) => (
                        <tr key={index}>
                            {/* <td>{product.id}</td> */}
                            <td>{product.name}</td>
                            <td>${product.pret}</td>
                            <td>{product.short_description}</td>
                            <td><img src={product.imageUrl} alt={product.name} style={{ width: '100px', height: 'auto' }} /></td>
                
                        </tr>
                    ))}
                </tbody>
            </table>
            <button
                onClick={submitDetails}
                style={{
                    backgroundColor: 'yellow',
                    color: 'black',
                    fontSize: '16px',
                    padding: '10px',
                    marginTop: '20px',
                    border: 'none',
                    borderRadius: '5px',
                    cursor: 'pointer'
                }}
            >
                Start completing the shipping process
            </button>
        </div>
    );
}    

// import React, { useEffect, useState } from 'react';
// import axios from 'axios';
// import { useParams, useNavigate } from 'react-router-dom';
// import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
// import { faShoppingCart } from '@fortawesome/free-solid-svg-icons';
// import { FaShoppingCart } from 'react-icons/fa';

// export default function Cart() {
//     const [products, setProducts] = useState([]);
//     const { id } = useParams();
//     const navigate = useNavigate();

//     useEffect(() => {
//         console.log('UserID from URL:', id);
//         loadProductsForOrder();
//     }, [id]);

//     const loadProductsForOrder = async () => {
//         try {
//             const response = await axios.get(`http://localhost:8080/api/v1/cart/user/${id}/products`);
//             setProducts(response.data);
//             console.log(response.data);
//         } catch (error) {
//             console.error("Error loading products for wishlist:", error);
//         }
//     };

//     const submitDetails = () => {
//         navigate(`/payment/${id}`);
//     };

//     return (
//         <div className="container">
//             <h2>User's Cart Products</h2>
//             <table className="table border shadow">
//                 <thead>
//                     <tr>
//                         <th scope="col">Product ID</th>
//                         <th scope="col">Product Name</th>
//                         <th scope="col">Price</th>
//                         <th scope="col">Short Description</th>
//                         <th scope="col">Actions</th>
//                     </tr>
//                 </thead>
//                 <tbody>
//                     {products.map((product, index) => (
//                         <tr key={index}>
//                             <td>{product.id}</td>
//                             <td>{product.name}</td>
//                             <td>{product.short_description}</td>
//                             <td>{product.pret}</td>
//                             <td>
//                                 {/* Actions here, like remove from wishlist */}
//                             </td>
//                         </tr>
//                     ))}
//                 </tbody>
//             </table>
//             <button
//                 onClick={submitDetails}
//                 style={{
//                     backgroundColor: 'yellow',
//                     color: 'black',
//                     fontSize: '16px',
//                     padding: '10px',
//                     marginTop: '20px',
//                     border: 'none',
//                     borderRadius: '5px',
//                     cursor: 'pointer'
//                 }}
//             >
//                 Start completing the shipping process
//             </button>
//         </div>
//     );
// }
