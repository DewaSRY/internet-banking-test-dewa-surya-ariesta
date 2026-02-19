import { z } from "zod";

export const SignupRequestSchema = z.object({
  username: z.string(),
  email: z.string(),
  password: z.string(),
});

export type SignupRequest = z.infer<typeof SignupRequestSchema>;

export const SigninRequestSchema = z.object({
  username: z.string(),
  email: z.string(),
  password: z.string(),
});

export type SigninRequest = z.infer<typeof SigninRequestSchema>;

export const RefreshTokenSchema = z.object({
  refreshToken: z.string(),
});

export type RefreshToken = z.infer<typeof RefreshTokenSchema>;
