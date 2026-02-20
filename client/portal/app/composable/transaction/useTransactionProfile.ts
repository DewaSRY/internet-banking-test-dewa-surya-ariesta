import { API_TRANSACTION_PROFILE } from "#shared/constants/api-phat";

export const useTransactionProfile = () => {
  const { $api } = useNuxtApp();
  const transactionProfile = useState<TransactionProfileResponse | null>(
    "transaction-profile",
    () => null,
  );
  const pending = useState<boolean>("profile-pending", () => false);

  const fetchTransactionProfile = async () => {
    if (pending.value) return;

    pending.value = true;

    try {
      const data = await $api<TransactionProfileResponse>(
        API_TRANSACTION_PROFILE,
      );
      transactionProfile.value = data;
    } catch (err) {
      transactionProfile.value = null;
    } finally {
      pending.value = false;
    }

    return transactionProfile.value;
  };

  const reset = () => {
    transactionProfile.value = null;
  };

  return {
    transactionProfile,
    fetchTransactionProfile,
    pending,
    reset,
  };
};
