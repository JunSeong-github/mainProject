import axios from "axios";
// export const api = axios.create({
//     baseURL: "http://localhost:8080", // 백엔드 포트
//     headers: { "Content-Type": "application/json" },
// });

// 개발: VITE_API_BASE=http://localhost:8080
// 운영(도커/NGINX): VITE_API_BASE=/api  (같은 도메인에서 리버스 프록시)
const baseURL = import.meta.env.VITE_API_BASE || "";
export const api = axios.create({ baseURL, headers: { "Content-Type": "application/json" } });