<template>
  <div class="min-h-screen bg-gray-50 flex items-center justify-center p-4">
    <form @submit.prevent="auth" class="bg-white rounded-2xl shadow-lg p-8 w-full max-w-md space-y-6">
      <div class="space-y-2">
        <h1 class="text-3xl font-bold text-gray-900 text-center">Welcome Back</h1>
        <p class="text-gray-500 text-center">Please sign in to continue</p>
      </div>

      <div class="space-y-4">
        <div v-if="error" class="border border-rose-300 rounded-lg bg-rose-200">
          <p class="py-2 text-rose-500 text-center">{{ error }}</p>
        </div>

        <div>
          <input
              v-model="login"
              placeholder="Email Address"
              class="w-full px-4 py-3 rounded-lg border border-gray-200 focus:border-indigo-500 focus:ring-2 focus:ring-indigo-200 transition-all outline-none text-gray-700 placeholder-gray-400"
          >
        </div>
        <div>
          <input
              v-model="password"
              placeholder="Password"
              class="w-full px-4 py-3 rounded-lg border border-gray-200 focus:border-indigo-500 focus:ring-2 focus:ring-indigo-200 transition-all outline-none text-gray-700 placeholder-gray-400"
          >
        </div>
        <button
            type="submit"
            class="w-full py-3.5 bg-gradient-to-r from-indigo-500 to-violet-600 text-white font-semibold rounded-lg hover:opacity-90 transition-opacity shadow-md cursor-pointer"
        >
          Sign In
        </button>
      </div>

      <p class="text-center text-sm text-gray-600">
        Don't have an account?
        <router-link to="/register" class="text-indigo-600 hover:underline font-medium">Sign up</router-link>
      </p>
    </form>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import api from "@/services/api.js";

const router = useRouter()
const login = ref('')
const password = ref('')
const error = ref('')

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
    error.value = 'Login and password are incorrect'
    console.error(err)
  }
}
</script>