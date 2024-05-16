
// import React, { useState } from 'react';
// import axios from 'axios';

// const CardForm = () => {
//   const [cardData, setCardData] = useState({
//     fullname: '',
//     number: '',
//     cvv: '',
//     expiration: '',
//     address: '',
//     city: '',
//     country: '',
//     zipcode: ''
//   });

//   const handleChange = (e) => {
//     const { name, value } = e.target;
//     setCardData({ ...cardData, [name]: value });
//   };

//   const handleSubmit = async (e) => {
//     e.preventDefault();
//     try {
//       const response = await axios.post('http://localhost:8080/api/v1/cards', cardData);
//       console.log(response.data);
//       // Clear form after successful submission
//       setCardData({
//         fullname: '',
//         number: '',
//         cvv: '',
//         expiration: '',
//         address: '',
//         city: '',
//         country: '',
//         zipcode: ''
//       });
//     } catch (error) {
//       console.error('Error submitting form:', error);
//     }
//   };

//   return (
//     <form onSubmit={handleSubmit}>
//       <div>
//         <label>Full Name:</label>
//         <input type="text" name="fullname" value={cardData.fullname} onChange={handleChange} required />
//       </div>
//       <div>
//         <label>Card Number:</label>
//         <input type="text" name="number" value={cardData.number} onChange={handleChange} required />
//       </div>
//       <div>
//         <label>CVV:</label>
//         <input type="text" name="cvv" value={cardData.cvv} onChange={handleChange} required />
//       </div>
//       <div>
//         <label>Expiration:</label>
//         <input type="text" name="expiration" value={cardData.expiration} onChange={handleChange} required />
//       </div>
//       <div>
//         <label>Address:</label>
//         <input type="text" name="address" value={cardData.address} onChange={handleChange} />
//       </div>
//       <div>
//         <label>City:</label>
//         <input type="text" name="city" value={cardData.city} onChange={handleChange} />
//       </div>
//       <div>
//         <label>Country:</label>
//         <input type="text" name="country" value={cardData.country} onChange={handleChange} />
//       </div>
//       <div>
//         <label>Zipcode:</label>
//         <input type="text" name="zipcode" value={cardData.zipcode} onChange={handleChange} />
//       </div>
//       <button type="submit">Submit</button>
//     </form>
//   );
// };

// export default CardForm;




//HERE IS THE GOOD CODE
//
//
//
//
// import React, { useEffect, useState } from 'react';
// import axios from 'axios';
// import { useParams } from 'react-router-dom';

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

//     const handleChange = (e) => {
//         const { name, value } = e.target;
//         setCardDetails({ ...cardDetails, [name]: value });
//     };

//     const placeOrder = async () => {
//         try {
//             await axios.get(`http://localhost:8080/api/v1/cart/user/${id}/products/move`);
//             alert('Order placed successfully!');
//             // Navigate to a success page or reset the form as needed
//         } catch (error) {
//             console.error("Error placing order:", error);
//         }
//     };

//     const handleSubmit = async (e) => {
//         e.preventDefault();
//         try {
//             const response = await axios.post(`http://localhost:8080/api/v1/cards`, cardDetails);
//             console.log('Card details submitted:', response.data);
//             placeOrder(); // Place order after submitting card details
//         } catch (error) {
//             console.error('Error submitting card details:', error);
//         }
//     };

//     return (
//         <div className="container">
//             <h2>Payment Details</h2>
//             <form onSubmit={handleSubmit}>
//                 <div>
//                     <label>Full Name</label>
//                     <input
//                         type="text"
//                         name="fullname"
//                         value={cardDetails.fullname}
//                         onChange={handleChange}
//                         required
//                     />
//                 </div>
//                 <div>
//                     <label>Card Number</label>
//                     <input
//                         type="text"
//                         name="number"
//                         value={cardDetails.number}
//                         onChange={handleChange}
//                         required
//                     />
//                 </div>
//                 <div>
//                     <label>CVV</label>
//                     <input
//                         type="text"
//                         name="cvv"
//                         value={cardDetails.cvv}
//                         onChange={handleChange}
//                         required
//                     />
//                 </div>
//                 <div>
//                     <label>Expiration Date</label>
//                     <input
//                         type="text"
//                         name="expiration"
//                         value={cardDetails.expiration}
//                         onChange={handleChange}
//                         required
//                     />
//                 </div>
//                 <div>
//                     <label>Address</label>
//                     <input
//                         type="text"
//                         name="address"
//                         value={cardDetails.address}
//                         onChange={handleChange}
//                         required
//                     />
//                 </div>
//                 <div>
//                     <label>City</label>
//                     <input
//                         type="text"
//                         name="city"
//                         value={cardDetails.city}
//                         onChange={handleChange}
//                         required
//                     />
//                 </div>
//                 <div>
//                     <label>Country</label>
//                     <input
//                         type="text"
//                         name="country"
//                         value={cardDetails.country}
//                         onChange={handleChange}
//                         required
//                     />
//                 </div>
//                 <div>
//                     <label>Zip Code</label>
//                     <input
//                         type="text"
//                         name="zipcode"
//                         value={cardDetails.zipcode}
//                         onChange={handleChange}
//                         required
//                     />
//                 </div>
//                 <button type="submit" style={{
//                     backgroundColor: 'yellow',
//                     color: 'black',
//                     fontSize: '16px',
//                     padding: '10px',
//                     marginTop: '20px',
//                     border: 'none',
//                     borderRadius: '5px',
//                     cursor: 'pointer'
//                 }}>
//                     Submit Payment Details
//                 </button>
//             </form>
//             <button onClick={placeOrder} style={{
//                 backgroundColor: 'yellow',
//                 color: 'black',
//                 fontSize: '16px',
//                 padding: '10px',
//                 marginTop: '20px',
//                 border: 'none',
//                 borderRadius: '5px',
//                 cursor: 'pointer'
//             }}>
//                 Place Order
//             </button>
//         </div>
//     );
// }
import React, { useState } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';
import './Card.css'; // Import CSS for styling

export default function Payment() {
    const { id } = useParams();
    const [cardDetails, setCardDetails] = useState({
        // user_id: id,
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
    // const handleChange = (e) => {
    //     const { name, value } = e.target;
    //     setCardDetails({ ...cardDetails, [name]: value });
    // };

    // const placeOrder = async () => {
    //     try {
    //         await axios.get(`http://localhost:8080/api/v1/cart/user/${id}/products/move`);
    //         alert('Order placed successfully!');
    //     } catch (error) {
    //         console.error("Error placing order:", error);
    //     }
    // };

    // const handleSubmit = async (e) => {
    //     e.preventDefault();
    //     try {
    //         const response = await axios.post(`http://localhost:8080/api/v1/card`, cardDetails);
    //         console.log('Card details submitted:', response.data);
    //         placeOrder(); // Place order after submitting card details
    //     } catch (error) {
    //         console.error('Error submitting card details:', error);
    //     }
    // };

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