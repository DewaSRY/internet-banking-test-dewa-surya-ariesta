import { z } from "zod";
import { PaginateMetaDataSchema } from "../common/paginate";

export const CommonResponseSchema = <T extends  z.ZodTypeAny>(schema: T) => {
  return z.object({
    messages: z.string(),
    metadata: PaginateMetaDataSchema,
    data: z.array(schema),
  });
};
