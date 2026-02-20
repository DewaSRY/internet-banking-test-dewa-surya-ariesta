<script setup lang="ts"></script>

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

    <template #body> user dashboard </template>
  </UDashboardPanel>
</template>
