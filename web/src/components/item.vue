<template>
  <div
    class="root"
    :style="{ paddingLeft: isEditMode ? '3em' : '' }"
    tabindex="0"
    role="link"
    @click="handleClick(id)"
  >
    <span
      class="checkbox"
      @click="
        (e) => {
          e.stopPropagation()
          addToSelection(id)
        }
      "
      v-if="isEditMode"
    >
      <Checkbox :checked="selection.includes(id)"> </Checkbox>
    </span>
    <div class="header">
      <h2>{{ fullUrl }}</h2>
      <time>
        {{ formatDate(createdAt) }}
      </time>
    </div>
    <div class="short">{{ shortUrl }}</div>
  </div>

  <div class="seq"></div>
  <a-modal
    v-model:visible="dialogVisible"
    title="查看"
    ok-text="确认"
    cancel-text="取消"
    @ok="() => (dialogVisible = false)"
  >
    <p>
      <a
        :href="fullUrl"
        target="_blank"
        @click="(e) => handleOpenLink(fullUrl, e)"
        >原地址: {{ fullUrl }}</a
      >
    </p>
    <p>
      <a :href="shortUrl" @click="(e) => handleOpenLink(shortUrl, e)"
        >短地址: {{ shortUrl }}</a
      >
    </p>
  </a-modal>
</template>

<script lang="ts">
import { EnvStore } from '../store'
import { useInjector } from '../utils/deps-injection'
import { defineComponent, ref } from 'vue'
import { Checkbox } from 'ant-design-vue'
import { EventTypes } from '../constants/event'
export default defineComponent({
  components: { Checkbox },
  name: 'item',
  props: {
    fullUrl: {
      type: String,
      required: true,
    },
    shortUrl: {
      type: String,
      required: true,
    },
    createdAt: {
      type: Date,
      required: true,
    },
    id: {
      type: Number,
      required: true,
    },
  },
  setup() {
    function formatDate(date: string | Date) {
      date = new Date(date)
      return Intl.DateTimeFormat().format(date)
    }
    const envStore = useInjector(EnvStore)

    function addToSelection(id: number) {
      const list = envStore.selectedItem
      const has = list.value.indexOf(+id)
      console.log(has)

      if (has != -1) {
        list.value.splice(has, 1)
      } else list.value.push(+id)
    }
    const dialogVisible = ref(false)
    return {
      formatDate,
      isEditMode: envStore.isEditMode,
      addToSelection,
      selection: envStore.selectedItem,
      handleClick(id: number) {
        window.bus.emit(EventTypes.VISIT, id)
        dialogVisible.value = true
      },
      handleOpenLink(link: string, e: MouseEvent) {
        if (window.Bridge) {
          e.stopPropagation()
          e.preventDefault()
          window.Bridge.openLink(link)
        }
      },

      dialogVisible,
    }
  },
})
</script>

<style scoped="">
.root {
  background: #fff;
  padding: 0.5em 1em;
  transition: padding 0.5s;
  position: relative;
}
.root:focus {
  outline: 0;
}
.root:focus,
.root:active {
  background-color: rgba(238, 238, 238, 0.363);
}
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.header h2 {
  flex-basis: calc(100% - 8rem);
  flex-shrink: 1;
  overflow: hidden;
  text-overflow: ellipsis;
}
time {
  color: #333;
  opacity: 0.8;
  font-size: 0.8em;
}
.seq {
  height: 1px;
  width: 100%;
  background: #bbb;
  transform: scaleY(0.3);
}

h2 {
  margin: 0.3em 0;
  font-weight: 500;
  font-size: 1.3rem;
}

.short {
  opacity: 0.9;
  font-weight: 300;
}

.checkbox {
  position: absolute;
  left: 1em;
  top: 50%;
  transform: translateY(-50%);
}
</style>
