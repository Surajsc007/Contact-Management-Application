import React from 'react';
import { Link } from 'react-router-dom';

const Contact = ({ contact = {} }) => {
  // Ensure contact is not null or undefined
  if (!contact) {
    return <p>Loading contact...</p>;
  }

  // Provide default values for missing properties
  const {
    id = '',
    name = 'Unknown',
    title = 'No Title',
    email = '',
    address = 'No Address',
    phone = 'No Phone',
    status = 'Inactive',
    photoUrl = 'https://via.placeholder.com/100' // Default placeholder image
  } = contact;

  return (
    <Link to={`/contacts/${id}`} className="contact__item">
      <div className="contact__header">
        <div className="contact__image">
          <img src={photoUrl} alt={name} />
        </div>
        <div className="contact__details">
          <p className="contact_name">{name.substring(0, 50)}</p>
          <p className="contact_title">{title}</p>
        </div>
      </div>
      <div className="contact__body">
        <p><i className="bi bi-envelope"></i> {email ? email.substring(0, 20) : 'No Email'}</p>
        <p><i className="bi bi-geo"></i> {address}</p>
        <p><i className="bi bi-telephone"></i> {phone}</p>
        <p>{status === 'Active' ? <i className='bi bi-check-circle'></i> : 
            <i className='bi bi-x-circle'></i>} {status}</p>
      </div>
    </Link>
  );
};

export default Contact;
