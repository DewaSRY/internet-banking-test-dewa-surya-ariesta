export default defineNuxtPlugin(() => {
  const config = useRuntimeConfig();

  const api = $fetch.create({
    baseURL: (config.public.apiBaseUrl as string) ?? "",
    onRequest({ options }) {
      const token = useCookie("access_token").value;

      if (token) {
        const headers = new Headers(options.headers || {});
        headers.set("Authorization", `Bearer ${token}`);

        options.headers = headers;
      }
    },

    onResponseError: ({ response }) => {
      if (response.status === 401) {
        useCookie("access_token").value = null;
        navigateTo("/login");
        return;
      }
    },
  });

  return {
    provide: {
      api,
    },
  };
});
