
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
                {/* <p className="product-long-description">{product.long_description}</p> */}
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
