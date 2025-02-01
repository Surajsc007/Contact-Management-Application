import axios from "axios";

const API_URL = "https://contact-management-api-production.up.railway.app";

export async function saveContact(contact) {
  return await axios.post(`${API_URL}/contacts`, contact);
}

export async function getContacts(page = 0, size = 10) {
  return await axios.get(`${API_URL}/contacts?page=${page}&size=${size}`);
}

export async function getContact(id) {
  return await axios.get(`${API_URL}/contacts/${id}`);
}

export async function udpateContact(contact) {
  return await axios.post(`${API_URL}/contacts`, contact);
}

export async function udpatePhoto(formData) {
  return await axios.put(`${API_URL}/contacts/photo`, formData);
}

export async function deleteContact(id) {
  return await axios.delete(`${API_URL}/contacts/${id}`);
}
