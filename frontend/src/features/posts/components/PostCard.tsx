import { Link } from "react-router-dom";
import { Post } from "../api";

export default function PostCard({ post }: { post: Post }) {
    return (
        <div style={{ border:"1px solid #ddd", padding:16, borderRadius:8, marginBottom:12 }}>
            <h3 style={{ margin:0 }}>
                <Link to={`/posts/${post.id}`}>{post.title}</Link>
            </h3>
            <p style={{ color:"#666", margin:"6px 0 0" }}>
                by {post.userId} · {new Date(post.createdAt).toLocaleString()}
            </p>
        </div>
    );
}