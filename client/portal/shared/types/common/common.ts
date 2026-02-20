import {z} from "zod";


export const SelectOptionSchema = z.object({
    label : z.string(), 
    value: z.string()
})

export type SelectOption = z.infer<typeof SelectOptionSchema>;