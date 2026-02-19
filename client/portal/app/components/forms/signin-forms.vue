<template>
  <div class="w-full max-w-md mx-auto">
    <FormHeaderUi
      title="Welcome Back"
      subtitle="Sign in to your account to continue"
    />

    <UForm
      ref="formRef"
      :schema="SigninRequestSchema"
      :state="formState"
      :validate-on="['blur', 'change', 'input']"
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
  </div>
</template>

<script setup lang="ts">
import EmailInputUi from "../ui/email-input-ui.vue";
import SubmitButtonUi from "../ui/submit-button-ui.vue";
import FormHeaderUi from "../ui/form-header-ui.vue";
import PasswordInputUi from "../ui/password-input-ui.vue";
import type { FormSubmitEvent, FormError, Form } from "@nuxt/ui";

import { API_AUTH_SIGNIN } from "#shared/constants";
import type { JwtResponse, SigninRequest } from "#shared/types";
import { SigninRequestSchema } from "#shared/types";

const { $api } = useNuxtApp();

const defaultState: Partial<SigninRequest> = {};
const formState = reactive<Partial<SigninRequest>>({});

const toast = useToast();
const isLoading = ref(false);
const formRef = ref<Form<SigninRequest> | null>(null);

const isFormInvalid = computed<boolean>(() => {
  if (!formRef.value?.dirty) {
    return false;
  }
  console.log(formRef.value.errors)
  return (formRef.value?.errors?.length ?? 0) > 0;
});

async function onSubmit(event: FormSubmitEvent<SigninRequest>) {
  console.log(event);
  isLoading.value = true;

  try {
    const body: SigninRequest = {
      email: event.data.email,
      password: event.data.password,
    };

    const { data } = await useAsyncData(API_AUTH_SIGNIN, () =>
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
      accessToken.value = data.value.accessToken;
    }

    toast.add({
      title: "Success!",
      description: "Welcome back",
      color: "success",
      icon: "i-heroicons-check-circle",
    });

    // handle navigate
    await navigateTo("/user");
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
