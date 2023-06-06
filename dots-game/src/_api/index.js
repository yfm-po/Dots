import axios from 'axios';

const gsAxios = axios.create({
		baseURL: 'http://localhost:3000/api/',
});

export default gsAxios;
