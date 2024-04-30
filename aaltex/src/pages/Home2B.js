// import React, { useEffect, useState } from 'react';
// import axios from 'axios';
// import { Link, useParams } from 'react-router-dom';
// import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
// import { faHeart,faShop, faStar } from '@fortawesome/free-solid-svg-icons';
// export default function Home2B() {
//     const [products, setProducts] = useState([]);
//     const { userId } = useParams(); // 'id' is the userId obtained from the URL
// //     const { id } = useParams();

// //   useEffect(() => {
// //     // Log the ID to the console
// //     console.log(id);
// //   }, [id]); // The useEffect hook will run whenever the `id` changes

//   const { id } = useParams();

//   useEffect(() => {
//     console.log('UserID from URL:', id);
//     loadProducts();
//   }, [id]);


//     const loadProducts = async () => {
//       const result = await axios.get("http://localhost:8080/api/v1/products");
//       setProducts(result.data);
//     };
//     const addToWishlist = async (productId) => {
//         try {
//             const response = await axios.post(`http://localhost:8080/api/v1/wishlist?userId=${id}&productId=${productId}`);
//             console.log(response.data);
//             // Handle the successful response here
//         } catch (error) {
//             // Handle errors here
//             console.log(error);
//         }
//     };
//     const addToCart = async (productId) => {
//         try {
//             const response = await axios.post(`http://localhost:8080/api/v1/cart?userId=${id}&productId=${productId}`);
//             console.log(response.data);
//             // Handle the successful response here
//         } catch (error) {
//             // Handle errors here
//             console.log(error);
//         }
//     };
//     // const addReview = async (productId) => {
//     //     try {
//     //         const response = await axios.post(`http://localhost:8080/api/v1/review?userId=${id}&productId=${productId}`);
//     //         console.log(response.data);
//     //         // Handle the successful response here
//     //     } catch (error) {
//     //         // Handle errors here
//     //         console.log(error);
//     //     }
//     // };
//     const fetchAverageRating = async (productId) => {
//         try {
//             const response = await axios.get(`http://localhost:8080/api/v1/reviews/average/${productId}`);
//             return response.data;
//         } catch (error) {
//             console.error("Error fetching average rating:", error);
//             return "N/A";
//         }
//     };
    

//     return (
//         <div className="container">
//             <div className="py-4">
//             <div className="d-flex justify-content-between align-items-center mb-3">
//                     <h2>Product List</h2>
//                     <Link to={`/order/${id}`}>
//                         <FontAwesomeIcon icon={faShop} size="2x" color="yellow" />
//                     </Link>
//                     <Link to={`/wishlist/${id}`}>
//                         <FontAwesomeIcon icon={faHeart} size="2x" color="yellow" />
//                     </Link>
//                 </div>
//                 <table className="table border shadow">
//                     <thead>
//                         <tr>
//                             <th scope="col">Id</th>
//                             <th scope="col">Name</th>
//                             <th scope="col">Pret description</th>
//                             <th scope="col">Short description</th>
//                             {/* <th scope="col">Long description</th> */}
//                             {/* <th scope="col">Quantity description</th> */}
//                             <th scope="col">Actions</th>
//                         </tr>
//                     </thead>
//                     <tbody>
//                         {products.map((product, index) => (
//                             <tr key={index}>
//                                 <td>{product.id}</td>
//                                 <td>{product.name}</td>
//                                 <td>{product.pret}</td>
//                                 <td>{product.short_description}</td>
//                                 {/* <td>
//                                     {average(product.id)}
//                                 </td> */}
//                                 {/* <td>{product.long_description}</td> */}
//                                 {/* <td>{product.quantity}</td> */}
//                                 <td>
//                                 <Link className="btn btn-outline-danger mx-2" onClick={() => addToCart(product.id)}>
//                                         Cart
//                                     </Link>
//                                     <Link className="btn btn-outline-danger mx-2" onClick={() => addToWishlist(product.id)}>
//                                         Wishlist
//                                     </Link>
//                                     <Link className="btn btn-outline-danger mx-2" to={`/viewproduct/${product.id}/${id}`}>
//                                         View
//                                     </Link>
//                                     <Link className="btn btn-outline-danger mx-2" to={`/editproduct/${product.id}`}>
//                                         Edit
//                                     </Link>
//                                     <Link className="btn btn-outline-danger mx-2" to={`/editproduct/${product.id}`}>
//                                         See Ratijng
//                                     </Link>
                                    
