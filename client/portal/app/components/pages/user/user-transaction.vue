<script setup lang="ts">
import TransactionHistoryTable from "~/components/tabeldata/transaction-history-table.vue";
</script>

<template>
  <UDashboardPanel id="user-transaction">
    <template #header>
      <UDashboardNavbar title="user-transaction">
        <template #leading>
          <UDashboardSidebarCollapse />
        </template>
        <template #right> </template>
      </UDashboardNavbar>
    </template>

    <template #body>
      <TransactionHistoryTable />
    </template>
  </UDashboardPanel>
</template>
