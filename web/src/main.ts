import { createApp } from 'vue'
import App from './App.vue'
// import 'ant-design-vue/lib/checkbox/style/index.css'
// import 'ant-design-vue/lib/modal/style/index.css'
// import 'ant-design-vue/lib/button/style/index.css'
import 'ant-design-vue/dist/antd.css'
import 'virtual:windi.css'
import ADV from 'ant-design-vue'
// if (import.meta.env.mode === 'development' || import.meta.env.DEV) {
//   import('./utils/event')
// }

const app = createApp(App)
app.use(ADV)
app.mount('#app')
