import { useMutation } from "@tanstack/react-query";
import { createShipment } from "../api";
import { useState } from "react";

export default function ShipCreatePage(){
    const [soId, setSoId] = useState<number>(0);
    const [shipDate, setDate] = useState(new Date().toISOString().slice(0,10));
    const [remark, setRemark] = useState("");

    const mut = useMutation({ mutationFn: createShipment, onSuccess: ()=>alert("출고 완료") });

    return (
        <div style={{maxWidth:600, margin:"24px auto"}}>
            <h1>출고 처리</h1>
            <input type="number" placeholder="SO ID" value={soId} onChange={(e)=>setSoId(Number(e.target.value))} />
            <input type="date" value={shipDate} onChange={(e)=>setDate(e.target.value)} />
            <input placeholder="비고" value={remark} onChange={(e)=>setRemark(e.target.value)} />
            <div style={{marginTop:12}}>
                <button onClick={()=>mut.mutate({ soId, shipDate, remark })}>출고</button>
            </div>
        </div>
    );
}
