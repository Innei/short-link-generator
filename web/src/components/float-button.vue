<template>
  <button
    @click="$emit('click')"
    class="text-4xl flex items-center justify-center font-mono"
  >
    +
  </button>
</template>

<script lang="ts">
import { defineComponent } from 'vue'

export default defineComponent({
  name: 'float-btn',
  emits: ['click'],
})
</script>

<style scoped>
button {
  transition: all 0.5s;
  position: fixed;
  height: 60px;
  width: 60px;
  padding: 18px;
  border-radius: 50%;
  box-shadow: 3px 3px 6px -4px #333;
  border: 0;
  color: #fff;
  background: #3498db;
  display: flex;
  align-items: center;
  justify-content: center;
  right: 10px;
  bottom: calc(env(safe-area-inset-bottom) + 40px);
}
button:active {
  opacity: 0.6;
}
button:focus {
  outline: none;
}
</style>
