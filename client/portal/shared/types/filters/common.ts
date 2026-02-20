import { z } from "zod";

export const CommonFilterSchema  = z.object({
  page: z.number(),
  limit: z.number(),
});


export type CommonFilter = z.infer<typeof CommonFilterSchema>;