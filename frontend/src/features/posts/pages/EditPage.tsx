import { useParams, useNavigate } from "react-router-dom";
import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { getPost, updatePost } from "../api";
import PostForm from "../components/PostForm";

export default function EditPage() {
    const { id = "" } = useParams();
    const nav = useNavigate();
    const qc = useQueryClient();

    const { data } = useQuery({ queryKey: ["post", id], queryFn: () => getPost(id) });

    const update = useMutation({
        mutationFn: (payload:any) => updatePost(id, payload),
        onSuccess: async (res) => {
            await Promise.all([
                qc.invalidateQueries({ queryKey: ["posts"] }),
                qc.invalidateQueries({ queryKey: ["post", id] }),
            ]);
            nav(`/posts/${res.id}`);
        },
    });

    if (!data) return <p style={{ padding:16 }}>불러오는 중…</p>;

    return (
        <div style={{ maxWidth:800, margin:"24px auto", padding:"0 16px" }}>
            <h1>글 수정</h1>
            <PostForm
                initial={{ userId: data.userId, title: data.title, content: data.content, slug: data.slug, published: data.published }}
                onSubmit={(v)=>update.mutate(v)}
            />
        </div>
    );
}