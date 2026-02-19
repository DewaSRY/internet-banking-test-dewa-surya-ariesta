import {z} from "zod";
import { UserProfileSchema } from "#shared/common/user";

export const JwtResponseSchema = z.object({
  accessToken: z.string(),
  refreshToken: z.string(),
  userProfileDto: UserProfileSchema
});

