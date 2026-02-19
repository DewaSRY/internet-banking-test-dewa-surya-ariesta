// types/nuxt.d.ts
declare module "#app" {
  interface NuxtApp {
    $api: typeof $fetch;
  }
}
