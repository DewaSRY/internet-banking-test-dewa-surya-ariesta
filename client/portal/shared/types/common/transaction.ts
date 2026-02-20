import {z} from "zod";
import { TransactionEnumSchema } from "./enum";
import { UserProfileSchema } from "./user";


export const TransactionHistoryRecordSchema = z.object({
  transactionEnum: TransactionEnumSchema,
  amount: z.number(),
  userFrom: UserProfileSchema,
  userTo: UserProfileSchema.optional(),
  createdAt: z.coerce.date(),
});

export type TransactionHistoryRecord = z.infer<typeof TransactionHistoryRecordSchema>;