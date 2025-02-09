<template>
  <div class="game-container">
    <h1 class="text-3xl font-bold mb-6 text-gray-800">Tic-Tac-Toe</h1>

    <div v-if="loading" class="text-lg text-gray-600">Загрузка...</div>

    <div v-else class="space-y-6">
      <div class="grid grid-cols-3 gap-3 w-72 h-72 mx-auto">
        <div
            v-for="(row, rowIndex) in field"
            :key="rowIndex"
            class="contents"
        >
          <button
              v-for="(cell, colIndex) in row"
              :key="colIndex"
              @click="handleMove(rowIndex, colIndex)"
              :disabled="isDisabled(rowIndex, colIndex)"
              class="w-24 h-24 flex items-center justify-center text-4xl font-bold border-2 border-gray-200 rounded-lg hover:bg-gray-50 transition-all"
              :class="{
              'bg-green-100': cell === 1,
              'bg-red-100': cell === 2,
              'opacity-50 cursor-not-allowed': isDisabled(rowIndex, colIndex)
            }"
          >
            <span class="transform transition-transform hover:[&_.symbol]:scale-110">
              {{ cellSymbol(cell) }}
            </span>
          </button>
        </div>
      </div>

      <div v-if="gameMessage" class="text-center space-y-4">
        <p class="text-lg font-medium">{{ gameMessage }}</p>
        <button
            @click="handleReset"
            class="px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600 transition-colors"
        >
          Новая игра
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue';

const props = defineProps({
  field: Array,
  loading: Boolean,
  gameState: String,
  currentPlayer: Number,
  playerIcon: Number
});

const emit = defineEmits(['move', 'reset']);

const cellSymbol = (cell) => {
  return cell === 1 ? '✕' : cell === 2 ? '○' : '';
};

const gameMessage = computed(() => {
  const messages = {
    'FIRST_PLAYER_WON': 'Победил игрок 1!',
    'SECOND_PLAYER_WON': 'Победил игрок 2!',
    'DRAW': 'Ничья!',
    'IN_PROGRESS': ''
  };
  return messages[props.gameState] || '';
});

const isDisabled = (row, col) => {
  return props.field[row][col] !== 0 ||
      props.gameState !== 'IN_PROGRESS' ||
      props.currentPlayer !== props.playerIcon;
};

const handleMove = (row, col) => {
  if (!isDisabled(row, col)) {
    emit('move', { row, col });
  }
};

const handleReset = () => {
  emit('reset');
};
</script>