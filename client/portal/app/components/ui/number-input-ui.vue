<template>
  <UFormField
    :label="label"
    :name="name"
    :required="required"
    :help="help"
  >
    <UInput
      :model-value="modelValue"
      type="number"
      :placeholder="placeholder"
      :size="size"
      :icon="icon"
      :disabled="disabled"
      @update:model-value="$emit('update:modelValue', $event)"
      class="w-full"
    />
  </UFormField>
</template>

<script setup lang="ts">

interface Props {
  modelValue?: number
  label?: string
  name?: string
  placeholder?: string
  required?: boolean
  disabled?: boolean
  size?: 'sm' | 'md' | 'lg' | 'xl'
  icon?: string
  help?: string
}

withDefaults(defineProps<Props>(), {
  modelValue: 0,
  label: 'Full Name',
  name: 'name',
  placeholder: 'Enter your full name',
  required: false,
  disabled: false,
  size: 'xl',
  icon: 'mdi-light-alarm-panel',
  help: '',
})

defineEmits<{
  'update:modelValue': [value: number | undefined]
}>()
</script>
