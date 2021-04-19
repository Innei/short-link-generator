<template>
  <Header />
  <div class="padding"></div>
  <List
    :data="[
      {
        fullUrl: 'https://111111aaaaaaaaaaaaaa111.com',
        uid: 1,
        code: 'https://x.c/1',
        createdAt: new Date(),
      },
      {
        fullUrl: 'https://111111aaaaaaaaaaaaaa111.com',
        uid: 2,
        code: 'https://x.c/1',
        createdAt: new Date(),
      },
      {
        fullUrl: 'https://111111aaaaaaaaaaaaaa111.com',
        uid: 3,
        code: 'https://x.c/1',
        createdAt: new Date(),
      },
      {
        fullUrl: 'https://111111aaaaaaaaaaaaaa111.com',
        uid: 4,
        code: 'https://x.c/1',
        createdAt: new Date(),
      },
    ]"
  />
  <FloatButton @click="handleAdd"></FloatButton>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue'
import 'normalize.css'
import Header from './components/header.vue'
import List from './components/list.vue'
import FloatButton from './components/float-button.vue'
export default defineComponent({
  name: 'App',
  components: { Header, List, FloatButton },
  setup() {
    const data = ref('')
    window.bus.on('dispatch', (e) => {
      data.value = e
    })
    return {
      data,
      handleAdd() {
        console.log('add')
      },
    }
  },
})
</script>

<style>
html,
body {
  margin: 0;
  padding: 0;
  font-size: 16px;
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen,
    Ubuntu, Cantarell, 'Open Sans', 'Helvetica Neue', sans-serif;
}

body {
  min-height: 100vh;
  background-color: #f3f2f8;
}
</style>

<style scoped>
.padding {
  padding-top: 3rem;
}
</style>
