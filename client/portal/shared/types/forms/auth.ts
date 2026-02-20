import { z } from "zod";

import { SignupRequestSchema } from "../requests/auth";

export const SignupFormSchema = SignupRequestSchema.extend({
  confirmPassword: z.string(),
}).refine((data) => data.password === data.confirmPassword, {
  message: "Passwords do not match",
  path: ["confirmPassword"],
});

export type SignupForm = z.infer<typeof SignupFormSchema>;
