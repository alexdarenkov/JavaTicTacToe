<template>
  <div class="text-center">
    <h1 class="text-2xl font-bold">Welcome!</h1>
    <p>You are successfully logged in.</p>
    <p>{{str}}</p>
  </div>
</template>

<script setup>
import {onMounted, ref} from "vue"
import api from "@/services/api.js";

const str = ref('')

onMounted(async () => {
  try {
    str.value = await api.getHello();
  } catch (error) {
    console.error('Token refresh failed', error)
  }
});
</script>