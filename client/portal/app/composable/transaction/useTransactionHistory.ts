import { API_TRANSACTION_HISTORY } from "#shared/constants/api-phat";
import type { CommonFilter } from "#shared/types";

export const useTransactionHistory = () => {
  const { $api } = useNuxtApp();
  const transactionHistory = useState<TransactionRecordHistory | null>(
    "transactionHistory",
    () => null,
  );
  const pending = useState<boolean>("transactionHistory-pending", () => false);

  const fetchTransactionHistory = async (
    filter: CommonFilter = {
      limit: 100,
      page: 1,
    },
  ) => {
    if (pending.value) return;

    pending.value = true;

    try {
      const data = await $api<TransactionRecordHistory>(
        API_TRANSACTION_HISTORY,
        {
          method: "GET",
          query: filter,
        },
      );
      transactionHistory.value = data;
      console.log(data)
    } catch (err) {
      transactionHistory.value = null;
    } finally {
      pending.value = false;
    }

    return transactionHistory.value;
  };

  const reset = () => {
    transactionHistory.value = null;
  };

  return {
    transactionHistory,
    fetchTransactionHistory,
    pending,
    reset,
  };
};
