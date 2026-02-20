import {z} from "zod";


export const PaginateMetaDataSchema = z.object({
    total: z.number(), 
    limit: z.number(), 
    page: z.number()
})

export type PaginateMetaData = z.infer<typeof PaginateMetaDataSchema>;