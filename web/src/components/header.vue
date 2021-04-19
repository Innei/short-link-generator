<template>
  <header>
    <h1>短连接生成器</h1>
    <div class="seq"></div>
  </header>
</template>

<script lang="ts">
import { defineComponent } from '@vue/runtime-core'

export default defineComponent({
  name: 'header',
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
  padding: 0.5em 1em;
  font-weight: bold;
  text-align: center;
  overflow: hidden;
}
h1 {
  font-size: 1.2rem;
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
</style>
