<script setup lang="ts">
import type { TableColumn } from "@nuxt/ui";
import { getPaginationRowModel } from "@tanstack/table-core";
import { useTransactionHistory } from "~/composable/transaction/useTransactionHistory";
import TransactionHistoryRecord from "../ui/transaction-history-record.vue";

const { fetchTransactionHistory, transactionHistory } = useTransactionHistory();

const tableData = computed(() => transactionHistory.value?.data ?? []);

async function refresh() {
  await fetchTransactionHistory();
}

await fetchTransactionHistory();

defineExpose({
  refresh,
});
</script>

<template>
  <template v-for="t in tableData" :transaction-record="t">
    <div class="my-2">
      <TransactionHistoryRecord :transaction-record="t" />
    </div>
  </template>
</template>
