<template>
  <BaseForm
      v-model:login="login"
      v-model:password="password"
      v-model:showPassword="showPassword"
      v-model:drawError="drawError"
      :button-text="'Sign in'"
      :error="error"
      :link-text="'Sign up'"
      :redirect="'/register'"
      :mainText="'Welcome to Tic-tac-toe'"
      @submit="auth"
  />
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'

import api from "@/services/api.js";
import BaseForm from "@/components/BaseForm.vue";

const router = useRouter()
const login = ref('')
const password = ref('')
const error = ref('')
const drawError = ref(false)
const showPassword = ref(false)

const auth = async () => {
  try {
    const response = await api.login({
      login: login.value,
      password: password.value
    })

    if (response.status === 200) {
      localStorage.setItem('accessToken', response.data.accessToken)
      localStorage.setItem('refreshToken', response.data.refreshToken)
      router.push('/menu')
    }

  } catch (err) {
    drawError.value = true
    error.value = 'Invalid login and/or password'
    console.error(err)
  }
}
</script>