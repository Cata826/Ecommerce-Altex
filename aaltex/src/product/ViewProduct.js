// import axios from "axios";
// import React, { useEffect, useState } from "react";
// import { Link, useParams } from "react-router-dom";
// import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
// import { faStar } from '@fortawesome/free-solid-svg-icons';

// export default function ViewProduct() {
//     const [product, setProduct] = useState({
//         name: "",
//         price: "",
//         short_description: "",
//         long_description: "",
//         image: "",
//         // Add any other product fields you need to display
//     });
//     const [message, setMessage] = useState(""); // State to hold the message
//     const [rating, setRating] = useState(0);

//     const { di, id } = useParams(); // Destructuring userId and productId directly

//     useEffect(() => {
//         console.log("userId:", di); // Log userId to console
//         console.log("productId:", id); // Log productId to console
//         loadProduct();
//     }, [id]); // Adding userId and productId as dependencies to reload if the parameter changes

//     const loadProduct = async () => {
//         try {
//             const result = await axios.get(`http://localhost:8080/api/v1/products/${id}`);
//             setProduct(result.data);
//         } catch (error) {
//             console.error("Error loading product:", error);
//             // Handle error or set some error state to show an error message
//         }
//     };

//     const handleRating = (ratingValue) => {
//         setRating(ratingValue);
//     };

//     const handleReviewSubmit = async () => {
//         try {
//             const response = await axios.post(`http://localhost:8080/api/v1/reviews`, null, {
//                 params: {
//                     userId: di,
//                     productId: id,
//                     star: rating,
//                     message: message
//                 }
//             });
    
//             console.log("Review added successfully:", response.data);
//             // Optionally, you can show a success message or navigate to a different page
//         } catch (error) {
//             console.error("Error adding review:", error);
//             // Handle error or set some error state to show an error message
//         }
//     };
    

//     return (
//         <div className="product-container" style={{ padding: "20px", maxWidth: "800px", margin: "auto" }}>
//             <h2 style={{ textAlign: "center", margin: "20px 0" }}>Product Details</h2>

//             <div style={{
//                 display: "grid",
//                 gridTemplateColumns: "1fr",
//                 gap: "20px",
//                 backgroundColor: "#f9f9f9",
//                 padding: "20px",
//                 borderRadius: "8px",
//                 boxShadow: "0 4px 8px rgba(0,0,0,0.1)"
//             }}>
//                 <h3>{product.name}</h3>
             
//                 <p><b>Price:</b> ${product.pret}</p>
//                 <p><b>Short Description:</b> {product.short_description}</p>
//                 <p><b>Long Description:</b> {product.long_description}</p>
//                 <img src={'http://localhost:8080/uploads/f1.png'} alt={product.name} className="product-image mb-3" style={{ maxWidth: "100%", borderRadius: "4px" }} />

//                 {/* Add more product details here */}
//                 <Link to="/" style={{ display: "inline-block", marginTop: "20px", textDecoration: "none", backgroundColor: "#007bff", color: "#ffffff", padding: "10px 15px", borderRadius: "5px" }}>
//                     Back to Home
//                 </Link>
//             </div>

//             <div>
//                 {[...Array(5)].map((star, index) => {
//                     const ratingValue = index + 1;
//                     return (
//                         <FontAwesomeIcon
//                             key={ratingValue}
//                             className="star"
//                             icon={faStar}
//                             size="2x"
//                             color={ratingValue <= rating ? "yellow" : "gray"}
//                             onClick={() => handleRating(ratingValue)}
//                         />
//                     );
//                 })}
//             </div>

//             <div style={{ marginTop: "20px" }}>
//                 <input
//                     type="text"
//                     placeholder="Enter your review message"
//                     value={message}
//                     onChange={(e) => setMessage(e.target.value)}
//                     style={{ width: "100%", padding: "10px", borderRadius: "4px", border: "1px solid #ccc" }}
//                 />
//                 <button onClick={handleReviewSubmit} style={{ marginTop: "10px", padding: "10px 20px", borderRadius: "4px", backgroundColor: "#007bff", color: "#ffffff", border: "none", cursor: "pointer" }}>Submit Review</button>
//             </div>
//         </div>
//     );
// }

