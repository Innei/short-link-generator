<template>
  <p>HHHHHHHHH</p>
  <p>{{ data }}</p>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue'

export default defineComponent({
  name: 'App',
  setup() {
    const data = ref('')
    window.bus.on('dispatch', (e) => {
      data.value = e
    })
    return {
      data,
    }
  },
})
</script>

<style>
html,
body {
  margin: 0;
  padding: 0;
}
</style>
