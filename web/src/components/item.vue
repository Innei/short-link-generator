<template>
  <div class="root" tabindex="0" role="link">
    <div class="header">
      <h2>{{ fullUrl }}</h2>
      <time>
        {{ formatDate(createdAt) }}
      </time>
    </div>
    <div class="short">{{ shortUrl }}</div>
  </div>

  <div class="seq"></div>
</template>

<script lang="ts">
import { defineComponent } from 'vue'

export default defineComponent({
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
  },
  setup() {
    function formatDate(date: string | Date) {
      date = new Date(date)
      return Intl.DateTimeFormat().format(date)
    }

    return {
      formatDate,
    }
  },
})
</script>

<style scoped="">
.root {
  background: #fff;
  padding: 0.5em 1em;
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
</style>
