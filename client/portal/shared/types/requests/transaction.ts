import { z } from "zod";
import { TransactionEnumSchema } from "../common";

export const CreateTransactionSchema = z.object({
  email: z.string().optional(),
  transactionEnum: TransactionEnumSchema,
  amount: z.number(),
});

export type CreateTransaction = z.infer<typeof CreateTransactionSchema>;
