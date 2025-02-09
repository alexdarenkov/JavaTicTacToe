import { createRouter, createWebHistory } from 'vue-router';
import LoginForm from '../components/LoginForm.vue';
import RegisterForm from '../components/RegisterForm.vue';
import Welcome from '../components/Welcome.vue';
import Menu from '../components/Menu.vue';
import GameWithUser from "@/components/GameWithUser.vue";
import GameWithAI from "@/components/GameWithAI.vue";

const routes = [
    { path: '/login', component: LoginForm },
    { path: '/register', component: RegisterForm },
    { path: '/welcome', component: Welcome, meta: { requiresAuth: true }},
    { path: '/menu', component: Menu },
    { path: '/game-with-user/:id', component: GameWithUser, name: 'GameWithUser', props: true },
    { path: '/game-with-ai/:id', component: GameWithAI, name: 'GameWithAI', props: true },
    { path: '/', redirect: '/login' }
];

const router = createRouter({
    history: createWebHistory(),
    routes
});

// // Функция для проверки срока действия токена
// const isTokenValid = (token) => {
//     if (!token) return false;
//
//     try {
//         const decoded = jwtDecode(token); // Декодируем токен
//         const currentTime = Date.now() / 1000; // Текущее время в секундах
//         return decoded.exp > currentTime; // Проверяем, не истек ли срок действия токена
//     } catch (error) {
//         console.error('Error decoding token:', error);
//         return false;
//     }
// };
//
// router.beforeEach(async (to, from, next) => {
//     const accessToken = localStorage.getItem('accessToken');
//     const refreshToken = localStorage.getItem('refreshToken');
//
//     // Если маршрут требует авторизации
//     if (to.meta.requiresAuth) {
//         if (!accessToken || !isTokenValid(accessToken)) {
//             // Если токен отсутствует или истек
//             if (refreshToken) {
//                 try {
//                     // Пытаемся обновить токен
//                     const response = await api.refreshToken({ refreshToken });
//                     localStorage.setItem('accessToken', response.data.accessToken);
//                     localStorage.setItem('refreshToken', response.data.refreshToken);
//                     next(); // Повторяем навигацию
//                 } catch (error) {
//                     // Если не удалось обновить токен, перенаправляем на страницу логина
//                     localStorage.removeItem('accessToken');
//                     localStorage.removeItem('refreshToken');
//                     next('/login');
//                 }
//             } else {
//                 // Если refreshToken отсутствует, перенаправляем на страницу логина
//                 next('/login');
//             }
//         } else {
//             // Если токен валиден, продолжаем навигацию
//             next();
//         }
//     } else {
//         // Если маршрут не требует авторизации, продолжаем навигацию
//         next();
//     }
// });

export default router;