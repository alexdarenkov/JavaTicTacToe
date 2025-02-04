<template>
  <div class="login-container">
    <h2>Вход</h2>
    <p v-if="error" class="error-message">{{ error }}</p>
    <form class="login-form" @submit.prevent="handleSubmit">
      <div class="form-group">
        <label>Логин:</label>
        <input type="text" v-model="login" required />
      </div>
      <div class="form-group">
        <label>Пароль:</label>
        <input type="password" v-model="password" required />
      </div>
      <button type="submit" class="login-button">Войти</button>
    </form>
    <p class="register-link">
      Нет аккаунта? <router-link to="/register">Зарегистрироваться</router-link>
    </p>
  </div>
</template>

<script>
import axios from 'axios';
import { ref } from 'vue';
import { useRouter } from 'vue-router';

export default {
  setup() {
    const login = ref('');
    const password = ref('');
    const error = ref('');
    const router = useRouter();

    const handleSubmit = async () => {
      try {
        const authHeader = `Basic ${btoa(`${login.value}:${password.value}`)}`;
        const response = await axios.post(
            'http://localhost:8080/auth/login',
            {},
            {
              headers: {
                Authorization: authHeader,
              },
            }
        );

        if (response.status === 200) {
          localStorage.setItem('userId', response.data);
          localStorage.setItem('authHeader', authHeader);
          router.push('/game/new');
        }
      } catch (err) {
        error.value = 'Неверный логин или пароль';
      }
    };

    return {
      login,
      password,
      error,
      handleSubmit,
    };
  },
};
</script>

<style scoped>
  @import './styles/auth.css';
</style>