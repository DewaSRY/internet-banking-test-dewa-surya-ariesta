<template>
  <UFormField :label="label" :name="name" :required="required" :help="help">
    <USelectMenu
      :items="items"
      :model-value="modelValue"
      :placeholder="placeholder"
      :size="size"
      :disabled="disabled"
      value-attribute="value"
      option-attribute="label"
      @update:model-value="$emit('update:modelValue', $event?.toString())"
      class="w-full"
    />
  </UFormField>
</template>

<script setup lang="ts">

interface Props {
  modelValue?: string;
  label?: string;
  name: string;
  items: string[];
  placeholder?: string;
  required?: boolean;
  disabled?: boolean;
  size?: "sm" | "md" | "lg" | "xl";
  help?: string;
}

const props = withDefaults(defineProps<Props>(), {
  label: "Select Option",
  placeholder: "Choose option",
  required: false,
  disabled: false,
  size: "xl",
  help: "",
});

defineEmits<{
  "update:modelValue": [value: string | undefined];
}>();


</script>
