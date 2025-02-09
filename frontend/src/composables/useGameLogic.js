import { ref, onMounted } from 'vue';
import axios from 'axios';
import { useRoute, useRouter } from 'vue-router';
import api from "@/services/api.js";

export default function useGameLogic() {
    const route = useRoute();
    const router = useRouter();
    const gameId = route.params.id;
    const authHeader = localStorage.getItem('authHeader');

    const webGame = ref(null);
    const loading = ref(true);
    const error = ref(null);

    const getGame = async () => {
        try {
            console.log('ID:')
            console.log(gameId)
            const response = await api.getCurrGame(gameId)
            webGame.value = response.data;
        } catch (e) {
            error.value = 'Ошибка загрузки игры';
        } finally {
            loading.value = false;
        }
    };

    const makeMove = async (row, col) => {
        try {
            const response = await api.postMove(
                gameId,
                {
                    row: row,
                    col: col
                });
        } catch (e) {
            error.value = 'Ошибка выполнения хода';
        }
    };

    const resetGame = () => {
        router.push({ name: 'NewGame' });
    };

    onMounted(getGame);

    return {
        webGame,
        loading,
        error,
        getGame,
        makeMove,
        resetGame
    };
}