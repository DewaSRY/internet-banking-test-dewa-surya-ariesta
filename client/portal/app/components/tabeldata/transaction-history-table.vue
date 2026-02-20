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
  <section class="w-full max-w-2xl">
    <div>
        <h2 class="text-primary text-2xl mb-2">
            Transaction History
        </h2>
    </div>
    <template v-for="t in tableData" :transaction-record="t">
      <div class="my-2">
        <TransactionHistoryRecord :transaction-record="t" />
      </div>
    </template>
  </section>
</template>
