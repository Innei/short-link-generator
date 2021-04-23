<template>
  <div class="root">
    <ul>
      <Item
        :full-url="item.fullUrl"
        :short-url="item.code"
        :created-at="item.createdAt"
        :id="item.uid"
        v-for="item in data"
        :key="item.uid"
      />
    </ul>
  </div>
</template>

<script lang="ts">
import { defineComponent, PropType } from 'vue'
import { UrlModel } from '../models'
import Item from './item.vue'
export default defineComponent({
  components: {
    Item,
  },
  name: 'list',
  props: {
    data: {
      type: (Array as any) as PropType<UrlModel[]>,
      required: true,
    },
  },
})
</script>

<style scoped="">
ul {
  margin: 0;
  padding: 0;
}
.root {
  margin-top: 1.5em;
}
</style>
