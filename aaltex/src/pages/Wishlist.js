import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';

export default function Wishlist() {
    const [products, setProducts] = useState([]); 
    const { id } = useParams(); 
    useEffect(() => {
        console.log('UserID from URL:', id);
        loadProductsForWishlist();
    }, [id]);
  
    const loadProductsForWishlist = async () => {
        try {
            const response = await axios.get(`http://localhost:8080/api/v1/wishlist/user/${id}/products`);
            setProducts(response.data);
            console.log(response.data);
        } catch (error) {
            console.error("Error loading products for wishlist:", error);
        }
    };

    return (
        <div className="container">
            <h2>User's Wishlist Products</h2>
            <table className="table border shadow">
                <thead>
                    <tr>
                        <th scope="col">Product ID</th>
                        <th scope="col">Product Name</th>
                        <th scope="col">Price</th>
                        <th scope="col">Short Description</th>
                        {/* ... Include other product details as necessary ... */}
                        <th scope="col">Actions</th>
                    </tr>
                </thead>
                <tbody>
                    {products.map((product, index) => (
                        <tr key={index}>
                            <td>{product.id}</td>
                            <td>{product.name}</td>
                            <td>{product.short_description}</td>
                            <td>{product.pret}</td>
                           
                      
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
}
