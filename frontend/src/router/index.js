import { createRouter, createWebHistory } from 'vue-router';
import LoginForm from '../components/LoginForm.vue';
import RegisterForm from '../components/RegisterForm.vue';
import Welcome from '../components/Welcome.vue';
import Menu from '../components/Menu.vue';
import Game from "@/components/Game.vue";
import FreeGames from "@/components/FreeGames.vue";
import TopUsers from "@/components/TopUsers.vue";

const routes = [
    { path: '/login', component: LoginForm },
    { path: '/register', component: RegisterForm },
    { path: '/welcome', component: Welcome, meta: { requiresAuth: true }},
    { path: '/menu', component: Menu },
    { path: '/game/:id', component: Game, name: 'Game', props: true },
    { path: '/find-game', component: FreeGames, name: 'FreeGames', props: true },
    { path: '/top-users', component: TopUsers, name: 'TopUsers' },
    { path: '/', redirect: '/login' }
];

const router = createRouter({
    history: createWebHistory(),
    routes
});

export default router;