//                                 </td>
//                                 {/* <td>
//                                     {fetchAverageRating(product.id) !== null ? (
//                                         fetchAverageRating(product.id)
//                                     ) : (
//                                         "N/A"
//                                     )}
//                                 </td> */}
//                             </tr>
//                         ))}
//                     </tbody>
//                 </table>
//             </div>
//         </div>
//     );
// }
import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Link, useParams } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faHeart, faShop, faStar,faEye,faEdit,faComments } from '@fortawesome/free-solid-svg-icons';
import './Home2B.css'; 
export default function Home2B() {
    const [products, setProducts] = useState([]);
    const { userId } = useParams(); // 'id' is the userId obtained from the URL
    const { id } = useParams();

    useEffect(() => {
        console.log('UserID from URL:', id);
        loadProducts();
    }, [id]);

    const loadProducts = async () => {
        const result = await axios.get("http://localhost:8080/api/v1/products");
        setProducts(result.data);
    };

    const addToWishlist = async (productId) => {
        try {
            const response = await axios.post(`http://localhost:8080/api/v1/wishlist?userId=${id}&productId=${productId}`);
            console.log(response.data);
        } catch (error) {
            console.log(error);
        }
    };

    const addToCart = async (productId) => {
        try {
            const response = await axios.post(`http://localhost:8080/api/v1/cart?userId=${id}&productId=${productId}`);
            console.log(response.data);
        } catch (error) {
            console.log(error);
        }
    };

    const fetchAverageRating = async (productId) => {
        try {
            const response = await axios.get(`http://localhost:8080/api/v1/reviews/average/${productId}`);
            return response.data;
        } catch (error) {
            console.error("Error fetching average rating:", error);
            return "N/A";
        }
    };

    const handleSeeRating = async (productId) => {
        const averageRating = await fetchAverageRating(productId);
        alert(`Average Rating for Product ID ${productId}: ${averageRating}`);
    };

    // return (
    //     <div className="container">
    //         <div className="py-4">
    //             <div className="d-flex justify-content-between align-items-center mb-3">
    //                 <h2>Product List</h2>
    //                 <Link to={`/order/${id}`}>
    //                     <FontAwesomeIcon icon={faShop} size="2x" color="yellow" />
    //                 </Link>
    //                 <Link to={`/wishlist/${id}`}>
    //                     <FontAwesomeIcon icon={faHeart} size="2x" color="yellow" />
    //                 </Link>
    //             </div>
    //             <table className="table border shadow">
    //                 <thead>
    //                     <tr>
    //                         <th scope="col">Id</th>
    //                         <th scope="col">Name</th>
    //                         <th scope="col">Pret description</th>
    //                         <th scope="col">Short description</th>
    //                         <th scope="col">Photo</th>
    //                         <th scope="col">Actions</th>
    //                     </tr>
    //                 </thead>
    //                 <tbody>
    //                     {products.map((product, index) => (
    //                         <tr key={index}>
    //                             <td>{product.id}</td>
    //                             <td>{product.name}</td>
    //                             <td>{product.pret}</td>
    //                             <td>{product.short_description}</td>
    //                             <td>
    //                                 <img src={product.image} alt={product.name} className="product-image mb-3" />
    //                             </td>
    //                             <td>
    //                                 <Link className="btn btn-outline-danger mx-2" onClick={() => addToCart(product.id)}>
    //                                     Cart
    //                                 </Link>
                         
    //                                 <Link className="btn btn-outline-danger mx-2" onClick={() => addToWishlist(product.id)}>
    //                                     Wishlist
    //                                 </Link>
    //                                 <Link className="btn btn-outline-danger mx-2" to={`/viewproduct/${product.id}/${id}`}>
    //                                     View
    //                                 </Link>
    //                                 <Link className="btn btn-outline-danger mx-2" to={`/editproduct/${product.id}`}>
    //                                     Edit
    //                                 </Link>
    //                                 <button className="btn btn-outline-danger mx-2" onClick={() => handleSeeRating(product.id)}>
    //                                     See Rating
    //                                 </button>
    //                                 <Link className="btn btn-outline-danger mx-2" to={`/chat/${id}`}>
    //                                     Chating
    //                                 </Link>
    //                             </td>
    //                         </tr>
    //                     ))}
    //                 </tbody>
    //             </table>
    //         </div>
    //     </div>
    // );
    return (
        <div className="container mt-5">
            <div className="row">
                {products.map((product, index) => (
                    <div key={index} className="col-md-4 mb-4">
                        <div className="card h-100 shadow-sm">
                        <img src={product.imageUrl} className="card-img-top" alt={product.name} style={{ height: '250px', objectFit: 'cover' }} />
                            <div className="card-body">
                                <h5 className="card-title">{product.name}</h5>
                                <p className="card-text">{product.short_description}</p>
                                <p className="text-muted">${product.pret.toFixed(2)}</p>
                                <div className="d-flex justify-content-between align-items-center">
                                    <div>
                                        <button className="btn btn-sm btn-outline-primary" onClick={() => addToCart(product.id)}>
                                            <FontAwesomeIcon icon={faShop} /> Cart
                                        </button>
                                        <button className="btn btn-sm btn-outline-danger" onClick={() => addToWishlist(product.id)}>
                                            <FontAwesomeIcon icon={faHeart} /> Wishlist
                                        </button>
                                    </div>
                                    <small>
                                        <FontAwesomeIcon icon={faStar} className="text-warning" /> {product.stars}
                                    </small>
                                </div>
                            </div>
                            <div className="card-footer">
                                <Link className="btn btn-sm btn-outline-secondary" to={`/viewproduct/${product.id}`}>
                                    <FontAwesomeIcon icon={faEye} /> View
                                </Link>
                                <button className="btn btn-sm btn-outline-info" onClick={() => handleSeeRating(product.id)}>
                                    Rate
                                </button>
                                <Link className="btn btn-sm btn-outline-primary" to={`/editproduct/${product.id}`}>
                                    <FontAwesomeIcon icon={faEdit} /> Edit
                                </Link>
                                <Link className="btn btn-sm btn-outline-success" to={`/chat/${id}`}>
                                    <FontAwesomeIcon icon={faComments} /> Chat
                                </Link>
                            </div>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
}
