import { createApp } from 'vue'
import App from './App.vue'

if (import.meta.env.mode === 'development') {
  import('./utils/event')
}

createApp(App).mount('#app')
