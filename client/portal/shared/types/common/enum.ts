import {z} from "zod";

export const TransactionEnumSchema = z.enum(["DEPOSIT", "TRANSFER", "WITHDRAW", "RECEIVE"])


export type TransactionEnum = z.infer<typeof TransactionEnumSchema>;