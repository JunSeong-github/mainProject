import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";

// https://vitejs.dev/config/
export default defineConfig({
    plugins: [react()],
    server: {
        port: 5173,          // 필요하면 바꿔도 됨
        open: true,          // dev 서버 띄우면 브라우저 자동 오픈
    },
});