<template>
  <Header />
  <div class="flex flex-col w-min m-auto gap-4 p-4 bg-[#f4f4f9] rounded-xl shadow-sm mt-8">
    <h2 class="text-lg text-center font-semibold text-gray-800">Create game</h2>

    <!-- Выбор значка -->
<!--    <div class="space-y-4 bg-slate-50 p-6 rounded-lg shadow-md">-->
<!--      <h3 class="text-sm font-semibold text-gray-600 text-center">Select an icon</h3>-->
<!--      <div class="flex gap-4 justify-center">-->
<!--        <label v-for="option in iconOptions" :key="option.value" class="cursor-pointer">-->
<!--          <input type="radio" v-model="icon" :value="option.value" class="hidden">-->
<!--          <div-->
<!--              :class="[-->
<!--              'h-14 w-22 flex justify-center items-center text-center border-2 rounded-lg transition-all shadow-sm',-->
<!--              icon === option.value-->
<!--                ? 'bg-purple-600 border-purple-600 text-white'-->
<!--                : 'border-gray-200 hover:bg-purple-600 hover:border-purple-600 active:bg-purple-800 text-gray-600 hover:text-white active:text-white'-->
<!--              ]"-->
<!--          >-->

<!--            <IconCross v-if="option.value === 1" class="w-5 h-5 m-auto"/>-->
<!--            <IconCircle v-else-if="option.value === 2" class="w-6 h-6 m-auto"/>-->

<!--          </div>-->
<!--        </label>-->
<!--      </div>-->
<!--    </div>-->

    <!-- Выбор оппонента -->
    <div class="space-y-4 bg-slate-50 p-6 rounded-lg shadow-md">
      <h3 class="text-sm font-semibold text-gray-600 text-center">Choose your opponent</h3>
      <div class="flex gap-4 justify-center">
        <label v-for="mode in gameModes" :key="mode.value" class="cursor-pointer">
          <input type="radio" v-model="ai" :value="mode.value" class="hidden"/>
          <div
              :class="[
              'flex justify-center items-center text-center rounded-lg border-2 transition-all shadow-sm flex',
              ai === mode.value
                ? 'bg-purple-600 border-purple-600 text-white'
                : 'border-gray-200 hover:bg-purple-600 hover:border-purple-600 active:bg-purple-800 text-gray-600 hover:text-white active:text-white'
            ]"
          >
            <div v-if="mode.value === true" class="flex flex-col justify-center items-center gap-2 px-8 py-4">
              <IconAI  class="w-7 h-7"/>
              <p class="text-center text-xs font-semibold">AI</p>
            </div>

            <div v-else-if="mode.value === false" class="flex flex-col justify-center items-center gap-2 px-8 py-4">
              <IconFriend  class="w-7 h-7"/>
              <p class="text-center text-xs font-semibold">Player</p>
            </div>
          </div>
        </label>
      </div>
    </div>

    <button
        @click="createGame(ai)"
        class="bg-[rgb(68,235,153)] hover:bg-purple-600 text-black hover:text-white active:bg-purple-800 py-2 px-6 rounded-lg cursor-pointer shadow-sm"
    >
      Create game
    </button>

    <Alert :message="errorMessage" />

    <div
        v-if="successMessage"
        class="p-3 rounded-lg bg-green-100 text-green-700 text-sm"
    >
      {{ successMessage }}
    </div>


  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import api from "@/services/api.js";
import Header from "@/components/Header.vue";
import IconCross from "@/assets/IconCross.vue";
import IconCircle from "@/assets/IconCircle.vue";
import IconAI from "@/assets/IconAI.vue";
import IconFriend from "@/assets/IconFriend.vue";
import Alert from "@/components/Alert.vue";

const router = useRouter()

const icon = ref(1)
const isFirstPlayer = ref(true)
const ai = ref(true)
const errorMessage = ref('')
const successMessage = ref('')

const iconOptions = [
  { value: 1 },
  { value: 2 }
]

const gameModes = [
  { value: true },
  { value: false }
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
      localStorage.setItem('isFirstPlayer', isFirstPlayer.value)
      const gameId = response.data.id
      console.log('ID:')
      console.log(gameId)
      router.push({
        name: 'Game',
        params: { id: gameId }
      })
    }
  } catch (error) {
    console.error('Ошибка создания игры:', error)
    errorMessage.value = 'Не удалось создать игру'
  }
}

</script>