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
        <template #right>
          <UButton
            label="New Transaction"
            icon="i-lucide-plus"
            to="/user/transaction/new"
            as="NuxtLink"
          />
        </template>
      </UDashboardNavbar>
    </template>

    <template #body>
      <UContainer>
        <TransactionHistoryTable />
      </UContainer>
    </template>
  </UDashboardPanel>
</template>
