import { createBrowserRouter , Navigate} from "react-router-dom";

import AppLayout from "./AppLayout";

import InventoryPage from "../features/erp/inventory/InventoryPage";
import PoListPage from "../features/erp/purchase/PoListPage";
import PoCreatePage from "../features/erp/purchase/PoCreatePage";
import SoListPage from "../features/erp/sales/SoListPage";
import SoCreatePage from "../features/erp/sales/SoCreatePage";
import GrnCreatePage from "../features/erp/grn/GrnCreatePage";
import ShipCreatePage from "../features/erp/shipment/ShipCreatePage";

export const router = createBrowserRouter([
    {
    path: "/",
    element: <AppLayout />,         // ← 여기서 헤더가 항상 렌더됨
    children: [
    { path: "/", element: <Navigate to="/erp/inv" replace /> },   // ← 기본 리다이렉트
    { path: "/erp/inv", element: <InventoryPage /> },
    { path: "/erp/purchase", element: <PoListPage /> },
    { path: "/erp/purchase/new", element: <PoCreatePage /> },
        { path: "/erp/purchase/:id", element: <PoCreatePage /> },
    { path: "/erp/sales", element: <SoListPage /> },
    { path: "/erp/sales/new", element: <SoCreatePage /> },
    { path: "/erp/grn/new", element: <GrnCreatePage /> },
    { path: "/erp/ship/new", element: <ShipCreatePage /> },
    { path: "*", element: <Navigate to="/" replace /> },          // 안전망
    ],
    },
]);
