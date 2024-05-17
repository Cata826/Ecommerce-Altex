
// import React, { useState } from 'react';
// import axios from 'axios';
// import { useParams ,useNavigate } from 'react-router-dom';
// import './Card.css'; // Import CSS for styling

// export default function Payment() {
//     const { id } = useParams();
//     const [cardDetails, setCardDetails] = useState({
//         fullname: '',
//         number: '',
//         cvv: '',
//         expiration: '',
//         address: '',
//         city: '',
//         country: '',
//         zipcode: ''
//     });
//     const navigate = useNavigate();
//     const handleChange = (e) => {
//         const { name, value } = e.target;
//         setCardDetails({ ...cardDetails, [name]: value });
//     };

//     const handleSubmit = async (e) => {
//         e.preventDefault();
//         try {
//             const response = await axios.post(`http://localhost:8080/api/v1/cards`, cardDetails);
//             console.log('Card details submitted:', response.data);
//             navigate(`/user/${id}`);
//             await axios.get(`http://localhost:8080/api/v1/cart/user/${id}/products/move`);
//             alert('Order placed successfully!');
//         } catch (error) {
//             console.error('Error submitting card details or placing order:', error);
//         }
//     };


//     return (
//         <div className="container" style={{ maxHeight: '80vh', overflowY: 'auto' }}>
//         <h2>Payment Details</h2>
//             <form onSubmit={handleSubmit} className="payment-form">
//                 <div className="card-preview">
//                     <div className="card-section">
//                         <label>Full Name</label>
//                         <input
//                             type="text"
//                             name="fullname"
//                             value={cardDetails.fullname}
//                             onChange={handleChange}
//                             required
//                         />
//                     </div>
//                     <div className="card-section">
//                         <label>Card Number</label>
//                         <input
//                             type="text"
//                             name="number"
//                             value={cardDetails.number}
//                             onChange={handleChange}
//                             required
//                         />
//                     </div>
//                     <div className="card-row">
//                         <div className="card-section">
//                             <label>CVV</label>
//                             <input
//                                 type="text"
//                                 name="cvv"
//                                 value={cardDetails.cvv}
//                                 onChange={handleChange}
//                                 required
//                             />
//                         </div>
//                         <div className="card-section">
//                             <label>Expiration Date</label>
//                             <input
//                                 type="text"
//                                 name="expiration"
//                                 value={cardDetails.expiration}
//                                 onChange={handleChange}
//                                 required
//                             />
//                         </div>
//                     </div>
//                 </div>
//                 <div className="form-section">
//                     <label>Address</label>
//                     <input
//                         type="text"
//                         name="address"
//                         value={cardDetails.address}
//                         onChange={handleChange}
//                         required
//                     />
//                 </div>
//                 <div className="form-section">
//                     <label>City</label>
//                     <input
//                         type="text"
//                         name="city"
//                         value={cardDetails.city}
//                         onChange={handleChange}
//                         required
//                     />
//                 </div>
//                 <div className="form-section">
//                     <label>Country</label>
//                     <input
//                         type="text"
//                         name="country"
//                         value={cardDetails.country}
//                         onChange={handleChange}
//                         required
//                     />
//                 </div>
//                 <div className="form-section">
//                     <label>Zip Code</label>
//                     <input
//                         type="text"
//                         name="zipcode"
//                         value={cardDetails.zipcode}
//                         onChange={handleChange}
//                         required
//                     />
//                 </div>
//                 <button type="submit" className="submit-button">
//                     Submit Payment Details and Place Order
//                 </button>
//             </form>
//         </div>
//     );
// }

import React, { useState } from 'react';
import axios from 'axios';
import { useParams, useNavigate } from 'react-router-dom';
import './Card.css'; // Import CSS for styling

export default function Payment() {
    const { id } = useParams();
    const navigate = useNavigate();
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

    const [errors, setErrors] = useState({
        cvv: '',
        number: '',
        expiration: ''
    });

    const handleChange = (e) => {
        const { name, value } = e.target;
        setCardDetails({ ...cardDetails, [name]: value });

        // Validation logic
        if (name === 'cvv' && !/^\d{3}$/.test(value)) {
            setErrors((prevErrors) => ({ ...prevErrors, cvv: 'CVV must be exactly 3 digits' }));
        } else if (name === 'cvv') {
            setErrors((prevErrors) => ({ ...prevErrors, cvv: '' }));
        }

        if (name === 'number' && !/^\d{12}$/.test(value)) {
            setErrors((prevErrors) => ({ ...prevErrors, number: 'Card number must be exactly 12 digits' }));
        } else if (name === 'number') {
            setErrors((prevErrors) => ({ ...prevErrors, number: '' }));
        }

        if (name === 'expiration' && !/^(0[1-9]|1[0-2])\/(0[1-9]|[12]\d|3[01])$/.test(value)) {
            setErrors((prevErrors) => ({ ...prevErrors, expiration: 'Expiration date must be in MM/DD format' }));
        } else if (name === 'expiration') {
            setErrors((prevErrors) => ({ ...prevErrors, expiration: '' }));
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (errors.cvv || errors.number || errors.expiration) {
            alert('Please fix the errors before submitting');
            return;
        }
        try {
            const response = await axios.post(`http://localhost:8080/api/v1/cards`, cardDetails);
            console.log('Card details submitted:', response.data);
            await axios.get(`http://localhost:8080/api/v1/cart/user/${id}/products/move`);
            // alert('Order placed successfully!');
            navigate(`/user/${id}`);
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
                        {errors.number && <p className="error">{errors.number}</p>}
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
                            {errors.cvv && <p className="error">{errors.cvv}</p>}
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
                            {errors.expiration && <p className="error">{errors.expiration}</p>}
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
