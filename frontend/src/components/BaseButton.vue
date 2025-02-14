<template>
  <button
      @click="handleClick"
      class="bg-[rgb(68,235,153)] hover:bg-purple-600 text-black hover:text-white active:bg-purple-800 py-2 px-6 rounded-lg cursor-pointer shadow-sm"
  >
    {{ buttonText }}
  </button>
</template>

<script setup>
const props = defineProps({
  buttonText: {
    type: String,
    default: 'Button',
  },
  onClick: {
    type: Function,
    default: () => {},
  },
})

const handleClick = () => {
  props.onClick()
}
</script>