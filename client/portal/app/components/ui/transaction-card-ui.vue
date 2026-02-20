<script setup lang="ts">
interface Props {
  transactionProfile: TransactionProfileResponse;
}

const props = defineProps<Props>();

const formatCurrency = (value: number) =>
  new Intl.NumberFormat("id-ID", {
    style: "currency",
    currency: "IDR",
  }).format(value);
</script>

<template>
  <UCard class="w-full">
    <template #header>
      <div class="flex items-center justify-between">
        <h2 class="text-lg font-semibold">Transaction Summary</h2>
        <UBadge color="primary" variant="soft">Overview</UBadge>
      </div>
    </template>

    <div class="mb-6 rounded-2xl bg-primary-50 dark:bg-primary-900/20 p-6">
      <div class="flex items-center gap-3">
        <UIcon name="i-lucide-wallet" class="w-8 h-8 text-primary" />
        <div>
          <p class="text-sm text-gray-500 dark:text-gray-400">Total Balance</p>
          <p class="text-2xl font-bold text-primary">
            {{ formatCurrency(props.transactionProfile.total) }}
          </p>
        </div>
      </div>
    </div>

    <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4">
      <div class="rounded-xl border p-4 flex items-center gap-3">
        <UIcon
          name="i-lucide-arrow-down-circle"
          class="w-6 h-6 text-green-500"
        />
        <div>
          <p class="text-sm text-gray-500">Deposit</p>
          <p class="font-semibold">
            {{ formatCurrency(props.transactionProfile.deposit) }}
          </p>
        </div>
      </div>

      <div class="rounded-xl border p-4 flex items-center gap-3">
        <UIcon name="i-lucide-arrow-up-circle" class="w-6 h-6 text-red-500" />
        <div>
          <p class="text-sm text-gray-500">Transfer</p>
          <p class="font-semibold">
            {{ formatCurrency(props.transactionProfile.transfer) }}
          </p>
        </div>
      </div>

      <div class="rounded-xl border p-4 flex items-center gap-3">
        <UIcon name="i-lucide-inbox" class="w-6 h-6 text-blue-500" />
        <div>
          <p class="text-sm text-gray-500">Received</p>
          <p class="font-semibold">
            {{ formatCurrency(props.transactionProfile.received) }}
          </p>
        </div>
      </div>
    </div>
  </UCard>
</template>
