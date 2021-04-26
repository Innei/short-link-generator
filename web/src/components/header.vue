<template>
  <header>
    <button class="edit-btn btn" @click="handleEdit">
      {{ !isEdit ? '编辑' : '完成' }}
    </button>
    <button class="delete-btn btn" @click="handleDelete" v-if="isEdit">
      删除
    </button>

    <h1>短连接生成器</h1>
    <div class="seq"></div>
  </header>
</template>

<script lang="ts">
import { defineComponent } from '@vue/runtime-core'
import { EventTypes } from '../constants/event'
import { EnvStore } from '../store'
import { useInjector } from '../utils/deps-injection'

export default defineComponent({
  name: 'header',
  setup() {
    const envStore = useInjector(EnvStore)
    function handleEdit() {
      envStore.isEditMode.value = !envStore.isEditMode.value
    }
    function handleDelete() {
      const list = envStore.selectedItem.value
      window.bus.emit(EventTypes.REMOVE_MANY, list)
      envStore.isEditMode.value = false
    }
    return {
      handleEdit,
      isEdit: envStore.isEditMode,
      handleDelete,
    }
  },
})
</script>

<style scoped="">
header {
  height: 3rem;
  width: 100%;
  position: fixed;
  top: 0;
  backdrop-filter: blur(25px) saturate(150%) brightness(1.1);
  background-color: hsla(0, 0%, 96%, 0.5);
  padding: calc(env(safe-area-inset-top) + 0.5em) 1em 0.5em 1em;
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: bold;
  text-align: center;
  overflow: hidden;
  z-index: 2;
}
h1 {
  font-size: 1.1rem;
}
.seq {
  height: 1px;
  width: 300%;
  background: #bbb;
  transform: scaleY(0.3);
  margin: 0 -99px;
  position: absolute;
  bottom: 0;
}
.btn {
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  border: 0;
  outline: none;
  background: none;
  cursor: pointer;
  transition: opacity 0.5s;
}
.edit-btn {
  left: 1em;
  color: #3498db;
}
.delete-btn {
  right: 1em;
  color: red;
}

.btn:active {
  opacity: 0.8;
}
</style>
