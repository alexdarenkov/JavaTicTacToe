import { createRouter, createWebHistory } from 'vue-router';
import Login from '../components/Login.vue';
import Register from '../components/Register.vue';
import TicTacToe from '../components/TicTacToe.vue';
import CreateGame from "../components/CreateGame.vue";
import GameWithUser from "@/components/GameWithUser.vue";

const routes = [
    { path: '/', redirect: '/login' },
    { path: '/login', component: Login },
    { path: '/register', component: Register },
    { path: '/game/new', component: CreateGame },
    { path: '/game/:id', component: TicTacToe, name: 'TicTacToe', props: true },
    { path: '/game/user/:id', component: GameWithUser, name: 'GameWithUser', props: true },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;