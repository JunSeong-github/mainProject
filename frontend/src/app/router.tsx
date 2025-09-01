import { createBrowserRouter } from "react-router-dom";
import Layout from "./Layout";
import ListPage from "../features/posts/pages/ListPage";
import DetailPage from "../features/posts/pages/DetailPage";
import WritePage from "../features/posts/pages/WritePage";
import EditPage from "../features/posts/pages/EditPage";

export const router = createBrowserRouter([
    {
        path: "/",
        element: <Layout />,                     // ✅ 상단 메뉴 공통 적용
        children: [
            { index: true, element: <ListPage /> },          // /
            { path: "c/:cat", element: <ListPage /> },       // /c/general, /c/dev, /c/life
            { path: "posts/:id", element: <DetailPage /> },  // /posts/123
            { path: "edit/:id", element: <EditPage /> },     // /edit/123
            { path: "write", element: <WritePage /> },       // /write
        ],
    },
]);