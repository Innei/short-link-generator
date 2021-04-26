<template>
  <Header />
  <div class="padding"></div>
  <List :data="data" />
  <FloatButton @click="handleAdd"></FloatButton>
  <a-modal
    v-model:visible="createDialogVisible"
    title="创建"
    ok-text="确认"
    cancel-text="取消"
    @ok="handleAdd"
  >
    <a-input
      v-model:value="url"
      placeholder="输入一个 URL"
      @change="handleCheck"
    />
    <span class="text-red-300" ref="tip"></span>
  </a-modal>
</template>

<script lang="ts">
import { defineComponent, onMounted, onUnmounted, ref } from 'vue'
import 'normalize.css'
import Header from './components/header.vue'
import List from './components/list.vue'
import FloatButton from './components/float-button.vue'
import { useInjector, useProvider } from './utils/deps-injection'
import { EnvStore } from './store'
import { EventTypes } from './constants/event'
import { UrlModel } from 'models'
import { Modal } from 'ant-design-vue'
export default defineComponent({
  name: 'App',
  components: { Header, List, FloatButton },
  setup() {
    useProvider(EnvStore)
    const data = ref<UrlModel[]>([])
    const tip = ref<HTMLSpanElement>()
    const url = ref('https://innei.ren')
    const createDialogVisible = ref(false)
    const handler_FETCH_ALL = (payload) => {
      // console.log(payload)
      data.value = payload
    }
    const handler_APPEND = (payload) => {
      const newData = payload as UrlModel
      data.value.unshift(newData)
    }
    const handler_REMOVE_MANY = (payload) => {
      // console.log(payload)
      data.value = data.value.filter(({ uid }) => !payload.includes(uid))
    }
    onMounted(() => {
      window.bus.on(EventTypes.FETCH_ALL, handler_FETCH_ALL)
      window.bus.on(EventTypes.APPEND, handler_APPEND)
      window.bus.on(EventTypes.REMOVE_MANY, handler_REMOVE_MANY)
    })

    onUnmounted(() => {
      window.bus.off(EventTypes.FETCH_ALL, handler_FETCH_ALL)
      window.bus.off(EventTypes.APPEND, handler_APPEND)
      window.bus.off(EventTypes.REMOVE_MANY, handler_REMOVE_MANY)
    })

    // window.bus.on('dispatch', (e) => {
    //   data.value = e
    // })
    return {
      data,
      tip,
      createDialogVisible,
      url,
      handleAdd() {
        if (!createDialogVisible.value) {
          createDialogVisible.value = true
        } else {
          if (tip.value?.innerText) {
            return
          }
          createDialogVisible.value = false
          window.bus.emit(EventTypes.WANT_CREATE, { data: url.value })
        }
      },
      handleCheck() {
        const _url = url.value
        try {
          new URL(_url)
          tip.value!.innerText = ''
        } catch {
          tip.value!.innerText = 'URL 有误'
        }
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
