<template>
  <div class="relative min-h-screen bg-gray-s21 overflow-hidden">
    <IconLogo class="w-100 h-100 absolute -top-35 -left-20" />

    <div class="flex items-center justify-center -mt-56">
      <form @submit.prevent="onSubmit" class="relative p-10 w-80 h-100 bg-gradient-to-tr from-[#8139da] to-[#59a9b1] rounded-tl-2xl rounded-br-2xl space-y-6">
        <h2 class="text-xl font-bold mb-6 text-center text-white">{{mainText}}</h2>

        <div>
          <label for="login" class="block text-xs font-medium text-gray-700">Login</label>
          <div class="relative">
            <input
                @focus="emit('update:drawError', false)"
                :value="login"
                @input="emit('update:login', $event.target.value)"
                :class="['mt-1 text-sm block border-2 w-full p-2 pr-10 bg-white rounded-lg focus:outline-none focus:ring focus:ring-purple-800 shadow-sm', drawError ? 'border-red-500' : 'border-white']"
            />
            <IconErrorCross v-if="drawError" class="absolute right-0 mr-3 top-0 w-5 h-5 text-red-500 top-1/2 transform -translate-y-1/2" />
          </div>
        </div>

        <div>
          <label for="password" class="block text-xs font-medium text-gray-700">Password</label>
          <div class="relative">
            <input
                @focus="emit('update:drawError', false)"
                :type="showPassword ? 'text' : 'password'"
                :value="password"
                @input="emit('update:password', $event.target.value)"
                :class="['mt-1 text-sm block border-2 w-full p-2 pr-10 bg-white rounded-lg focus:outline-none focus:ring focus:ring-purple-800 shadow-sm', drawError ? 'border-red-500' : 'border-white']"
            />
            <button
                type="button"
                @click="emit('update:showPassword', !showPassword)"
                class="absolute inset-y-0 right-0 pr-3 text-gray-500 cursor-pointer rounded-xl pl-2"
            >
              <IconCloseEye v-if="showPassword" class="w-5 h-5" />
              <IconErrorCross v-else-if="drawError" class="w-5 h-5 text-red-500 cursor-default" />
              <IconOpenEye v-else class="w-5 h-5" />
            </button>
          </div>
          <p v-if="drawError" class="text-red-500 text-sm mt-1 absolute">{{ error }}</p>
        </div>

        <BaseButton
            :button-text="buttonText"
            type="submit"
            class="w-full mt-2"
        />

        <p class="text-center font-semibold text-sm text-white border-t-1 border-white pt-4">
          Already have an account?
          <router-link :to="redirect" class="text-[rgb(68,235,153)] hover:underline font-medium">{{ linkText }}</router-link>
        </p>

        <img src="@/assets/pattern-rt.svg" alt="pattern-rt" class="absolute w-full h-full lb z-1" />
        <img src="@/assets/pattern-lb.svg" alt="pattern-lb" class="absolute w-full h-full rt z-1" />
      </form>
    </div>
  </div>
</template>

<script setup>
import IconCloseEye from "@/assets/IconCloseEye.vue";
import BaseButton from "@/components/BaseButton.vue";
import IconLogo from "@/assets/IconLogo.vue";
import IconErrorCross from "@/assets/IconErrorCross.vue";
import IconOpenEye from "@/assets/IconOpenEye.vue";

const props = defineProps({
  buttonText: {
    type: String,
    required: true
  },
  linkText: {
    type: String,
    required: true
  },
  login: {
    type: String,
    default: ''
  },
  password: {
    type: String,
    default: ''
  },
  drawError: {
    type: Boolean,
    default: false
  },
  error: {
    type: String,
    default: ''
  },
  showPassword: {
    type: Boolean,
    default: false
  },
  redirect: {
    type: String,
    default: ''
  },
  mainText: {
    type: String,
    default: ''
  }
});

const emit = defineEmits([
  'update:login',
  'update:password',
  'update:showPassword',
  'submit',
  'update:drawError'
]);

const onSubmit = () => {
  emit('submit');
};
</script>

<style>
.bg-gray-s21 {
  background-color: #1d2633;
}

.lb {
  bottom: calc(100% - 18.8%);
  left: calc(100% - 3.4%);
}

.rt {
  top: calc(100% - 12.8%);
  right: calc(100% - 3.4%);
}
</style>