import { createApp } from 'vue'
import App from './App.vue'
import 'ant-design-vue/lib/checkbox/style/index.css'
import 'virtual:windi.css'
if (import.meta.env.mode === 'development' || import.meta.env.DEV) {
  import('./utils/event')
}

createApp(App).mount('#app')
