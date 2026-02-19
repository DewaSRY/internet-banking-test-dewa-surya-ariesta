<template>
  <div>
    <UPageHero
      title="Bank Test - Dewa Surya Ariesta"
      description="Lorem ipsum, dolor sit amet consectetur adipisicing elit. In, eos eaque blanditiis ducimus dolorum iure magnam dolorem quos officiis voluptatibus minima illo! Maiores est inventore delectus, tempore earum nostrum porro."
      :links="[
        {
          label: 'Get started',
          to: '/auth/signup',
          trailingIcon: 'i-lucide-arrow-right',
          size: 'xl',
        },
        {
          label: 'Sign in',
          to: '/auth/signin',
          icon: 'i-lucide-log-in',
          size: 'xl',
          color: 'neutral',
          variant: 'subtle',
        },
      ]"
    />

    

  </div>
</template>
