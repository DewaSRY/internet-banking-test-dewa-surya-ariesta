<template>
  <div class="w-full max-w-md mx-auto">
    <FormHeaderUi
      title="Welcome Back"
      subtitle="Sign in to your account to continue"
    />

    <UForm
      :schema="SignupFormSchema"
      :state="formState"
      class="space-y-4"
      @submit="onSubmit"
    >
      <div class="flex flex-col gap-2">
        <EmailInputUi
          v-model="formState.email"
          label="Email Address"
          placeholder="you@example.com"
          name="email"
          required
        />

        <PasswordInputUi
          v-model="formState.password"
          label="Password"
          placeholder="Enter your password"
          name="password"
          required
        />

        <SubmitButtonUi
          label="Sign In"
          :loading="isLoading"
          :disabled="isFormInvalid || isLoading"
        />
      </div>
    </UForm>
    <div class="w-full p-4">
      <RefreshButton />
    </div>
  </div>
</template>

<script setup lang="ts">
import PasswordInputUi from "$components/ui/password-input-ui.vue";
import EmailInputUi from "$components/ui/email-input-ui.vue";
import SubmitButtonUi from "$components/ui/submit-button-ui.vue";
import FormHeaderUi from "$components/ui/form-header-ui.vue";
import type { FormSubmitEvent, FormError, Form } from "@nuxt/ui";

import { API_AUTH_SIGNIN } from "#shared/constants";
import type {  JwtResponse , SigninRequest} from "#shared/types";
import { SignupFormSchema } from "#shared/types";
import RefreshButton from "../ui/refresh-button.vue";

const { $api } = useNuxtApp();

const defaultState: Partial<SigninRequest> = {};
const formState = reactive<Partial<SigninRequest>>({
  password: undefined,
  email: undefined,
});

const toast = useToast();
const isLoading = ref(false);
const formRef = ref<Form<SigninRequest> | null>(null);

const isFormInvalid = computed<boolean>(() => {
  if (!formRef.value?.dirty) {
    return false;
  }
  return (formRef.value?.errors?.length ?? 0) > 0;
});

async function onSubmit(event: FormSubmitEvent<SigninRequest>) {
  isLoading.value = true;

  try {
    const body: SigninRequest = {
      email: event.data.email,
      password: event.data.password,
    };

  const { data } = await useAsyncData("users", () =>
      $api<JwtResponse>(API_AUTH_SIGNIN, {
        method: "POST",
        body: body,
      }),
    );

    if (data.value) {
      const accessToken = useCookie<string>("access_token", {
        maxAge: 60 * 60 * 24, 
        sameSite: "lax",
        secure: process.env.NODE_ENV === "production",
      });

      accessToken.value = data.value.refreshToken;
    }

    toast.add({
      title: "Success!",
      description: "Welcome back to ArusKu!",
      color: "success",
      icon: "i-heroicons-check-circle",
    });

     // handle navigate


  } catch (error) {
    toast.add({
      title: "Sign In Failed",
      description: "Invalid email or password. Please try again.",
      color: "error",
      icon: "i-heroicons-x-circle",
    });
  } finally {
    isLoading.value = false;
    Object.assign(formState, defaultState);
  }
}
</script>
