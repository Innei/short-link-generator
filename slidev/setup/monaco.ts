import { defineMonacoSetup } from '@slidev/types'
import { language } from 'monaco-languages/release/esm/kotlin/kotlin'
export default defineMonacoSetup(async (monaco) => {
  monaco.languages.register({
    id: 'kt',
  })
  monaco.languages.setMonarchTokensProvider('kt', language)
  monaco.editor.EditorOptions.cursorStyle.defaultValue = 4
  monaco.editor.EditorOptions.cursorBlinking.defaultValue = 2
  // monaco.editor.setTheme('vs')
})
