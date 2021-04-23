declare class EventBus {
  private observers
  // on(event: EventTypes, handler: any): void
  on(event: string, handler: any): void
  emit(event: string, payload?: any): void
  // emit(event: EventTypes, payload?: any): void
  off(event: string, handler?: (...rest: any) => void): void
}

declare interface Window {
  bus: EventBus
}
