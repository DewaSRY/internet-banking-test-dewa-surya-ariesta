<script setup lang="ts">
import TransactionProfileContainer from "~/components/container/transaction-profile-container.vue";
</script>

<template>
  <UDashboardPanel id="user-dashboard">
    <template #header>
      <UDashboardNavbar title="user-dashboard">
        <template #leading>
          <UDashboardSidebarCollapse />
        </template>
        <template #right> </template>
      </UDashboardNavbar>
    </template>

    <template #body>
      <TransactionProfileContainer />
    </template>
  </UDashboardPanel>
</template>
