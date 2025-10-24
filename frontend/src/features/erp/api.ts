import { api } from "../../libs/axios";

/** 공통 페이지 응답 */
export interface PageResp<T> {
    content: T[];
    totalElements: number;
    totalPages: number;
    number: number; // 현재 페이지(0-base)
    size: number;
}

/** 재고 행 */
export type InventoryRow = {
    id: number;
    itemCode: string;
    itemName: string;
    warehouseId: number;
    warehouseName: string;
    qtyOnHand: number;
    avgCost: number;
};

/** 발주 */
export type PO = {
    id: number;
    poNo: string;
    bpName?: string;
    status: string;
    orderDate: string; // yyyy-MM-dd
    remark?: string;
};
export type POLine = { itemId: number; qty: number; unitPrice: number };
export type POCreate = {
    poNo: string;
    bpName?: string;
    orderDate: string;
    remark?: string;
    lines: POLine[];
};

/** 주문 */
export interface SO {
    id: number;
    soNo: string;
    customerName: string;
    status: string;
    orderDate: string; // ISO string
}
export type SOLine = { itemId: number; qty: number; price: number };
export type SOCreate = {
    soNo: string;
    customerName?: string;
    orderDate: string;
    lines: SOLine[];
};

/** GRN/Shipment */
export type GRNCreate = { poId: number; grnDate: string; remark?: string };
export type ShipCreate = { soId: number; shipDate: string; remark?: string };

/* ---- API ---- */

// Inventory
export const listInv = (p: {
    itemCode?: string;
    warehouseId?: number;
    page?: number;
    size?: number;
    sort?: string;
}) =>
    api
        .get<PageResp<InventoryRow>>("/api/erp/inventory", { params: p })
        .then((r) => r.data);

// Purchase
export const listPO = (p: {
    q?: string;
    page?: number;
    size?: number;
    sort?: string;
}) =>
    api
        .get<PageResp<PO>>("/api/purchase/orders", { params: p })
        .then((r) => r.data);

export const createPO = (body: POCreate) =>
    api.post<PO>("/api/purchase/orders", body).then((r) => r.data);

export const approvePO = (id: number) =>
    api.post<PO>(`/api/purchase/orders/${id}/approve`).then((r) => r.data);

// Sales
export const listSO = (p: { q?: string; page?: number; size?: number; sort?: string }) =>
    api.get<PageResp<SO>>("/api/erp/sales/orders", { params: p })
        .then((r) => r.data as PageResp<SO>);

export const createSO = (body: SOCreate) =>
    api.post<SO>("/api/erp/sales/orders", body).then((r) => r.data);

// GRN / Shipment
export const createGRN = (body: GRNCreate) =>
    api.post("/api/erp/grn", body).then((r) => r.data);

export const createShipment = (body: ShipCreate) =>
    api.post("/api/erp/shipment", body).then((r) => r.data);
