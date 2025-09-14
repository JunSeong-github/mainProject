import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import { listPO, approvePO, type PO, type PageResp } from "../api";
import { Link } from "react-router-dom";
import { useState } from "react";

export default function PoListPage(){
    const [q, setQ] = useState("");
    const [page, setPage] = useState(0);
    const qc = useQueryClient();

    const { data, isLoading } = useQuery<PageResp<PO>>({
        queryKey:["po", q, page],
        queryFn:()=>listPO({ q, page, size:10, sort:"orderDate,desc" }),
        keepPreviousData:true, staleTime:30_000
    });

    const approve = useMutation({
        mutationFn: (id:number)=>approvePO(id),
        onSuccess: ()=> qc.invalidateQueries({ queryKey:["po"] })
    });

    const rows = data?.content ?? [];

    return (
        <div style={{maxWidth:960, margin:"24px auto"}}>
            <h1>발주</h1>
            <div style={{display:"flex", gap:8, marginBottom:12}}>
                <input value={q} onChange={e=>setQ(e.target.value)} placeholder="PO번호/거래처" />
                <Link to="/erp/purchase/new"><button>새 발주</button></Link>
            </div>
            {isLoading? <p>로딩…</p> :
                <table width="100%">
                    <thead><tr><th>ID</th><th>번호</th><th>거래처</th><th>상태</th><th>일자</th><th/></tr></thead>
                    <tbody>
                    {rows.map(po=>(
                        <tr key={po.id}>
                            <td>{po.id}</td>
                            <td>{po.poNo}</td>
                            <td>{po.bpName}</td>
                            <td>{po.status}</td>
                            <td>{po.orderDate}</td>
                            <td>{po.status==="DRAFT" && <button onClick={()=>approve.mutate(po.id)}>승인</button>}</td>
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
