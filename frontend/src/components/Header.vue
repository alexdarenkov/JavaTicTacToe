<template>
  <header class="bg-slate-800 shadow-sm">
    <div class="mx-auto flex items-center justify-between">
      <IconHeaderLogo class="w-16 h-16 top-0 -left-0 absolute"/>

      <!-- Меню -->
      <nav class="hidden md:flex space-x-8 text-white">
        <router-link to="/menu" class="hover:text-[rgb(68,235,153)] cursor-pointer">
          Create game
        </router-link>
        <router-link to="/find-game" class="hover:text-[rgb(68,235,153)] cursor-pointer">
          Find game
        </router-link>
        <router-link to="/" class="hover:text-[rgb(68,235,153)] cursor-pointer">
          Top players
        </router-link>
      </nav>

      <!-- Иконка профиля -->
      <div class="flex items-center mr-4">
        <button @click="profileHandler" class="rounded-full bg-slate-200 h-9 w-9 cursor-pointer">
          <img src="@/assets/user.svg" alt="Профиль" class="h-7 w-7 rounded-full m-auto" />
        </button>
      </div>
    </div>
  </header>
</template>

<script setup>
import IconHeaderLogo from "@/assets/IconHeaderLogo.vue";
import {useRouter} from "vue-router";
const router = useRouter()

const profileHandler = () => {
  router.push('/profile')
};
</script>