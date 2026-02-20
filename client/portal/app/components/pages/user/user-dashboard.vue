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
        <template #right>
          <UButton
            label="New Transaction"
            icon="i-lucide-plus"
            to="/user/transaction/new"
            as="NuxtLink"
          />
          <div class=" ml-4 flex gap-2 ">
            <UButton
              icon="i-lucide-house"
              to="/"
              as="NuxtLink"
              color="info"
              variant="outline"
            />
            <UButton
              icon="i-lucide-log-in"
              to="/auth/signin"
              as="NuxtLink"
              color="info"
              variant="outline"
            />
          </div>
        </template>
      </UDashboardNavbar>
    </template>

    <template #body>
      <TransactionProfileContainer />
    </template>
  </UDashboardPanel>
</template>
