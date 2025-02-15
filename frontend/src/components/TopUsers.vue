<template>
  <Header />
  <div class="m-auto w-min p-4 bg-[#f4f4f9] rounded-xl shadow-sm mt-8">
    <section class="space-y-4">
      <h2 class="text-lg text-center font-semibold text-gray-800">Top Players</h2>

      <div class="group flex flex-row items-center gap-3 p-3 rounded-md w-full bg-slate-200 shadow-sm">
        <IconSearch class="w-4 h-4 ml-2 text-slate-400 group-focus-within:text-[rgb(68,235,153)]"/>
        <input
            v-model="searchLogin"
            placeholder="Search by login"
            class="text-sm focus:outline-none text-slate-600 w-full"
        />
      </div>

      <div
          v-if="filteredPlayers.length === 0"
          class="p-3 w-142 text-center rounded-lg bg-gray-white shadow-sm"
      >
        {{ players.length === 0 ? 'No players found' : 'No matches found' }}
      </div>

      <div v-else>
        <!-- Column Headers -->
        <div class="flex flex-row gap-2 mb-2 px-3">
          <div class="w-30 text-xs text-slate-700 font-medium text-center">Login</div>
          <div class="w-20 text-xs text-slate-700 font-medium text-center">Wins</div>
          <div class="w-20 text-xs text-slate-700 font-medium text-center">Draws</div>
          <div class="w-20 text-xs text-slate-700 font-medium text-center">Losses</div>
          <div class="w-20 text-xs text-slate-700 font-medium text-center">Total</div>
          <div class="w-20 text-xs text-slate-700 font-medium text-center">Win%</div>
        </div>

        <!-- Players List -->
        <ul class="space-y-2">
          <li
              v-for="player in filteredPlayers"
              :key="player.id"
              class="flex items-center flex-row gap-2 p-3 bg-white rounded-lg shadow-sm relative overflow-hidden"
          >
            <!-- Медальки для абсолютного топ-3 -->
            <div
                v-if="getPlayerMedal(player.id)"
                class="absolute -left-1 -top-2 text-2xl z-10"
            >
              <span v-if="getPlayerMedal(player.id) === 1" class="text-yellow-500 drop-shadow">🥇</span>
              <span v-else-if="getPlayerMedal(player.id) === 2" class="text-gray-400 drop-shadow">🥈</span>
              <span v-else-if="getPlayerMedal(player.id) === 3" class="text-amber-700 drop-shadow">🥉</span>
            </div>

            <!-- Логин игрока -->
            <div class="bg-slate-100 rounded-lg shadow-sm p-2 w-30 relative">
              <p class="text-center text-xs ibm-plex-mono text-slate-700 truncate">
                {{ player.login }}
              </p>
            </div>

            <!-- Статистика -->
            <div class="flex flex-row gap-2">
              <div class="bg-green-200 text-green-800 text-xs p-2 rounded-lg w-20 text-center ibm-plex-mono shadow-sm">
                {{ player.countWin }}
              </div>
              <div class="bg-amber-100 text-yellow-800 text-xs p-2 rounded-lg w-20 text-center ibm-plex-mono shadow-sm">
                {{ player.countDraw }}
              </div>
              <div class="bg-red-200 text-red-800 text-xs p-2 rounded-lg w-20 text-center ibm-plex-mono shadow-sm">
                {{ player.countLose }}
              </div>
              <div class="bg-slate-100 text-slate-700 text-xs p-2 rounded-lg w-20 text-center ibm-plex-mono shadow-sm">
                {{ totalGames(player) }}
              </div>
              <div
                  class="text-xs font-medium p-2 rounded-lg  w-20 text-center ibm-plex-mono shadow-sm"
                  :class="winPercentageClass(calculateWinPercentage(player))"
              >
                {{ calculateWinPercentage(player) }}%
              </div>
            </div>
          </li>
        </ul>
      </div>
    </section>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import Header from "@/components/Header.vue";
import IconSearch from "@/assets/IconSearch.vue";
import api from "@/services/api.js";

const players = ref([])
const searchLogin = ref('')
const loading = ref(true)
const error = ref(null)
const limit = 10;

// Топ-3 игроков из общего списка
const topThreePlayers = computed(() => {
  return [...players.value]
      .sort((a, b) => {
        const winDiff = b.countWin - a.countWin;
        if (winDiff !== 0) return winDiff;
        // При равенстве побед сортируем по убыванию общего игр
        return totalGames(b) - totalGames(a);
      })
      .slice(0, 3);
});

const totalGames = (player) => {
  return player.countWin + player.countDraw + player.countLose;
};

const calculateWinPercentage = (player) => {
  const total = totalGames(player)
  return total === 0 ? 0 : ((player.countWin / total) * 100).toFixed(1)
}

const winPercentageClass = (percentage) => {
  if (percentage >= 70) return 'bg-green-200 text-green-700'
  if (percentage >= 40) return 'bg-amber-100 text-orange-500'
  return 'bg-red-200 text-red-700'
}

const getPlayerMedal = (playerId) => {
  const index = topThreePlayers.value.findIndex(p => p.id === playerId)
  return index >= 0 ? index + 1 : null
}

const filteredPlayers = computed(() => {
  const searchTerm = searchLogin.value.toLowerCase()
  return players.value.filter(player =>
      player.login.toLowerCase().includes(searchTerm) &&
      (player.countWin + player.countDraw + player.countLose) > 0
  )
})

const fetchTopPlayers = async () => {
  try {
    const response = await api.getTopUsers(limit)
    players.value = response.data.map(p => ({
      ...p,
      totalGames: totalGames(p)
    }))
  } catch (err) {
    error.value = err.message || 'Failed to load players'
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  fetchTopPlayers()
})
</script>