import axios from "axios";
import React, { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faStar } from '@fortawesome/free-solid-svg-icons';
import './ViewProduct.css';

export default function ViewProduct() {
    const [product, setProduct] = useState({
        name: "",
        price: "",
        short_description: "",
        long_description: "",
        image: "",
    });
    const [message, setMessage] = useState("");
    const [rating, setRating] = useState(0);
    const { id,di } = useParams();

    useEffect(() => {
        loadProduct();
    }, [id]);

    const loadProduct = async () => {
        try {
            const result = await axios.get(`http://localhost:8080/api/v1/products/${id}`);
            setProduct(result.data);
        } catch (error) {
            console.error("Error loading product:", error);
        }
    };

    const handleRating = (ratingValue) => {
        setRating(ratingValue);
    };

    const handleReviewSubmit = async () => {
        try {
            const response = await axios.post(`http://localhost:8080/api/v1/reviews`, null, {
                params: {
                    userId: di,
                    productId: id,
                    star: rating,
                    message: message
                }
            });
            console.log("Review added successfully:", response.data);
        } catch (error) {
            console.error("Error adding review:", error);
        }
    };

    return (
        <div className="product-container">
            <div className="product-image-container">
                <img src={product.imageUrl} className="product-image" alt={product.name} />
            </div>
            <div className="product-info-container">
                <h2 className="product-name">{product.name}</h2>
                <p className="product-price">Price: ${product.pret}</p>
                <p className="product-short-description">{product.short_description}</p>
                <p className="product-long-description">{product.long_description}</p>
                <div className="rating-section">
                    {[...Array(5)].map((_, index) => {
                        const ratingValue = index + 1;
                        return (
                            <FontAwesomeIcon
                                key={ratingValue}
                                className="star"
                                icon={faStar}
                                size="2x"
                                color={ratingValue <= rating ? "gold" : "gray"}
                                onClick={() => handleRating(ratingValue)}
                            />
                        );
                    })}
                </div>
                <div className="review-section">
                    <input
                        type="text"
                        placeholder="Enter your review message"
                        value={message}
                        onChange={(e) => setMessage(e.target.value)}
                        className="review-input"
                    />
                    <button onClick={handleReviewSubmit} className="submit-review-button">Submit Review</button>
                </div>
                <div className="back-to-home">
                <Link to={`/user/${di}`} className="back-to-home-link">
                        Back to shopping
                    </Link>
                </div>
            </div>
        </div>
    );
}

// import axios from "axios";
// import React, { useEffect, useState } from "react";
// import { Link, useParams } from "react-router-dom";
// import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
// import { faStar } from '@fortawesome/free-solid-svg-icons';
// import './ViewProduct.css';

// export default function ViewProduct() {
//     const [product, setProduct] = useState({
//         name: "",
//         price: "",
//         short_description: "",
//         long_description: "",
//         image: "",
//     });
//     const [message, setMessage] = useState("");
//     const [rating, setRating] = useState(0);
//     const { di, id } = useParams();

//     useEffect(() => {
//         console.log("userId:", di);
//         console.log("productId:", id);
//         loadProduct();
//     }, [id]);

//     const loadProduct = async () => {
//         try {
//             const result = await axios.get(`http://localhost:8080/api/v1/products/${id}`);
//             setProduct(result.data);
//         } catch (error) {
//             console.error("Error loading product:", error);
//         }
//     };

//     const handleRating = (ratingValue) => {
//         setRating(ratingValue);
//     };

//     const handleReviewSubmit = async () => {
//         try {
//             const response = await axios.post(`http://localhost:8080/api/v1/reviews`, null, {
//                 params: {
//                     userId: di,
//                     productId: id,
//                     star: rating,
//                     message: message
//                 }
//             });
    
//             console.log("Review added successfully:", response.data);
//         } catch (error) {
//             console.error("Error adding review:", error);
//         }
//     };

