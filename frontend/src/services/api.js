import axios from 'axios';
import { useRouter } from 'vue-router';

const router = useRouter();

const api = axios.create({
    baseURL: 'http://localhost:8080',
    headers: {
        'Content-Type': 'application/json',
    },
});

let isRefreshing = false;
let failedQueue = [];

const processQueue = (error, token = null) => {
    failedQueue.forEach(prom => {
        if (error) {
            prom.reject(error);
        } else {
            prom.resolve(token);
        }
    });
    failedQueue = [];
};

api.interceptors.request.use((config) => {
    const token = localStorage.getItem('accessToken');
    if (token && !config.url.includes('/auth/login') && !config.url.includes('/auth/register')) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
});

api.interceptors.response.use(
    (response) => response,
    async (error) => {
        const originalRequest = error.config;

        if (error.response.status === 401 && !originalRequest._retry) {
            if (isRefreshing) {
                return new Promise((resolve, reject) => {
                    failedQueue.push({ resolve, reject });
                }).then((token) => {
                    originalRequest.headers.Authorization = `Bearer ${token}`;
                    return api(originalRequest);
                }).catch(err => {
                    return Promise.reject(err);
                });
            }

            originalRequest._retry = true;
            isRefreshing = true;

            const refreshToken = localStorage.getItem('refreshToken');
            if (!refreshToken) {
                router.push('/login');
                return Promise.reject(error);
            }

            try {
                const response = await api.post('/auth/access', { refreshToken });
                const newAccessToken = response.data.accessToken;
                const newRefreshToken = response.data.refreshToken;

                localStorage.setItem('accessToken', newAccessToken);
                localStorage.setItem('refreshToken', newRefreshToken);

                api.defaults.headers.common['Authorization'] = `Bearer ${newAccessToken}`;
                originalRequest.headers.Authorization = `Bearer ${newAccessToken}`;

                processQueue(null, newAccessToken);
                return api(originalRequest);
            } catch (e) {
                processQueue(e, null);
                localStorage.removeItem('accessToken');
                localStorage.removeItem('refreshToken');
                router.push('/login');
                return Promise.reject(e);
            } finally {
                isRefreshing = false;
            }
        }

        return Promise.reject(error);
    }
);

export default {
    login(userData) {
        return api.post('/auth/login', userData);
    },
    register(userData) {
        return api.post('/auth/register', userData);
    },
    getHello() {
        return api.get('/game/hello').then(response => response.data);
    },
    createGame(gameParam) {
        return api.post('/game/new', gameParam);
    },
    getFreeGames() {
        return api.get('/game/waiting-for-prayers');
    },
    joinGame(gameId) {
        return api.get(`/game/${gameId}/join`)
    },
    getCurrGame(gameId) {
        return api.get(`/game/${gameId}`)
    },
    postMove(gameId, move) {
        return api.post(`/game/${gameId}`, move)
    }
};