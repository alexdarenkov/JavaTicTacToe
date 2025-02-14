<template>
  <div class="relative">
    <div class="flex flex-row items-center gap-4 p-1 rounded-lg bg-slate-100 pl-4 shadow-sm">
      <span class="text-xs ibm-plex-mono">{{ code }}</span>
      <button
          @click="copyToClipboard"
          class="p-2 rounded-md bg-white hover:bg-purple-700 shadow-sm cursor-pointer text-gray-500 hover:text-white"
          :class="{ 'bg-green-100 hover:bg-green-100': isCopied }"
      >
        <IconCopy v-if="!isCopied" class="h-4 w-4"/>
        <IconOk v-else class="h-4 w-4"/>
      </button>
    </div>

  </div>
</template>

<script setup>
import { ref } from 'vue';
import IconCopy from "@/assets/IconCopy.vue";
import IconOk from "@/assets/IconOk.vue";

const props = defineProps({
  code: {
    type: String,
    required: true
  }
});

const isCopied = ref(false);
const showNotification = ref(false);
const codeBlock = ref(null);

const copyToClipboard = async () => {
  try {
    await navigator.clipboard.writeText(props.code);
    isCopied.value = true;
    showNotification.value = true;

    setTimeout(() => {
      isCopied.value = false;
      showNotification.value = false;
    }, 2000);
  } catch (err) {
    console.error('Ошибка копирования:', err);
  }
};
</script>