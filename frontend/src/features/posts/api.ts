import { api } from "../../libs/axios";

export type Post = {
    id: number; userId: number; title: string; content: string;
    slug?: string; published: boolean; likeCount: number; commentCount: number;
    createdAt: string; updatedAt: string;
};
export type Page<T> = {
    content: T[]; totalElements: number; totalPages: number; size: number; number: number;
};

const POSTS = "/api/posts";

export const listPosts = async (p:{q?:string;page?:number;size?:number}) =>
    (await api.get<Page<Post>>(POSTS, { params: p })).data;
export const getPost = async (id:string) =>
    (await api.get<Post>(`${POSTS}/${id}`)).data;
export const createPost = async (payload:{userId:number;title:string;content:string;slug?:string;published?:boolean}) =>
    (await api.post<Post>(POSTS, payload)).data;
export const updatePost = async (id:string, payload:Partial<{title:string;content:string;slug:string;published:boolean}>) =>
    (await api.patch<Post>(`${POSTS}/${id}`, payload)).data;
export const deletePost = async (id:string) => api.delete(`${POSTS}/${id}`);