import { z } from "zod";
import { CreateTransactionSchema } from "../requests";



export const CreateTransactionFormSchema = CreateTransactionSchema.omit({
  transactionEnum: true,
}).merge(
  z.object({
    transactionEnum: z.string()
  }),
);


export type CreateTransactionForm = z.infer<typeof CreateTransactionFormSchema>;
