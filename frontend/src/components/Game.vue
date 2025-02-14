<template>
  <Header />

  <div class="relative w-min m-auto gap-8 items-center bg-slate-50 rounded-xl shadow-sm mt-8 p-10 space-y-6">
    <h1 class="text-2xl font-semibold text-black text-center">Tic-Tac-Toe</h1>

    <div v-if="statusMessage" :class="['relative shadow-md rounded-md p-2 text-black', statusMessage === 'It\'s your turn' ? 'bg-[rgb(47,228,211)]' : 'bg-[rgb(255,197,64)]']">
      <p class="text-center font-semibold text-sm">{{statusMessage}}</p>
      <IconOk v-if="statusMessage === 'It\'s your turn'" class="absolute w-3 h-3 left-3 top-1/2 -translate-y-1/2"/>
      <IconTime v-else class="absolute w-3 h-3 left-3 top-1/2 -translate-y-1/2"/>
    </div>

    <div v-if="loading" class="text-lg text-gray-600">Загрузка...</div>

    <div v-else class="flex flex-col items-center">
      <div id="game-board" class="space-y-2">
        <div
            v-for="(row, rowIndex) in game?.field"
            :key="rowIndex"
            class="flex gap-2"
        >
          <div
              v-for="(cell, colIndex) in row"
              :key="colIndex"
              class="w-20 h-20 flex items-center justify-center text-4xl font-bold rounded-lg transition-all duration-200 shadow-sm"
              :class="[
              cell === 1 ? 'bg-[rgb(68,235,153)] text-white' :
              cell === 2 ? 'bg-red-500 text-white' :
              'bg-white border-1 border-gray-200',
              gameEnded ? 'cursor-default opacity-15' : 'hover:bg-purple-800 hover:border-purple-800 cursor-pointer'
            ]"
              @click="!gameEnded && handleCellClick(rowIndex, colIndex)"
          >
            <IconCross v-if="cell === 1" class="w-1/3 h-1/3"/>
            <IconCircle v-else-if="cell === 2" class="w-1/3 h-1/3"/>

          </div>
        </div>
      </div>

      <div v-if="gameEnded" class="absolute p-4 shadow-md rounded-lg top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 w-64 font-semibold"
        :class="[message === 'You win! 🥳' ? 'bg-green-200' : message === 'You lose! 😞' ? 'bg-rose-300' : message === 'Draw! 😑' ? 'bg-amber-100' : '']">
        <p class="text-md text-black text-center">{{ message }}</p>
      </div>
    </div>

    <BaseButton
        v-if="gameEnded"
        buttonText="Play again"
        :onClick="resetGame"
        class="w-full"
    />
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue'
import { useRoute } from 'vue-router'
import api from "@/services/api.js"
import websocket from "@/services/websocket.js"
import router from "@/router/index.js";

import Header from "@/components/Header.vue";
import IconCross from "@/assets/IconCross.vue";
import IconCircle from "@/assets/IconCircle.vue";
import BaseButton from "@/components/BaseButton.vue";
import IconTime from "@/assets/IconTime.vue";
import IconOk from "@/assets/IconOk.vue";

const route = useRoute()
const gameId = route.params.id
const game = ref(null)
const message = ref('')
const statusMessage = ref('')
const loading = ref(true)
const gameEnded = ref(false)
const isFirstPlayer = localStorage.getItem('isFirstPlayer')

// Получение текущего состояния игры
const getGame = async (id) => {
  try {
    const { data } = await api.getCurrGame(id)
    game.value = data
    gameEnded.value = false
    checkGameStatus(data.gameState);
  } catch (error) {
    console.error('Ошибка загрузки игры:', error)
  } finally {
    loading.value = false
  }
}

// Обработка входящих сообщений
const handleIncomingMessage = (gameState) => {
  game.value = gameState
  checkGameStatus(gameState.gameState)
}

// Инициализация WebSocket
const initWebSocket = () => {
  websocket.connect(gameId, handleIncomingMessage)
}

// Обработка хода
const handleCellClick = async (row, col) => {
  if (game.value?.field[row][col] !== 0) return

  try {
    await api.postMove(game.value.id, { row, col })
  } catch (error) {
    message.value = 'Ошибка при отправке хода'
  }
}

// Проверка статуса игры
const checkGameStatus = (gameState) => {
  console.log(gameState)
  console.log(isFirstPlayer)
  const currStatusMap = {
    'WAITING_FOR_PLAYERS' : 'Waiting second player',
    'FIRST_PLAYER_TURN': (isFirstPlayer ? 'It\'s your turn' : 'It\'s second player turn'),
    'SECOND_PLAYER_TURN' : (isFirstPlayer ? 'It\'s your turn' : 'It\'s second player turn')
  }

  const endedStatusMap = {
    'FIRST_PLAYER_WON': (!isFirstPlayer ? 'You win! 🥳' : 'You lose! 😞'),
    'SECOND_PLAYER_WON': (!isFirstPlayer ? 'You win! 🥳' : 'You lose! 😞'),
    'DRAW': 'Draw! 😑'
  }

  if (currStatusMap[gameState]) {
    statusMessage.value = currStatusMap[gameState]
  }

  if (endedStatusMap[gameState]) {
    statusMessage.value = ''
    message.value = endedStatusMap[gameState]
    gameEnded.value = true
  }
}

const resetGame = () => {
  router.push('/menu')
}

// Жизненный цикл
onMounted(async () => {
  await getGame(gameId)
  initWebSocket()
})

onUnmounted(() => {
  websocket.disconnect()
})
</script>