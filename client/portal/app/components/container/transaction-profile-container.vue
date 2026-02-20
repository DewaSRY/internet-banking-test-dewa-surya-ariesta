<script setup lang="ts">
import { useTransactionProfile } from "~/composable/transaction/useTransactionProfile";
import TransactionCardUi from "../ui/transaction-card-ui.vue";
const { fetchTransactionProfile, transactionProfile } = useTransactionProfile();

await fetchTransactionProfile();
</script>

<template>
  <UContainer>
    <TransactionCardUi
      v-if="transactionProfile"
      :transactionProfile="transactionProfile"
    />
  </UContainer>
</template>
