<template>
  <div class="new-game-container">
    <h2>Создать новую игру</h2>

    <div class="form-section">
      <h3>Выберите значок:</h3>
      <div class="radio-group">
        <label class="radio-option">
          <input
              type="radio"
              v-model="icon"
              :value="1"
              class="radio-input"
              aria-label="Выбрать X"
          >
          <span class="custom-radio x-option"></span>
          X
        </label>

        <label class="radio-option">
          <input
              type="radio"
              v-model="icon"
              :value="2"
              class="radio-input"
              aria-label="Выбрать O"
          >
          <span class="custom-radio o-option"></span>
          O
        </label>
      </div>
    </div>

    <div class="form-section">
      <h3>Играть с:</h3>
      <div class="radio-group">
        <label class="radio-option">
          <input
              type="radio"
              v-model="ai"
              :value="true"
              class="radio-input"
              aria-label="Играть с ИИ"
          >
          <span class="custom-radio ai-option"></span>
          С ИИ
        </label>

        <label class="radio-option">
          <input
              type="radio"
              v-model="ai"
              :value="false"
              class="radio-input"
              aria-label="Играть с другом"
          >
          <span class="custom-radio friend-option"></span>
          С другом
        </label>
      </div>
    </div>

    <button @click="createGame(ai)" class="submit-button">Создать игру</button>

    <div v-if="errorMessage" class="error-message">{{ errorMessage }}</div>
    <div v-if="successMessage" class="success-message">{{ successMessage }}</div>

    <!-- Новый раздел для отображения списка свободных игр -->
    <div class="free-games-section">
      <h2>Свободные игры</h2>
      <ul v-if="freeGames.length > 0" class="games-list">
        <li v-for="gameId in freeGames" :key="gameId" class="game-item">
          <span>Игра #{{ gameId }}</span>
          <button @click="joinGame(gameId)" class="join-button">Присоединиться</button>
        </li>
      </ul>
      <p v-else>Нет доступных игр</p>
    </div>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';

export default {
  setup() {
    const icon = ref(1); // По умолчанию X
    const ai = ref(false); // По умолчанию с другом
    const errorMessage = ref('');
    const successMessage = ref('');
    const freeGames = ref([]); // Список свободных игр
    const router = useRouter();
    const authHeader = localStorage.getItem('authHeader');

    // Функция для создания игры
    const createGame = async (vsAI) => {
      errorMessage.value = '';
      successMessage.value = '';

      try {
        const response = await axios.post('http://localhost:8080/game/new', {
          icon: icon.value,
          ai: vsAI
        }, {
          headers: {
            Authorization: authHeader,
          },
        });

        if (response.status === 201) {
          localStorage.setItem("icon", icon.value);
          successMessage.value = 'Игра успешно создана!';
          const gameId = response.data.id;

          // Перенаправление на соответствующий маршрут
          if (vsAI) {
            router.push({ name: 'TicTacToe', params: { id: gameId } });
          } else {
            router.push({ name: 'GameWithUser', params: { id: gameId } });
          }
        } else {
          errorMessage.value = 'Ошибка при создании игры.';
        }
      } catch (error) {
        console.error('Ошибка сети:', error);
        errorMessage.value = 'Ошибка при создании игры.';
      }
    };

    // Функция для получения списка свободных игр
    const fetchFreeGames = async () => {
      try {
        const response = await axios.get('http://localhost:8080/game/waiting-for-prayers', {
          headers: {
            Authorization: authHeader,
          },
        });
        freeGames.value = response.data;
      } catch (error) {
        console.error('Ошибка при получении списка игр:', error);
        errorMessage.value = 'Ошибка при получении списка игр.';
      }
    };

    // Функция для присоединения к игре
    const joinGame = async (gameId) => {
      try {
        const response = await axios.get(`http://localhost:8080/game/${gameId}/join`, {
          headers: {
            Authorization: authHeader,
          },
        });

        if (response.status === 200) {
          // Перенаправление на страницу игры
          router.push({ name: 'GameWithUser', params: { id: gameId } });
        } else {
          errorMessage.value = 'Ошибка при присоединении к игре.';
        }
      } catch (error) {
        console.error('Ошибка сети:', error);
        errorMessage.value = 'Ошибка при присоединении к игре.';
      }
    };

    // Загружаем список свободных игр при монтировании компонента
    onMounted(() => {
      fetchFreeGames();
    });

    return {
      icon,
      ai,
      createGame,
      errorMessage,
      successMessage,
      freeGames,
      joinGame,
    };
  },
};
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Inter:wght@400;500&display=swap');

.new-game-container {
  max-width: 400px;
  margin: 0 auto;
  padding: 20px;
  border: 1px solid #e0e0e0;
  border-radius: 12px;
  background-color: #ffffff;
  font-family: 'Inter', sans-serif;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

h2 {
  font-size: 24px;
  font-weight: 500;
  margin-bottom: 20px;
  color: #333;
}

h3 {
  font-size: 18px;
  font-weight: 500;
  margin-bottom: 10px;
  color: #555;
}

.radio-group {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.radio-option {
  display: flex;
  align-items: center;
  cursor: pointer;
  font-size: 16px;
  color: #333;
}

.radio-input {
  display: none;
}

.custom-radio {
  width: 20px;
  height: 20px;
  border: 2px solid #007bff;
  border-radius: 50%;
  margin-right: 10px;
  position: relative;
  transition: background-color 0.2s;
}

.radio-input:checked + .custom-radio {
  background-color: #007bff;
}

.submit-button {
  padding: 10px 15px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  font-size: 16px;
  font-weight: 500;
  transition: background-color 0.2s;
}

.submit-button:hover {
  background-color: #0056b3;
}

.error-message {
  color: #ff4d4f;
  margin-top: 10px;
  font-size: 14px;
}

.success-message {
  color: #52c41a;
  margin-top: 10px;
  font-size: 14px;
}

.free-games-section {
  margin-top: 30px;
}

.games-list {
  list-style: none;
  padding: 0;
}

.game-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px;
  border-bottom: 1px solid #e0e0e0;
}

.join-button {
  padding: 5px 10px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  font-size: 14px;
  font-weight: 500;
  transition: background-color 0.2s;
}

.join-button:hover {
  background-color: #0056b3;
}
</style>