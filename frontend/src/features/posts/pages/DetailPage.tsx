import { Link, useNavigate, useParams } from "react-router-dom";
import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { deletePost, getPost } from "../api";

export default function DetailPage() {
    const { id = "" } = useParams();
    const nav = useNavigate();
    const qc = useQueryClient();

    const { data, isLoading } = useQuery({ queryKey: ["post", id], queryFn: () => getPost(id) });

    const del = useMutation({
        mutationFn: () => deletePost(id),
        onSuccess: async () => {
            await qc.invalidateQueries({ queryKey: ["posts"] });
            nav("/");
        },
    });

    if (isLoading) return <p style={{ padding:16 }}>불러오는 중…</p>;
    if (!data) return <p style={{ padding:16 }}>글이 없습니다.</p>;

    return (
        <div style={{ maxWidth:800, margin:"24px auto", padding:"0 16px" }}>
            <h1 style={{ marginBottom:8 }}>{data.title}</h1>
            <p style={{ color:"#666" }}>{new Date(data.createdAt).toLocaleString()} · 작성자 {data.userId}</p>
            <hr />
            <div style={{ whiteSpace:"pre-wrap", margin:"12px 0" }}>{data.content}</div>
            <div style={{ display:"flex", gap:8 }}>
                <Link to={`/edit/${data.id}`}><button>수정</button></Link>
                <button onClick={()=>del.mutate()}>삭제</button>
                <Link to="/"><button>목록</button></Link>
            </div>
        </div>
    );
}