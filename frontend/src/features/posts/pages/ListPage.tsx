import { Link } from "react-router-dom";
import { useQuery } from "@tanstack/react-query";
import { listPosts } from "../api";
import PostCard from "../components/PostCard";
import { useState } from "react";

export default function ListPage() {
    const [q, setQ] = useState("");
    const { data, isLoading } = useQuery({
        queryKey: ["posts", q, 0],
        queryFn: () => listPosts({ q, page: 0, size: 20 }),
    });

    return (
        <div style={{ maxWidth:800, margin:"24px auto", padding:"0 16px" }}>
            <h1>게시글</h1>
            <div style={{ display:"flex", gap:8, marginBottom:12 }}>
                <input value={q} onChange={e=>setQ(e.target.value)} placeholder="검색어" />
                <Link to="/write"><button>새 글</button></Link>
            </div>
            {isLoading && <p>불러오는 중…</p>}
            {data?.content.map(p => <PostCard key={p.id} post={p} />)}
            {!isLoading && (data?.content.length ?? 0) === 0 && <p>글이 없습니다.</p>}
        </div>
    );
}