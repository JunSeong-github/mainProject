import { useQuery } from "@tanstack/react-query";
import { listInv, type InventoryRow, type PageResp } from "../api";
import { useState } from "react";

export default function InventoryPage() {
    const [itemCode, setItemCode] = useState("");
    const [warehouseId, setWh] = useState<number | undefined>(undefined);
    const [page, setPage] = useState(0);

    const { data, isLoading } = useQuery<PageResp<InventoryRow>>({
        queryKey: ["inv", itemCode, warehouseId, page],
        queryFn: () =>
            listInv({
                itemCode: itemCode || undefined,
                warehouseId,
                page,
                size: 10,
                sort: "id,desc",
            }),
        keepPreviousData: true,
        staleTime: 30_000,
        refetchOnWindowFocus: false,
    });

    const rows = data?.content ?? [];

    return (
        <div style={{ maxWidth: 960, margin: "24px auto" }}>
            <h1>재고</h1>
            <div style={{ display: "flex", gap: 8, marginBottom: 12 }}>
                <input value={itemCode} onChange={(e)=>setItemCode(e.target.value)} placeholder="품목코드" />
                <input type="number" value={warehouseId ?? ""} placeholder="창고ID"
                       onChange={(e)=>setWh(e.target.value? Number(e.target.value): undefined)} />
            </div>

            {isLoading ? <p>로딩…</p> :
                <table width="100%">
                    <thead><tr><th>품목코드</th><th>품목명</th><th>창고</th><th>수량</th><th>평균단가</th></tr></thead>
                    <tbody>
                    {rows.map((inv)=>(
                        <tr key={inv.id}>
                            <td>{inv.itemCode}</td>
                            <td>{inv.itemName}</td>
                            <td>{inv.warehouseName}({inv.warehouseId})</td>
                            <td>{inv.qtyOnHand}</td>
                            <td>{inv.avgCost}</td>
                        </tr>
                    ))}
                    </tbody>
                </table>}
            <div style={{marginTop:12}}>
                <button disabled={page<=0} onClick={()=>setPage(p=>p-1)}>이전</button>
                <span style={{margin:"0 8px"}}>{(data?.number??0)+1} / {data?.totalPages??1}</span>
                <button disabled={(data?.number??0) >= ((data?.totalPages??1)-1)} onClick={()=>setPage(p=>p+1)}>다음</button>
            </div>
        </div>
    );
}
