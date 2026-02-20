<script setup lang="ts">
interface Props {
  transactionRecord: TransactionHistoryRecord;
}
const props = defineProps<Props>();

function parseDate(date: Date) {
  return new Date(date).toLocaleDateString("id-ID", {
    day: "2-digit",
    month: "short",
    year: "numeric",
  });
}
const badgeColor: globalThis.ComputedRef<
  "error" | "success" | "primary" | "secondary" | "info" | "warning" | "neutral"
> = computed(() => {
  switch (props.transactionRecord.transactionEnum) {
    case "TRANSFER":
      return "primary";
    case "DEPOSIT":
      return "success";
    case "WITHDRAW":
      return "neutral";
    default:
      return "info";
  }
});
</script>
<template>
  <UCard class="w-full">
    <div class="grid grid-cols-5 gap-6 items-center">
      <!-- LEFT SIDE -->
      <div class="col-span-2 space-y-4">
        <!-- Transaction Type -->
        <UBadge :color="badgeColor" variant="soft" size="sm">
          {{ props.transactionRecord.transactionEnum }}
        </UBadge>

        <!-- From User -->
        <div class="flex items-center gap-3">
          <UAvatar :alt="props.transactionRecord.userFrom.username" size="md" />

          <div>
            <div>
              <UBadge color="neutral" variant="outline" size="sm">
                From
              </UBadge>
            </div>
            <p class="font-semibold">
              {{ props.transactionRecord.userFrom.username }}
            </p>
            <p class="text-sm text-gray-500">
              {{ props.transactionRecord.userFrom.email }}
            </p>
          </div>
        </div>

        <!-- To User -->
        <div
          v-if="props.transactionRecord.userTo"
          class="flex items-center gap-3"
        >
          <UAvatar :alt="props.transactionRecord.userTo.username" size="md" />
          <div>
            <UBadge color="neutral" variant="outline" size="sm"> TO </UBadge>
            <p class="font-semibold">
              {{ props.transactionRecord.userTo.username }}
            </p>
            <p class="text-sm text-gray-500">
              {{ props.transactionRecord.userTo.email }}
            </p>
          </div>
        </div>
      </div>

      <!-- RIGHT SIDE -->
      <div class="col-span-3 space-y-4 text-right">
        <div>
          <p class="text-sm text-gray-500">Date</p>
          <p class="font-medium">
            {{ parseDate(props.transactionRecord.createdAt) }}
          </p>
        </div>

        <USeparator />

        <div>
          <p class="text-sm text-gray-500">Amount</p>
          <p class="text-xl font-bold text-primary">
            {{ props.transactionRecord.amount }}
          </p>
        </div>
      </div>
    </div>
  </UCard>
</template>
