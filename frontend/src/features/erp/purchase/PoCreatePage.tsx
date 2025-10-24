import { useMutation, useQueryClient } from "@tanstack/react-query";
import { createPO /*, type POCreate*/ } from "../api";
import { useNavigate } from "react-router-dom";
import { useState } from "react";

type Line = { itemId: number; qty: number; unitPrice: number };

type Item = { id: number; name: string; price: number }; // price를 필수로

const ITEMS: Item[] = [
    { id: 1, name: "아메리카노", price: 1500 },
    { id: 2, name: "라떼",       price: 2500 },
    { id: 3, name: "그린티",     price: 2000 },
];

export default function PoCreatePage() {
    const nav = useNavigate();
    const qc = useQueryClient();

    const [bpName, setBpName] = useState("거래처");
    const [orderDate, setOrderDate] = useState(new Date().toISOString().slice(0, 10));
    const [remark, setRemark] = useState("");
    const [lines, setLines] = useState<Line[]>([{ itemId: 1, qty: 1, unitPrice: 1500 }]);

    const mut = useMutation({
        mutationFn: createPO, // ← 중복 타입 지정 제거
        onSuccess: () => { qc.invalidateQueries({ queryKey: ["po"] }); nav("/erp/purchase"); },
    });

    const addLine = () => setLines(ls => [...ls, { itemId: 1, qty: 1, unitPrice: 1500 }]);
    const removeLine = (i: number) => setLines(ls => ls.filter((_, idx) => idx !== i));
    const updateLine = <K extends keyof Line>(i: number, key: K, val: Line[K]) =>
        setLines(ls => ls.map((l, idx) => (idx === i ? { ...l, [key]: val } : l)));

    const total = lines.reduce((s, l) => s + l.qty * l.unitPrice, 0);

    return (
        <div style={{ maxWidth: 720, margin: "24px auto" }}>
            <h1>발주 작성</h1>

            <div style={{ display: "grid", gap: 8 }}>
                <input placeholder="거래처" value={bpName} onChange={e => setBpName(e.target.value)} />
                <input type="date" value={orderDate} onChange={e => setOrderDate(e.target.value)} />
                <input placeholder="비고" value={remark} onChange={e => setRemark(e.target.value)} />
                <button type="button" onClick={addLine}>라인 추가</button>
            </div>

            <div style={{ marginTop: 16, display: "grid", gap: 8 }}>
                {lines.map((ln, i) => (
                    <div key={i} style={{ display: "grid", gridTemplateColumns: "1.5fr 1fr 1fr auto", gap: 8 }}>
                        <select
                            value={ln.itemId} // ← 숫자 그대로
                            onChange={(e) => {
                                const id = Number(e.target.value);
                                updateLine(i, "itemId", id);
                                const found = ITEMS.find(x => x.id === id);
                                if (found) updateLine(i, "unitPrice", found.price); // 옵셔널 체크 불필요
                            }}
                        >
                            {ITEMS.map(item => (
                                <option key={item.id} value={item.id}>
                                    {item.name}
                                </option>
                            ))}
                        </select>

                        <input
                            type="number"
                            placeholder="수량"
                            value={ln.qty}
                            onChange={e => updateLine(i, "qty", Number(e.target.value || 0))}
                        />
                        <input
                            type="number"
                            placeholder="단가"
                            value={ln.unitPrice}
                            onChange={e => updateLine(i, "unitPrice", Number(e.target.value || 0))}
                        />
                        <button type="button" onClick={() => removeLine(i)}>삭제</button>
                    </div>
                ))}
            </div>

            <div style={{ marginTop: 12 }}>
                <div>합계: {total.toLocaleString()}원</div>
                <button
                    onClick={() => {
                        const poNo = `PO-${Date.now()}`;
                        // 백엔드 DTO가 String을 기대하면 여기서 map 해서 문자열 변환해서 보내기
                        // mut.mutate({ poNo, bpName, orderDate, remark, lines: lines.map(l => ({ ...l, ... })) })
                        mut.mutate({ poNo, bpName, orderDate, remark, lines });
                    }}
                >
                    저장
                </button>
            </div>
        </div>
    );
}
