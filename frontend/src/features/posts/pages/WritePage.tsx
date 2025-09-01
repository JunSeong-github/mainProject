import { useMutation, useQueryClient } from "@tanstack/react-query";
import { createPost } from "../api";
import { useNavigate } from "react-router-dom";
import PostForm from "../components/PostForm";

export default function WritePage() {
    const nav = useNavigate();
    const qc = useQueryClient();

    const create = useMutation({
        mutationFn: createPost,
        onSuccess: async (res) => {
            await qc.invalidateQueries({ queryKey: ["posts"] });
            nav(`/posts/${res.id}`);
        },
    });

    return (
        <div style={{ maxWidth:800, margin:"24px auto", padding:"0 16px" }}>
            <h1>새 글 쓰기</h1>
            <PostForm onSubmit={(v)=>create.mutate(v)} />
        </div>
    );
}