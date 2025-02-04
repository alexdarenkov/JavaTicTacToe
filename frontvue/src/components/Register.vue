<template>
  <div class="login-container">
    <h2>Регистрация</h2>
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
      <button type="submit" class="login-button">Зарегистрироваться</button>
    </form>
    <p class="register-link">
      Уже есть аккаунт? <router-link to="/login">Войти</router-link>
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
        const token = btoa(`${login.value}:${password.value}`);
        const response = await axios.post(
            'http://localhost:8080/auth/register',
            { login: login.value, password: password.value },
            {
              headers: {
                'Content-Type': 'application/json',
                Authorization: `Basic ${token}`,
              },
            }
        );

        if (response.status === 200) {
          router.push('/login');
        }
      } catch (err) {
        error.value = 'Логин или пароль заняты';
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