
import React, { useState } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';
import './Card.css'; // Import CSS for styling

export default function Payment() {
    const { id } = useParams();
    const [cardDetails, setCardDetails] = useState({
        fullname: '',
        number: '',
        cvv: '',
        expiration: '',
        address: '',
        city: '',
        country: '',
        zipcode: ''
    });

    const handleChange = (e) => {
        const { name, value } = e.target;
        setCardDetails({ ...cardDetails, [name]: value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post(`http://localhost:8080/api/v1/cards`, cardDetails);
            console.log('Card details submitted:', response.data);
            await axios.get(`http://localhost:8080/api/v1/cart/user/${id}/products/move`);
            alert('Order placed successfully!');
        } catch (error) {
            console.error('Error submitting card details or placing order:', error);
        }
    };


    return (
        <div className="container" style={{ maxHeight: '80vh', overflowY: 'auto' }}>
        <h2>Payment Details</h2>
            <form onSubmit={handleSubmit} className="payment-form">
                <div className="card-preview">
                    <div className="card-section">
                        <label>Full Name</label>
                        <input
                            type="text"
                            name="fullname"
                            value={cardDetails.fullname}
                            onChange={handleChange}
                            required
                        />
                    </div>
                    <div className="card-section">
                        <label>Card Number</label>
                        <input
                            type="text"
                            name="number"
                            value={cardDetails.number}
                            onChange={handleChange}
                            required
                        />
                    </div>
                    <div className="card-row">
                        <div className="card-section">
                            <label>CVV</label>
                            <input
                                type="text"
                                name="cvv"
                                value={cardDetails.cvv}
                                onChange={handleChange}
                                required
                            />
                        </div>
                        <div className="card-section">
                            <label>Expiration Date</label>
                            <input
                                type="text"
                                name="expiration"
                                value={cardDetails.expiration}
                                onChange={handleChange}
                                required
                            />
                        </div>
                    </div>
                </div>
                <div className="form-section">
                    <label>Address</label>
                    <input
                        type="text"
                        name="address"
                        value={cardDetails.address}
                        onChange={handleChange}
                        required
                    />
                </div>
                <div className="form-section">
                    <label>City</label>
                    <input
                        type="text"
                        name="city"
                        value={cardDetails.city}
                        onChange={handleChange}
                        required
                    />
                </div>
                <div className="form-section">
                    <label>Country</label>
                    <input
                        type="text"
                        name="country"
                        value={cardDetails.country}
                        onChange={handleChange}
                        required
                    />
                </div>
                <div className="form-section">
                    <label>Zip Code</label>
                    <input
                        type="text"
                        name="zipcode"
                        value={cardDetails.zipcode}
                        onChange={handleChange}
                        required
                    />
                </div>
                <button type="submit" className="submit-button">
                    Submit Payment Details and Place Order
                </button>
            </form>
        </div>
    );
}