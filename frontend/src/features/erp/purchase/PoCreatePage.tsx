import { useMutation, useQueryClient } from "@tanstack/react-query";
import { createPO, type POCreate } from "../api";
import { useNavigate } from "react-router-dom";
import { useState } from "react";

type Line = { itemId: number; qty: number; unitPrice: number };

type Item = { id: number; name: string; price?: number };

const ITEMS: Item[] = [
    { id: 1, name: "아메리카노", price: 1500 },
    { id: 2, name: "라떼", price: 2500 },
    { id: 3, name: "그린티", price: 2000 },
];

export default function PoCreatePage() {
    const nav = useNavigate();
    const qc = useQueryClient();

    const [bpName, setBpName] = useState("거래처");
    const [orderDate, setOrderDate] = useState(new Date().toISOString().slice(0, 10));
    const [remark, setRemark] = useState("");
    const [lines, setLines] = useState<Line[]>([{ itemId: 1, qty: 1, unitPrice: 1000 }]);

    const mut = useMutation({
        mutationFn: (body: POCreate) => createPO(body),
        onSuccess: () => { qc.invalidateQueries({ queryKey: ["po"] }); nav("/erp/purchase"); },
    });

    const addLine = () => setLines(ls => [...ls, { itemId: 1, qty: 1, unitPrice: 1000 }]);
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

            {/* 라인 렌더링 */}
            <div style={{ marginTop: 16, display: "grid", gap: 8 }}>
                {lines.map((ln, i) => (
                    <div key={i} style={{ display: "grid", gridTemplateColumns: "1.5fr 1fr 1fr auto", gap: 8 }}>
                        {/* 품목 드롭다운: label=이름, value=id */}
                        <select
                            value={ln.itemId === undefined ? "" : String(ln.itemId)}  // ← 문자열로 통일
                            onChange={(e) => {
                                const v = e.target.value;
                                const id = v === "" ? undefined : Number(v);            // ← 숫자로 변환해 상태 저장

                                updateLine(i, "itemId", id as any);

                                // 단가 자동 세팅 (기존 로직 유지)
                                const found = ITEMS.find(x => x.id === id);
                                if (found?.price != null) {
                                    updateLine(i, "unitPrice", found.price);
                                }
                            }}
                        >
                            <option value="" disabled>품목 선택</option>
                            {ITEMS.map(item => (
                                <option key={item.id} value={String(item.id)}>  {/* ← 문자열로 통일 */}
                                    {item.name}
                                </option>
                            ))}
                        </select>

                        <input
                            type="number"
                            placeholder="수량"
                            value={ln.qty}
                            onChange={e => updateLine(i, "qty", Number(e.target.value))}
                        />
                        <input
                            type="number"
                            placeholder="단가"
                            value={ln.unitPrice}
                            onChange={e => updateLine(i, "unitPrice", Number(e.target.value))}
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
                        mut.mutate({ poNo, bpName, orderDate, remark, lines });
                    }}
                >
                    저장
                </button>
            </div>
        </div>
    );
}
