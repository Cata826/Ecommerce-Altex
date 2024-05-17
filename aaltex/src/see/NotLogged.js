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
           
                {products.map((product, index) => (
                    <div key={index} className="col-md-4 mb-4">
                        <div className="card shadow-sm">
                            <img src={product.imageUrl} className="card-img-top" alt={product.name} style={{ objectFit: 'cover' }} />
                            <div className="card-body">
                                <h5 className="card-title text-primary">{product.name}</h5>
                                <p className="card-text text-muted">{product.short_description}</p>
                                <p className="text-danger">${product.pret.toFixed(2)}</p>
                                <div className="d-flex justify-content-between align-items-center">
                                </div>
                            </div>
                            <div className="card-footer bg-yellow text-right">
                            </div>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
}
