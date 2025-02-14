<template>
  <BaseForm
      v-model:login="login"
      v-model:password="password"
      v-model:showPassword="showPassword"
      v-model:drawError="drawError"
      :button-text="'Sign up'"
      :error="error"
      :link-text="'Sign in'"
      :redirect="'/login'"
      :mainText="'Registration'"
      @submit="register"
  />
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import api from "@/services/api.js";
import BaseForm from "@/components/BaseForm.vue";

const login = ref('');
const password = ref('');
const error = ref('');
const drawError = ref(false);
const showPassword = ref(false);
const router = useRouter();

const register = async () => {
  try {
    const response = await api.register({
      login: login.value,
      password: password.value,
    });

    if (response.status === 200) {
      router.push('/login');
    }
  } catch (err) {
    drawError.value = true;
    error.value = 'This login is already taken';
    console.error(err);
  }
};
</script>