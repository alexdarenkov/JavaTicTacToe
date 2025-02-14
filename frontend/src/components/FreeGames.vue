<template>
  <Header />
  <div class="m-auto w-min p-4 bg-[#f4f4f9] rounded-xl shadow-sm mt-8">
    <section class="space-y-4">
      <h2 class="text-lg text-center font-semibold text-gray-800">Free games</h2>

      <div class="group flex flex-row items-center gap-3 p-3 rounded-md w-full bg-slate-200 shadow-sm">
        <IconSearch class="w-4 h-4 ml-2 text-slate-400 group-focus-within:text-[rgb(68,235,153)]"/>
        <input
            v-model="searchLogin"
            placeholder="Search by login"
            class="text-sm focus:outline-none text-slate-600 w-full"
        />
      </div>

      <div
          v-if="filteredGames.length === 0"
          class="p-3 w-142 text-center rounded-lg bg-gray-white shadow-sm"
      >
        {{ freeGames.length === 0 ? 'No games available' : 'Not found' }}
      </div>

      <ul v-else class="space-y-2">
        <li
            v-for="game in filteredGames"
            :key="game.id"
            class="flex items-center flex-row gap-3 p-3 bg-white rounded-lg shadow-sm"
        >
          <div class="bg-slate-100 rounded-lg shadow-md p-3 w-30">
            <p class="text-center text-xs ibm-plex-mono text-slate-700 truncate"> {{ game.login }} </p>
          </div>

          <CopyCodeBlock
              :code="String(game.id)"
              class="w-max"
          />

          <button
              @click="joinGame(game.id)"
              class="bg-[rgb(68,235,153)] hover:bg-purple-600 text-black hover:text-white active:bg-purple-800 py-2 px-6 rounded-lg cursor-pointer shadow-sm"
          >
            Join
          </button>
        </li>
      </ul>
    </section>
  </div>
</template>

<script setup>
import Header from "@/components/Header.vue";
import api from "@/services/api.js";
import { computed, onMounted, ref } from "vue";
import { useRouter } from "vue-router";
import CopyCodeBlock from "@/components/CopyCodeBlock.vue";
import IconSearch from "@/assets/IconSearch.vue";

const freeGames = ref([]);
const searchLogin = ref('');
const errorMessage = ref('');
const router = useRouter();

// Фильтрация с точным совпадением
const filteredGames = computed(() => {
  const searchTerm = searchLogin.value.toLowerCase();
  return freeGames.value.filter(game =>
      game.login.toLowerCase().includes(searchTerm)
  );
});

const fetchFreeGames = async () => {
  try {
    const response = await api.getFreeGames();
    freeGames.value = response.data;
  } catch (error) {
    console.error('Ошибка при получении списка игр:', error);
    errorMessage.value = 'Error getting list of games';
  }
};

const joinGame = async (gameId) => {
  try {
    const response = await api.joinGame(gameId);
    if (response.status === 200) {
      router.push({ name: 'Game', params: { id: gameId } });
    } else {
      errorMessage.value = 'Error joining the game';
    }
  } catch (error) {
    console.error('Ошибка сети:', error);
    errorMessage.value = 'Error joining the game';
  }
};

onMounted(() => {
  fetchFreeGames();
});
</script>
