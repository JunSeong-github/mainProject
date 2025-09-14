import { useMutation } from "@tanstack/react-query";
import { createGRN } from "../api";
import { useState } from "react";

export default function GrnCreatePage(){
    const [poId, setPoId] = useState<number>(0);
    const [grnDate, setDate] = useState(new Date().toISOString().slice(0,10));
    const [remark, setRemark] = useState("");

    const mut = useMutation({ mutationFn: createGRN, onSuccess: ()=>alert("입고 완료") });

    return (
        <div style={{maxWidth:600, margin:"24px auto"}}>
            <h1>입고 처리(GRN)</h1>
            <input type="number" placeholder="PO ID" value={poId} onChange={(e)=>setPoId(Number(e.target.value))} />
            <input type="date" value={grnDate} onChange={(e)=>setDate(e.target.value)} />
            <input placeholder="비고" value={remark} onChange={(e)=>setRemark(e.target.value)} />
            <div style={{marginTop:12}}>
                <button onClick={()=>mut.mutate({ poId, grnDate, remark })}>입고</button>
            </div>
        </div>
    );
}
