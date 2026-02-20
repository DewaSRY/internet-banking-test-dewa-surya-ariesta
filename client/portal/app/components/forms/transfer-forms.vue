<template>
  <div class="w-full max-w-md mx-auto">
    <FormHeaderUi
      title="Create Transaction"
      subtitle="Create Transaction base on transaction type"
    />

    <UForm
      ref="formRef"
      :schema="CreateTransactionFormSchema"
      :state="formState"
      :validate-on="['blur', 'change', 'input']"
      class="space-y-4"
      @submit="onSubmit"
    >
      <div class="flex flex-col gap-2">
        <SelectInputUI
          :items="transactionOptions"
          v-model="formState.transactionEnum"
          label="Transaction Type "
          placeholder="Select the transaction "
          name="email"
          required
          @update:modelValue="handleChangeTransactionType"
        />

        <EmailInputUi
          v-if="isTransfer"
          v-model="formState.email"
          label="Email Address"
          placeholder="Entered your Target email"
          name="email"
          required
        />

        <NumberInputUi
          v-model="formState.amount"
          label="Amount"
          placeholder="Entered your amount"
          icon="mdi-light-alarm-panel"
          name="amount"
          required
        />

        <SubmitButtonUi
          label="Submit"
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
import NumberInputUi from "../ui/number-input-ui.vue";
import SelectInputUI from "../ui/select-input-ui.vue";
import { FetchError } from "ofetch";

import type { FormSubmitEvent, Form } from "@nuxt/ui";

import { API_TRANSACTION } from "#shared/constants";
import type {
  CreateTransactionForm,
  CreateTransaction,
  TransactionEnum,
  CommonResponseMessage,
} from "#shared/types";
import {
  TransactionEnumSchema,
  CreateTransactionFormSchema,
} from "#shared/types";

const { $api } = useNuxtApp();

const defaultState: Partial<CreateTransaction> = {};
const formState = reactive<Partial<CreateTransaction>>({});
const toast = useToast();
const isLoading = ref(false);
const formRef = ref<Form<CreateTransaction> | null>(null);
const isTransfer = ref(false);

const transactionOptions = computed(() =>
  TransactionEnumSchema.options
    .filter((x) => x !== "RECEIVE")
    .map((x) => x.toString()),
);

const isFormInvalid = computed<boolean>(() => {
  if (!formRef.value?.dirty) {
    return false;
  }
  return (formRef.value?.errors?.length ?? 0) > 0;
});

function handleChangeTransactionType(type: string | undefined) {
  if (type && type === "TRANSFER") {
    isTransfer.value = true;
  } else {
    isTransfer.value = false;
  }
}

async function onSubmit(event: FormSubmitEvent<CreateTransactionForm>) {
  isLoading.value = true;

  try {
    const body: CreateTransaction = {
      amount: Number(event.data.amount),
      transactionEnum: event.data.transactionEnum as TransactionEnum,
      email: event.data.email,
    };

    const response = await $api<CommonResponseMessage>(API_TRANSACTION, {
      method: "POST",
      body: body,
    });

    toast.add({
      title: "Success!",
      description: "Welcome back",
      color: "success",
      icon: "i-heroicons-check-circle",
    });

    await navigateTo("/user/transaction");
  } catch (error) {
    let message = "Something went wrong. Please try again.";

    if (error instanceof FetchError) {
      message = (error.data as any)?.message || error.statusMessage || message;
    }

    toast.add({
      title: "Transaction Failed",
      description: message,
      color: "error",
      icon: "i-heroicons-x-circle",
    });
  } finally {
    isLoading.value = false;
    Object.assign(formState, defaultState);
  }
}
</script>
