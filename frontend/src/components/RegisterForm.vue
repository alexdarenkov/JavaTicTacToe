<template>
  <div class="min-h-screen bg-gray-50 flex items-center justify-center p-4">
    <form @submit.prevent="register" class="bg-white rounded-2xl shadow-lg p-8 w-full max-w-md space-y-6">
      <div class="space-y-2">
        <h1 class="text-3xl font-bold text-gray-900 text-center">Create Account</h1>
        <p class="text-gray-500 text-center">Get started with your free account</p>
      </div>

      <div class="space-y-4">
        <div v-if="error" class="border border-rose-300 rounded-lg bg-rose-200">
          <p class="py-2 text-rose-500 text-center">{{ error }}</p>
        </div>

        <div>
          <input
              v-model="login"
              type="text"
              class="w-full px-4 py-3 rounded-lg border border-gray-200 focus:border-indigo-500 focus:ring-2 focus:ring-indigo-200 transition-all outline-none text-gray-700 placeholder-gray-400"
          >
        </div>

        <div>
          <input
              v-model="password"
              type="password"
              class="w-full px-4 py-3 rounded-lg border border-gray-200 focus:border-indigo-500 focus:ring-2 focus:ring-indigo-200 transition-all outline-none text-gray-700 placeholder-gray-400"
          >
        </div>

        <button
            class="w-full py-3.5 bg-gradient-to-r from-indigo-500 to-violet-600 text-white font-semibold rounded-lg hover:opacity-90 transition-opacity shadow-md cursor-pointer"
        >
          Create Account
        </button>
      </div>

      <p class="text-center text-sm text-gray-600">
        Already have an account?
        <router-link to="/login" class="text-indigo-600 hover:underline font-medium">Sign in</router-link>
      </p>
    </form>
  </div>
</template>

<script setup>
import axios from 'axios'
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import api from "@/services/api.js";

const login = ref('')
const password = ref('')
const error = ref('')
const router = useRouter()

const register = async () => {
  try {
    const response = await api.register( {
      login: login.value,
      password: password.value
    })

    if (response.status === 200) {
      router.push('/login')
    }

  } catch (err) {
    error.value = 'This login is already taken'
    console.error(err)
  }
}
</script>