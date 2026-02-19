import { z } from "zod";
import { CommonResponseSchema } from "./common";
import { TransactionHistoryRecordSchema } from "#shared/common/transaction";

export const TransactionProfileResponseSchema = z.object({
  total: z.number(),
  widthDraw: z.number(),
  transfer: z.number(),
  received: z.number(),
  deposit: z.number(),
});

export type TransactionProfileResponse = z.infer<
  typeof TransactionProfileResponseSchema
>;

export const TransactionRecordHistorySchema = CommonResponseSchema(
  TransactionHistoryRecordSchema,
);

export type TransactionRecordHistory = z.infer<
  typeof TransactionRecordHistorySchema
>;
