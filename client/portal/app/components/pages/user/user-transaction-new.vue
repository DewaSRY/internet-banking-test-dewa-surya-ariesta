<script setup lang="ts">
import TransactionForms from "~/components/forms/transfer-forms.vue";
</script>

<template>
  <UDashboardPanel id="user-transaction">
    <template #header>
      <UDashboardNavbar title="user-transaction">
        <template #leading>
          <UDashboardSidebarCollapse />
        </template>
        <template #right>
    
        </template>
      </UDashboardNavbar>
    </template>

    <template #body>
      <TransactionForms />
    </template>
  </UDashboardPanel>
</template>
