import axios from 'axios';

const client = axios.create({
  baseURL: import.meta.env.PROD ? import.meta.env.VITE_API_BASE_URL : undefined,
  withCredentials: true,
});

export default client;

client.interceptors.response.use(
  (response) => {
    return response;
  },
  async (error) => {
    if (import.meta.env.PROD && error.response && error.response.status === 401) {
      try {
        localStorage.removeItem('auth-storage');
        window.location.href = '/login';
      } catch (error) {
        console.log('interceptor Logout Error :', error);
        return Promise.reject(error);
      }
    }

    return Promise.reject(error);
  }
);
