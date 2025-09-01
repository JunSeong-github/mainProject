import { useForm } from "react-hook-form";
type FormValues = { userId: number; title: string; content: string; slug?: string; published?: boolean };

export default function PostForm({ initial, onSubmit }:{
    initial?: Partial<FormValues>; onSubmit:(v:FormValues)=>void;
}) {
    const { register, handleSubmit } = useForm<FormValues>({ defaultValues: { userId:1, published:true, ...initial }});
    return (
        <form onSubmit={handleSubmit(onSubmit)} style={{ display:"grid", gap:12 }}>
            <input placeholder="제목" {...register("title", { required: true })} />
            <textarea placeholder="내용" rows={8} {...register("content", { required: true })} />
            <input placeholder="슬러그(선택)" {...register("slug")} />
            <label><input type="checkbox" {...register("published")} /> 공개</label>
            <button type="submit">저장</button>
        </form>
    );
}