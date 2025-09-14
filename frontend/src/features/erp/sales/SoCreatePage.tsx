import { useMutation, useQueryClient } from "@tanstack/react-query";
import { createSO, type SOCreate } from "../api";
import { useNavigate } from "react-router-dom";
import { useState } from "react";

export default function SoCreatePage(){
    const nav = useNavigate(); const qc = useQueryClient();
    const [customerName, setCustomerName] = useState("고객A");
    const [orderDate, setOrderDate] = useState(new Date().toISOString().slice(0,10));
    const [lines, setLines] = useState([{ itemId:1, qty:1, price:1500 }]);

    const mut = useMutation({
        mutationFn: (body:SOCreate)=>createSO(body),
        onSuccess: ()=>{ qc.invalidateQueries({ queryKey:["so"] }); nav("/erp/sales"); }
    });

    return (
        <div style={{maxWidth:720, margin:"24px auto"}}>
            <h1>주문 작성</h1>
            <div style={{display:"grid", gap:8}}>
                <input placeholder="고객명" value={customerName} onChange={e=>setCustomerName(e.target.value)} />
                <input type="date" value={orderDate} onChange={e=>setOrderDate(e.target.value)} />
                <button onClick={()=>setLines(ls=>[...ls, {itemId:1, qty:1, price:1500}])}>라인 추가</button>
            </div>
            <div style={{marginTop:12}}>
                <button onClick={()=>{
                    const soNo = `SO-${Date.now()}`;
                    mut.mutate({ soNo, customerName, orderDate, lines });
                }}>저장</button>
            </div>
        </div>
    );
}
