import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

export default defineConfig({
    plugins: [react()],
    server: {
        port: 5173,
        proxy: {
            // 로컬 백엔드가 8080일 때
            '/api': { target: 'http://localhost:8080', changeOrigin: true, secure: false },
            '/v3':  { target: 'http://localhost:8080', changeOrigin: true, secure: false },
            '/swagger-ui': { target: 'http://localhost:8080', changeOrigin: true, secure: false },
            '/actuator':   { target: 'http://localhost:8080', changeOrigin: true, secure: false },
        },
    },
})
