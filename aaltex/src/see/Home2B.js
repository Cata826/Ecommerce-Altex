import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Link, useParams } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faHeart, faShop, faStar, faEye, faEdit, faComments } from '@fortawesome/free-solid-svg-icons';
import './Home2B.css'

export default function Home2B() {
    const [products, setProducts] = useState([]);
    const { userId } = useParams(); 
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

    return (
        <div className="container-fluid-2 mt-5">
            
            <div className="row">
            <Link
                to={`/chat/${id}`}
                style={{
                    position: 'absolute',
                    top: '75px',
                    right: '20px',
                    backgroundColor: 'gold',
                    color: 'white',
                    padding: '10px',
                    borderRadius: '50%',
                    fontSize: '20px',
                    border: 'none',
                    cursor: 'pointer',
                    display: 'flex',
                    alignItems: 'center',
                    justifyContent: 'center',
                    width: '50px',
                    height: '50px',
                    transition: 'background-color 0.3s ease'
                }}
                onMouseEnter={(e) => e.currentTarget.style.backgroundColor = 'darkred'}
                onMouseLeave={(e) => e.currentTarget.style.backgroundColor = 'red'}
            >
                <FontAwesomeIcon icon={faComments} />
            </Link>
            <Link
                to={`/wishlist/${id}`}
                style={{
                    position: 'absolute',
                    top: '75px',
                    right: '75px',
                    backgroundColor: 'gold',
                    color: 'white',
                    padding: '10px',
                    borderRadius: '50%',
                    fontSize: '20px',
                    border: 'none',
                    cursor: 'pointer',
                    display: 'flex',
                    alignItems: 'center',
                    justifyContent: 'center',
                    width: '50px',
                    height: '50px',
                    transition: 'background-color 0.3s ease'
                }}
                onMouseEnter={(e) => e.currentTarget.style.backgroundColor = 'darkred'}
                onMouseLeave={(e) => e.currentTarget.style.backgroundColor = 'red'}
            >
                <FontAwesomeIcon icon={faHeart} />
            </Link>
            <Link
                to={`/cart/${id}`}
                style={{
                    position: 'absolute',
                    top: '75px',
                    right: '130px',
                    backgroundColor: 'gold',
                    color: 'white',
                    padding: '10px',
                    borderRadius: '50%',
                    fontSize: '20px',
                    border: 'none',
                    cursor: 'pointer',
                    display: 'flex',
                    alignItems: 'center',
                    justifyContent: 'center',
                    width: '50px',
                    height: '50px',
                    transition: 'background-color 0.3s ease'
                }}
                onMouseEnter={(e) => e.currentTarget.style.backgroundColor = 'darkred'}
                onMouseLeave={(e) => e.currentTarget.style.backgroundColor = 'red'}
            >
                <FontAwesomeIcon icon={faShop} />
            </Link>
                {products.map((product, index) => (
                    <div key={index} className="col-md-4 mb-4">
                        <div className="card shadow-sm">
                            <img src={product.imageUrl} className="card-img-top" alt={product.name} style={{ objectFit: 'cover' }} />
                            <div className="card-body">
                                <h5 className="card-title text-primary">{product.name}</h5>
                                <p className="card-text text-muted">{product.short_description}</p>
                                <p className="text-danger">${product.pret.toFixed(2)}</p>
                                <div className="d-flex justify-content-between align-items-center">
                                    {/* <div> */}
                                        {/* <button className="btn btn-sm btn-outline-warning mr-2" onClick={() => addToCart(product.id)}>
                                            <FontAwesomeIcon icon={faShop} /> Add to Cart
                                        </button>
                                        <button className="btn btn-sm btn-outline-danger" onClick={() => addToWishlist(product.id)}>
                                            <FontAwesomeIcon icon={faHeart} /> Add to Wishlist
                                        </button> */}
                                    {/* </div> */}
                                    <small>
                                        <FontAwesomeIcon icon={faStar} className="text-warning" /> {product.stars}
                                    </small>
                                </div>
                            </div>
                            <div className="card-footer bg-yellow text-right">
                                <Link className="btn btn-sm btn-outline-dark mr-2" to={`/viewproduct/${product.id}/${id}`}>
                                    <FontAwesomeIcon icon={faEye} /> 
                                </Link>
                                {/* <button className="btn btn-sm btn-outline-danger mr-2" onClick={() => handleSeeRating(product.id)}>
                                    Rate
                                </button> */}
                                {/* <Link className="btn btn-sm btn-outline-primary mr-2" to={`/editproduct/${product.id}`}>
                                    <FontAwesomeIcon icon={faEdit} /> Edit
                                </Link> */}
                                {/* <Link className="btn btn-sm btn-outline-success" to={`/chat/${id}`}>
                                    <FontAwesomeIcon icon={faComments} /> Chat
                                </Link> */}
                                <button className="btn btn-sm btn-outline-warning mr-2" onClick={() => addToCart(product.id)}>
                                            <FontAwesomeIcon icon={faShop} /> 
                                        </button>
                                        <button className="btn btn-sm btn-outline-danger" onClick={() => addToWishlist(product.id)}>
                                            <FontAwesomeIcon icon={faHeart} /> 
                                        </button>
                            </div>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
}
