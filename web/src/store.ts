import { ref, watch } from 'vue'

export function EnvStore() {
  const isEditMode = ref(false)
  const selectedItem = ref<number[]>([])

  watch(isEditMode, (newValue) => {
    if (!newValue) {
      selectedItem.value.length = 0
    }
  })
  return {
    isEditMode,
    selectedItem,
  }
}
