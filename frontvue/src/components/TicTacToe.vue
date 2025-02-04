<template>
  <div class="tic-tac-toe">
    <h1>Tic-Tac-Toe</h1>
    <div v-if="loading" class="loading">Загрузка...</div>
    <div v-else>
      <div id="game-board">
        <div v-for="(row, rowIndex) in webGame?.field" :key="rowIndex" class="row">
          <div
              v-for="(cell, colIndex) in row"
              :key="colIndex"
              :class="['cell', { x: cell === 1, o: cell === 2, disabled: gameEnded }]"
              @click="!gameEnded && handleCellClick(rowIndex, colIndex)"
          >
            {{ cell === 1 ? 'X' : cell === 2 ? 'O' : '' }}
          </div>
        </div>
      </div>
      <div v-if="message" class="message">
        <div>{{ message }}</div>
        <button @click="resetGame">Начать заново</button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import { ref, onMounted } from 'vue';
import {useRoute} from "vue-router";

export default {
  setup() {
    const webGame = ref(null);
    const message = ref('');
    const loading = ref(true);
    const authHeader = localStorage.getItem('authHeader');
    const gameEnded = ref(false);
    const route = useRoute();
    const gameId = route.params.id;

    const getGame = async (id) => {
      try {
        const response = await axios.get(`http://localhost:8080/game/${id}`,
            { headers: { Authorization: authHeader } }
        );

        if (response.status === 200) {
          webGame.value = response.data;
          console.log(webGame.value.id)
          gameEnded.value = false; // Сброс состояния игры

        } else {
          console.error('Ошибка при получении новой игры:', response.status, response.statusText);
        }
      } catch (error) {
        console.log("ПИЗДА РУЛЮ")
      } finally {
        loading.value = false;
      }
    };

    const sendMoveToServer = async (row, col) => {
      try {
        const response = await axios.post(`http://localhost:8080/game/${webGame.value.id}`,
          { row, col}, { headers: {Authorization: authHeader } }
        );

        if (response.status === 200) {
          webGame.value = response.data;
          checkGameStatus(webGame.value.gameState);
        } else {
          message.value = 'Ошибка при отправке хода';
        }
      } catch (error) {
        console.error('Ошибка сети:', error);
        message.value = 'Ошибка при отправке хода';
      }
    };

    const handleCellClick = async (row, col) => {
      if (!webGame.value || webGame.value.field[row][col] !== 0) {
        return;
      }

      const updatedGame = { ...webGame.value };
      updatedGame.field[row][col] = localStorage.getItem("icon");
      webGame.value = updatedGame;
      await sendMoveToServer(row, col);
    };

    const checkGameStatus = (gameState) => {
      console.log(gameState)
      if (gameState === 'FIRST_PLAYER_WON' || gameState === 'SECOND_PLAYER_WON') {
        message.value = 'Конец!';
        gameEnded.value = true;
      } else if (gameState === 'DRAW') {
        message.value = 'Ничья!';
        gameEnded.value = true;
      }
    };

    const resetGame = () => {
      router.push()
    };

    onMounted(() => {
      if (!authHeader) {
        message.value = 'Ошибка авторизации. Пожалуйста, войдите снова.';
        return;
      }
      getGame(gameId);
    });

    return {
      webGame,
      message,
      loading,
      handleCellClick,
      resetGame,
      gameEnded,
    };
  },
};
</script>

<style scoped>
.tic-tac-toe {
  display: flex;
  flex-direction: column;
  align-items: center;
  font-family: 'Arial', sans-serif;
  background-color: #ffffff;
  border-radius: 10px;
  padding: 20px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
}

h1 {
  color: #333;
  margin-bottom: 20px;
}

.loading {
  font-size: 18px;
  color: #666;
}

#game-board {
  display: grid;
  grid-template-columns: repeat(3, 100px);
  grid-template-rows: repeat(3, 100px);
  gap: 10px;
}

.row {
  display: contents; /* Используем для сетки */
}

.cell {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100px;
  height: 100px;
  font-size: 2rem;
  color: #fff;
  background-color: #007bff; /* Основной цвет ячейки */
  border-radius: 10px;
  cursor: pointer;
  transition: background-color 0.3s, transform 0.2s;
  border: 2px solid #e0e0e0; /* Легкая граница для ячеек */
}

.cell:hover {
  background-color: #0056b3; /* Темнее при наведении */
  transform: scale(1.05); /* Увеличение при наведении */
}

.cell.x {
  background-color: #28a745; /* Зеленый для X */
}

.cell.o {
  background-color: #dc3545; /* Красный для O */
}

.cell.disabled {
  cursor: not-allowed; /* Указатель "недоступно" для заблокированных ячеек */
  background-color: #e0e0e0; /* Серый цвет для заблокированных ячеек */
}

.message {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 20px;
  padding: 10px;
  background-color: #f8f9fa; /* Светлый фон для сообщения */
  border: 1px solid #ccc;
  border-radius: 5px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.message button {
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 5px;
  padding: 5px 10px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.message button:hover {
  background-color: #0056b3; /* Темнее при наведении */
}
</style>