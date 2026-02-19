import { API_USER_PROFILE } from "#shared/constants/api-phat";

export const useProfile = () => {
  const { $api } = useNuxtApp();
  const profile = useState<UserProfile | null>("profile", () => null);
  const pending = useState<boolean>("profile-pending", () => false);

  const fetchProfile = async () => {
    if (pending.value) return;

    pending.value = true;

    try {
      const data = await $api<UserProfile>(API_USER_PROFILE);
      profile.value = data;
    } catch (err) {
      profile.value = null;
    } finally {
      pending.value = false;
    }

    return profile.value;
  };

  const reset = () => {
    profile.value = null;
  };

  return {
    profile,
    fetchProfile,
    pending,
    reset,
  };
};
