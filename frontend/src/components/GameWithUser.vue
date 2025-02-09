<template>
  <div class="min-h-screen p-8 flex flex-col items-center bg-gray-50">
    <h1 class="text-3xl font-bold text-gray-800 mb-8">Tic-Tac-Toe</h1>

    <div v-if="loading" class="text-lg text-gray-600">Загрузка...</div>

    <div v-else class="flex flex-col items-center">
      <div id="game-board" class="space-y-2">
        <div
            v-for="(row, rowIndex) in webGame?.field"
            :key="rowIndex"
            class="flex gap-2"
        >
          <div
              v-for="(cell, colIndex) in row"
              :key="colIndex"
              class="w-20 h-20 flex items-center justify-center text-4xl font-bold rounded-lg transition-all duration-200 cursor-pointer"
              :class="[
              cell === 1 ? 'bg-green-500 text-white' :
              cell === 2 ? 'bg-red-500 text-white' :
              'bg-white hover:bg-blue-50 border-2 border-blue-200',
              gameEnded ? 'cursor-not-allowed opacity-75' : ''
            ]"
              @click="!gameEnded && handleCellClick(rowIndex, colIndex)"
          >
            {{ cell === 1 ? 'X' : cell === 2 ? 'O' : '' }}
          </div>
        </div>
      </div>

      <div
          v-if="message"
          class="mt-6 p-4 bg-white border border-gray-200 rounded-xl shadow-sm flex items-center gap-4"
      >
        <span class="text-lg text-gray-700">{{ message }}</span>
        <button
            @click="resetGame"
            class="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition-colors"
        >
          Начать заново
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import {useRoute, useRouter} from 'vue-router'
import api from "@/services/api.js";

const router = useRouter()
const route = useRoute()
const gameId = route.params.id;


const webGame = ref(null)
const message = ref('')
const loading = ref(true)
const gameEnded = ref(false)

const getGame = async (id) => {
  try {
    const { data } = await api.getCurrGame(id);

    webGame.value = data
    gameEnded.value = false
  } catch (error) {
    console.error('Ошибка загрузки игры:', error)
  } finally {
    loading.value = false
  }
}

const sendMoveToServer = async (row, col) => {
  try {
    const { data } = await api.postMove(
        webGame.value.id, {
          row: row,
          col: col
        })

    webGame.value = data
    checkGameStatus(data.gameState)
  } catch (error) {
    message.value = 'Ошибка при отправке хода'
  }
}

const handleCellClick = async (row, col) => {
  if (webGame.value?.field[row][col] !== 0) return

  await sendMoveToServer(row, col)
}

const checkGameStatus = (gameState) => {
  const statusMap = {
    'FIRST_PLAYER_WON': 'Игрок X победил!',
    'SECOND_PLAYER_WON': 'Игрок O победил!',
    'DRAW': 'Ничья!'
  }

  if (statusMap[gameState]) {
    message.value = statusMap[gameState]
    gameEnded.value = true
  }
}

const resetGame = () => {
  router.push('/menu')
}

onMounted(() => {
  getGame(gameId)
})
</script>