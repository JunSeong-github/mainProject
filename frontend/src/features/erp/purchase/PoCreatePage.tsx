import { useMutation, useQueryClient } from "@tanstack/react-query";
import { createPO, type POCreate } from "../api";
import { useNavigate } from "react-router-dom";
import { useState } from "react";

export default function PoCreatePage(){
    const nav = useNavigate();
    const qc = useQueryClient();

    const [bpName, setBpName] = useState("ACME");
    const [orderDate, setOrderDate] = useState(new Date().toISOString().slice(0,10));
    const [remark, setRemark] = useState("");
    const [lines, setLines] = useState([{ itemId:1, qty:1, unitPrice:1000 }]);

    const mut = useMutation({
        mutationFn: (body:POCreate)=>createPO(body),
        onSuccess: ()=>{ qc.invalidateQueries({ queryKey:["po"] }); nav("/erp/purchase"); }
    });

    return (
        <div style={{maxWidth:720, margin:"24px auto"}}>
            <h1>발주 작성</h1>
            <div style={{display:"grid", gap:8}}>
                <input placeholder="거래처" value={bpName} onChange={e=>setBpName(e.target.value)} />
                <input type="date" value={orderDate} onChange={e=>setOrderDate(e.target.value)} />
                <input placeholder="비고" value={remark} onChange={e=>setRemark(e.target.value)} />
                <button onClick={()=>setLines(ls=>[...ls, {itemId:1, qty:1, unitPrice:1000}])}>라인 추가</button>
            </div>
            <div style={{marginTop:12}}>
                <button onClick={()=>{
                    const poNo = `PO-${Date.now()}`;
                    mut.mutate({ poNo, bpName, orderDate, remark, lines });
                }}>저장</button>
            </div>
        </div>
    );
}
