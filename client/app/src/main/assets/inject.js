// @ts-nocheck
;(() => {
  class Observable {
    constructor() {
      this.observers = {}
    }

    on(event, handler) {
      const queue = this.observers[event]
      if (!queue) {
        this.observers[event] = [handler]
        return
      }
      const isExist = queue.some((func) => {
        return func === handler
      })
      if (!isExist) {
        this.observers[event].push(handler)
      }
    }

    emit(event, payload) {
      if ('Bridge' in window) {
        console.log(event, payload)
        window.Bridge.ipcEmitter(event, payload)
      }
      const queue = this.observers[event]
      if (!queue) {
        return
      }
      for (const func of queue) {
        func.call(this, payload)
      }
    }

    off(event, handler) {
      const queue = this.observers[event]
      if (!queue) {
        return
      }
      if (handler) {
        const index = queue.findIndex((func) => {
          return func === handler
        })
        if (index !== -1) {
          queue.splice(index, 1)
        }
      } else {
        queue.length = 0
      }
    }
  }

  window.bus = new Observable()
  console.log('event bus mounted')
})()
