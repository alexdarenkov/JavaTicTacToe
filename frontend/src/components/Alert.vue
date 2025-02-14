<template>
  <div v-if="message" class="alert flex items-center gap-3 rounded-lg shadow-sm bg-red-200 p-4 text-sm text-black">
    <IconAlert class="w-5 h-5 text-red-500 flex-shrink-0 ml-1"/>
    <span class="flex-grow text-center">{{ message }}</span>
  </div>
</template>

<script setup>
import { defineProps } from 'vue'
import IconAlert from "@/assets/IconAlert.vue";

const props = defineProps({
  message: {
    type: String,
    required: true
  }
})
</script>