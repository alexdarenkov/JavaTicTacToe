<template>
  <div class="container mx-auto p-4 max-w-md space-y-8">
    <!-- Секция создания игры -->
    <section class="space-y-6">
      <h2 class="text-2xl font-bold text-gray-800">Новая игра</h2>

      <!-- Выбор значка -->
      <div class="space-y-4">
        <h3 class="text-sm font-semibold text-gray-600">Значок</h3>
        <div class="flex gap-4">
          <label
              v-for="option in iconOptions"
              :key="option.value"
              class="flex-1 cursor-pointer"
          >
            <input
                type="radio"
                v-model="icon"
                :value="option.value"
                class="hidden"
            />
            <div
                :class="[
                'p-4 text-center rounded-lg border-2 transition-all',
                icon === option.value
                  ? 'border-blue-500 bg-blue-50'
                  : 'border-gray-200 hover:border-blue-300'
              ]"
            >
              <span class="text-2xl font-bold block mb-2">{{ option.symbol }}</span>
              <span class="text-gray-700 text-sm">{{ option.label }}</span>
            </div>
          </label>
        </div>
      </div>

      <!-- Выбор оппонента -->
      <div class="space-y-4">
        <h3 class="text-sm font-semibold text-gray-600">Оппонент</h3>
        <div class="flex gap-4">
          <label
              v-for="mode in gameModes"
              :key="mode.value"
              class="flex-1 cursor-pointer"
          >
            <input
                type="radio"
                v-model="ai"
                :value="mode.value"
                class="hidden"
            />
            <div
                :class="[
                'p-4 text-center rounded-lg border-2 transition-all',
                ai === mode.value
                  ? 'border-blue-500 bg-blue-50'
                  : 'border-gray-200 hover:border-blue-300'
              ]"
            >
              <span class="text-2xl block mb-2">{{ mode.emoji }}</span>
              <span class="text-gray-700 text-sm">{{ mode.label }}</span>
            </div>
          </label>
        </div>
      </div>

      <button
          @click="createGame(ai)"
          class="w-full bg-blue-500 hover:bg-blue-600 text-white font-medium py-3 rounded-lg transition-colors"
      >
        Начать игру
      </button>

      <!-- Сообщения -->
      <div
          v-if="errorMessage"
          class="p-3 rounded-lg bg-red-100 text-red-700 text-sm"
      >
        {{ errorMessage }}
      </div>
      <div
          v-if="successMessage"
          class="p-3 rounded-lg bg-green-100 text-green-700 text-sm"
      >
        {{ successMessage }}
      </div>
    </section>

    <!-- Свободные игры -->
    <section class="space-y-4">
      <h2 class="text-lg font-semibold text-gray-800">Открытые игры</h2>

      <div
          v-if="freeGames.length === 0"
          class="p-4 text-center text-gray-500 rounded-lg bg-gray-50"
      >
        Нет доступных игр
      </div>

      <ul v-else class="space-y-2">
        <li
            v-for="gameId in freeGames"
            :key="gameId"
            class="flex items-center justify-between p-4 bg-white rounded-lg shadow-sm border border-gray-100"
        >
          <span class="text-gray-700">Игра #{{ gameId }}</span>
          <button
              @click="joinGame(gameId)"
              class="px-4 py-2 bg-green-500 hover:bg-green-600 text-white text-sm rounded-md transition-colors"
          >
            Присоединиться
          </button>
        </li>
      </ul>
    </section>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import api from "@/services/api.js";

const router = useRouter()

const icon = ref(1)
const ai = ref(false)
const errorMessage = ref('')
const successMessage = ref('')
const freeGames = ref([])

const iconOptions = [
  { value: 1, symbol: '✕', label: 'Играть за X' },
  { value: 2, symbol: '○', label: 'Играть за O' }
]

const gameModes = [
  { value: true, emoji: '👾', label: 'Против ИИ' },
  { value: false, emoji: '👥', label: 'С другом' }
]

const createGame = async (vsAI) => {
  errorMessage.value = ''
  successMessage.value = ''

  try {
    const response = await api.createGame({
      icon: icon.value,
      ai: vsAI
    })

    console.log(response)

    if (response.status === 201) {
      localStorage.setItem('icon', icon.value)
      const gameId = response.data.id
      console.log('ID:')
      console.log(gameId)
      router.push({
        name: vsAI ? 'GameWithAI' : 'GameWithUser',
        params: { id: gameId }
      })
    }
  } catch (error) {
    console.error('Ошибка создания игры:', error)
    errorMessage.value = 'Не удалось создать игру'
  }
}

const getFreeGames = async () => {
  try {
    const response = await api.getFreeGames()
    freeGames.value = response.data
  } catch (error) {
    console.error('Ошибка загрузки игр:', error)
    errorMessage.value = 'Не удалось загрузить список игр'
  }
}

const joinGame = async (gameId) => {
  try {
    const response = await api.joinGame(gameId)

    if (response.status === 200) {
      router.push({ name: 'GameWithUser', params: { id: gameId } })
    }
  } catch (error) {
    console.error('Ошибка подключения:', error)
    errorMessage.value = 'Не удалось присоединиться к игре'
  }
}

onMounted(getFreeGames)
</script>