//     return (
//         <div className="product-container">
//             <div className="product-image-container">
//                 <img src={product.imageUrl} className="product-image" alt={product.name} />
//             </div>
//             <div className="product-info-container">
//                 <h2 className="product-name">{product.name}</h2>
//                 <p className="product-price">Price: ${product.price}</p>
//                 <p className="product-short-description">{product.short_description}</p>
//                 <p className="product-long-description">{product.long_description}</p>
//                 <div className="rating-section">
//                     {[...Array(5)].map((_, index) => {
//                         const ratingValue = index + 1;
//                         return (
//                             <FontAwesomeIcon
//                                 key={ratingValue}
//                                 className="star"
//                                 icon={faStar}
//                                 size="2x"
//                                 color={ratingValue <= rating ? "yellow" : "gray"}
//                                 onClick={() => handleRating(ratingValue)}
//                             />
//                         );
//                     })}
//                 </div>
//                 <div className="review-section">
//                     <input
//                         type="text"
//                         placeholder="Enter your review message"
//                         value={message}
//                         onChange={(e) => setMessage(e.target.value)}
//                         className="review-input"
//                     />
//                     <button onClick={handleReviewSubmit} className="submit-review-button">Submit Review</button>
//                 </div>
//                 <div className="back-to-home">
//                     <Link to="/" className="back-to-home-link">
//                         Back to Home
//                     </Link>
//                 </div>
//             </div>
//         </div>
//     );
// }

// import axios from "axios";
// import React, { useEffect, useState } from "react";
// import { Link, useParams } from "react-router-dom";
// import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
// import { faStar } from '@fortawesome/free-solid-svg-icons';
// import './ViewProduct.css'; // Import CSS for styling

// export default function ViewProduct() {
//     const [product, setProduct] = useState({
//         name: "",
//         price: "",
//         short_description: "",
//         long_description: "",
//         image: "",
//         // Add any other product fields you need to display
//     });
//     const [message, setMessage] = useState("");
//     const [rating, setRating] = useState(0);
//     const { di, id } = useParams();

//     useEffect(() => {
//         console.log("userId:", di);
//         console.log("productId:", id);
//         loadProduct();
//     }, [id]);

//     const loadProduct = async () => {
//         try {
//             const result = await axios.get(`http://localhost:8080/api/v1/products/${id}`);
//             setProduct(result.data);
//         } catch (error) {
//             console.error("Error loading product:", error);
//         }
//     };

//     const handleRating = (ratingValue) => {
//         setRating(ratingValue);
//     };

//     const handleReviewSubmit = async () => {
//         try {
//             const response = await axios.post(`http://localhost:8080/api/v1/reviews`, null, {
//                 params: {
//                     userId: di,
//                     productId: id,
//                     star: rating,
//                     message: message
//                 }
//             });
    
//             console.log("Review added successfully:", response.data);
//         } catch (error) {
//             console.error("Error adding review:", error);
//         }
//     };
    

//     return (
//         <div className="product-container">
//             <div className="product-details">
//                 <div className="product-image-container">
//                 <img src={product.imageUrl} className="card-img-top" alt={product.name} style={{ height: '250px', objectFit: 'cover' }} />
//                 </div>
//                 <div className="product-info">
//                     <h2 className="product-name">{product.name}</h2>
//                     <p className="product-price">Price: ${product.price}</p>
//                     <p className="product-short-description">{product.short_description}</p>
//                     <p className="product-long-description">{product.long_description}</p>
//                     <div className="rating-section">
//                         {[...Array(5)].map((star, index) => {
//                             const ratingValue = index + 1;
//                             return (
//                                 <FontAwesomeIcon
//                                     key={ratingValue}
//                                     className="star"
//                                     icon={faStar}
//                                     size="2x"
//                                     color={ratingValue <= rating ? "yellow" : "gray"}
//                                     onClick={() => handleRating(ratingValue)}
//                                 />
//                             );
//                         })}
//                     </div>
//                     <div className="review-section">
//                         <input
//                             type="text"
//                             placeholder="Enter your review message"
//                             value={message}
//                             onChange={(e) => setMessage(e.target.value)}
//                             className="review-input"
//                         />
//                         <button onClick={handleReviewSubmit} className="submit-review-button">Submit Review</button>
//                     </div>
//                 </div>
//             </div>

//             <div className="back-to-home">
//                 <Link to="/" className="back-to-home-link">
//                     Back to Home
//                 </Link>
//             </div>
//         </div>
//     );
// }
