<template>
  <header class="bg-slate-800 shadow-sm">
    <div class="mx-auto flex items-center justify-between">
      <IconHeaderLogo class="w-16 h-16 top-0 -left-0 absolute"/>

      <!-- Меню -->
      <nav class="hidden md:flex space-x-8 text-white">
        <router-link to="/menu" class="hover:text-[rgb(68,235,153)] cursor-pointer">
          Create game
        </router-link>
        <router-link to="/find-game" class="hover:text-[rgb(68,235,153)] cursor-pointer">
          Find game
        </router-link>
        <router-link to="/top-users" class="hover:text-[rgb(68,235,153)] cursor-pointer">
          Top players
        </router-link>
      </nav>

      <!-- Иконка профиля и выпадающее меню -->
      <div class="relative" ref="dropdownContainer">
        <div class="flex items-center mr-4">
          <button
              @click.stop="profileHandler"
              class="rounded-full bg-slate-200 h-10 w-10 cursor-pointer transition-transform transform"
          >
            <img src="@/assets/user.svg" alt="Профиль" class="h-8 w-8 rounded-full m-auto" />
          </button>
        </div>

        <!-- Выпадающее меню -->
        <transition
            enter-active-class="transition duration-150 ease-out"
            enter-from-class="transform opacity-0 translate-y-2"
            enter-to-class="transform opacity-100 translate-y-0"
            leave-active-class="transition duration-100 ease-in"
            leave-from-class="transform opacity-100 translate-y-0"
            leave-to-class="transform opacity-0 translate-y-2"
        >
          <div
              v-if="isProfileOpen"
              class="absolute right-0 mt-3 w-60 bg-white rounded-xl shadow-2xl z-50 border border-slate-100 overflow-hidden"
          >
            <div class="bg-gradient-to-r from-green-100 to-blue-100 p-4">
              <div class="text-center mb-2">
                <div class="font-bold text-xl text-slate-800 mb-1">
                  {{ login }}
                </div>
                <div class="text-sm text-slate-500">
                  Total games: {{ totalGames }}
                </div>
              </div>
            </div>

            <div v-if="currentUser" class="p-4 space-y-4">
              <!-- Прогресс бар побед -->
              <div>
                <div class="flex justify-between text-sm mb-2">
                  <span class="text-slate-600">Win rate</span>
                  <span class="font-semibold text-green-600">
                    {{ winPercentage }}%
                  </span>
                </div>
                <div class="h-2 bg-slate-100 rounded-full overflow-hidden">
                  <div
                      class="h-full bg-gradient-to-r from-green-400 to-blue-400 rounded-full transition-all duration-500"
                      :style="{ width: `${winPercentage}%` }"
                  ></div>
                </div>
              </div>

              <!-- Статистика -->
              <div class="space-y-3">
                <div class="flex items-center">
                  <div class="flex-1">
                    <div class="flex justify-between text-sm">
                      <span class="text-slate-600">Wins</span>
                      <span class="font-medium text-slate-900">{{ currentUser.countWin }}</span>
                    </div>
                  </div>
                </div>

                <div class="flex items-center">
                  <div class="flex-1">
                    <div class="flex justify-between text-sm">
                      <span class="text-slate-600">Draws</span>
                      <span class="font-medium text-slate-900">{{ currentUser.countDraw }}</span>
                    </div>
                  </div>
                </div>

                <div class="flex items-center">
                  <div class="flex-1">
                    <div class="flex justify-between text-sm">
                      <span class="text-slate-600">Losses</span>
                      <span class="font-medium text-slate-900">{{ currentUser.countLose }}</span>
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <!-- Лоадер -->
            <div v-else class="p-6 flex justify-center items-center">
              <div class="animate-spin h-8 w-8 border-4 border-blue-400 rounded-full border-t-transparent"></div>
            </div>
          </div>
        </transition>
      </div>
    </div>
  </header>
</template>

<script setup>
import {ref, onMounted, onBeforeUnmount, computed} from 'vue'
import { useRouter } from 'vue-router'
import IconHeaderLogo from "@/assets/IconHeaderLogo.vue"
import api from "@/services/api.js";

const router = useRouter()
const isProfileOpen = ref(false)
const currentUser = ref(null)
const dropdownContainer = ref(null)
const login = localStorage.getItem('login')

const profileHandler = () => {
  isProfileOpen.value = !isProfileOpen.value
  if (isProfileOpen.value && !currentUser.value) {
    fetchCurrentUser()
  }
}

const totalGames = computed(() => {
  if (!currentUser.value) return 0
  return currentUser.value.countWin + currentUser.value.countDraw + currentUser.value.countLose
})

const winPercentage = computed(() => {
  if (totalGames.value === 0) return 0
  return Math.round((currentUser.value.countWin / totalGames.value) * 100)
})

const fetchCurrentUser = async () => {
  try {
    console.log('login')
    console.log(login)
    if (!login) return

    const response = await api.getCurrUser(login)
    currentUser.value = response.data
  } catch (error) {
    console.error('Error fetching user data:', error)
  }
}

const handleClickOutside = (event) => {
  if (dropdownContainer.value && !dropdownContainer.value.contains(event.target)) {
    isProfileOpen.value = false
  }
}

onMounted(() => {
  document.addEventListener('click', handleClickOutside)
})

onBeforeUnmount(() => {
  document.removeEventListener('click', handleClickOutside)
})
</script>