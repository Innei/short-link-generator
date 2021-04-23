<template>
  <Header />
  <div class="padding"></div>
  <List :data="data" />
  <FloatButton @click="handleAdd"></FloatButton>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue'
import 'normalize.css'
import Header from './components/header.vue'
import List from './components/list.vue'
import FloatButton from './components/float-button.vue'
import { useInjector, useProvider } from './utils/deps-injection'
import { EnvStore } from './store'
export default defineComponent({
  name: 'App',
  components: { Header, List, FloatButton },
  setup() {
    useProvider(EnvStore)

    const data = ref(
      Array.from({ length: 100 }, (_, i) => {
        return {
          fullUrl: 'https://111111aaaaaaaaaaaaaa111.com',
          uid: i,
          code: 'https://x.c/1',
          createdAt: new Date(),
        }
      }),
    )
    // window.bus.on('dispatch', (e) => {
    //   data.value = e
    // })
    return {
      data,
      handleAdd() {
        window.bus.emit(EventTypes.WANT_CREATE)
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
