import axios from "axios";

export const apiClient = axios.create({
  baseURL: process.env.NUXT_PUBLIC_API_BASE ?? "http://localhost:8080",
  headers: {},
  withCredentials: true,
});



apiClient.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("access_token");

    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }

    return config;
  },
  (error) => {
    return Promise.reject(error);
  },
);