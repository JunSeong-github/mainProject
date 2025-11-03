import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import { useNavigate, useParams, Link } from "react-router-dom";
import {
    createPO,
    updatePO,
    getPO,
    approvePO,
    // types (선택): PO, POLine, POCreate
} from "../api";
import { useEffect, useMemo, useState } from "react";

// ---- 로컬 데모용 아이템 목록 (실서비스에선 API로 교체 가능) ----
type Line = { itemId: number; qty: number; unitPrice: number };
type Item = { id: number; name: string; price: number };
const ITEMS: Item[] = [
    { id: 1, name: "아메리카노", price: 1500 },
    { id: 2, name: "라떼",       price: 2500 },
    { id: 3, name: "그린티",     price: 2000 },
];

export default function PoCreatePage() {
    const { id } = useParams<{ id: string }>();
    const isEdit = !!id;
    const poId = isEdit ? Number(id) : undefined;

    const nav = useNavigate();
    const qc = useQueryClient();

    // --------- 폼 상태 ---------
    const [poNo, setPoNo] = useState<string>("");
    const [bpName, setBpName] = useState<string>("거래처");
    const [orderDate, setOrderDate] = useState<string>(
        new Date().toISOString().slice(0, 10)
    );
    const [remark, setRemark] = useState<string>("");
    const [status, setStatus] = useState<string | undefined>(undefined);
    const [lines, setLines] = useState<Line[]>([
        { itemId: 1, qty: 1, unitPrice: 1500 },
    ]);

    // --------- 단건 조회 (편집 모드일 때만) ---------
    const {
        data: detail,
        isLoading: loadingDetail,
        isError: isDetailError,
    } = useQuery({
        queryKey: ["po", "detail", poId],
        queryFn: () => getPO(poId!),
        enabled: isEdit && Number.isFinite(poId),
        staleTime: 30_000,
    });

    // detail → 폼 상태 채우기
    useEffect(() => {
        if (!isEdit || !detail) return;
        // 서버의 필드명에 맞춰 매핑하세요.
        setPoNo(detail.poNo ?? "");
        setBpName(detail.bpName ?? "");
        setOrderDate(detail.orderDate ?? new Date().toISOString().slice(0, 10));
        setRemark(detail.remark ?? "");
        setStatus(detail.status);

        // 라인 매핑 (숫자 보정)
        const mapped: Line[] = (detail.lines ?? []).map((l: any) => ({
            itemId: Number(l.itemId),
            qty: Number(l.qty),
            unitPrice: Number(l.unitPrice),
        }));
        setLines(mapped.length ? mapped : [{ itemId: 1, qty: 1, unitPrice: 1500 }]);
    }, [detail, isEdit]);

    // --------- Mutations ---------
    const createMut = useMutation({
        mutationFn: createPO,
        onSuccess: (res: any) => {
            qc.invalidateQueries({ queryKey: ["po"] });
            const newId = res?.id;
            if (newId) nav(`/erp/purchase/${newId}`);
            else nav(`/erp/purchase`);
        },
    });

    const updateMut = useMutation({
        mutationFn: (body: any) => updatePO(poId!, body),
        onSuccess: () => {
            qc.invalidateQueries({ queryKey: ["po"] });
            qc.invalidateQueries({ queryKey: ["po", "detail", poId] });
        },
    });

    const approveMut = useMutation({
        mutationFn: () => approvePO(poId!),
        onSuccess: () => {
            qc.invalidateQueries({ queryKey: ["po"] });
            qc.invalidateQueries({ queryKey: ["po", "detail", poId] });
            setStatus("APPROVED");
        },
    });

    // --------- 라인 조작 ---------
    const addLine = () =>
        setLines((ls) => [...ls, { itemId: 1, qty: 1, unitPrice: 1500 }]);

    const removeLine = (i: number) =>
        setLines((ls) => ls.filter((_, idx) => idx !== i));

    const updateLine = <K extends keyof Line>(i: number, key: K, val: Line[K]) =>
        setLines((ls) => ls.map((l, idx) => (idx === i ? { ...l, [key]: val } : l)));

    const total = useMemo(
        () => lines.reduce((s, l) => s + (Number(l.qty) || 0) * (Number(l.unitPrice) || 0), 0),
        [lines]
    );

    // --------- 제출 ---------
    const toBody = () => ({
        poNo: poNo || `PO-${Date.now()}`,
        bpName,
        orderDate,
        remark,
        // 백엔드 DTO가 문자열을 원하면 여기서 String 변환
        lines: lines.map((l) => ({
            itemId: l.itemId,
            qty: l.qty,
            unitPrice: l.unitPrice,
        })),
    });

    const onSubmit = () => {
        const body = toBody();
        if (isEdit) updateMut.mutate(body);
        else createMut.mutate(body);
    };

    // --------- 렌더 ---------
    if (isEdit && loadingDetail) {
        return (
            <div style={{ maxWidth: 760, margin: "24px auto" }}>상세 로딩…</div>
        );
    }

    if (isEdit && isDetailError) {
        return (
            <div style={{ maxWidth: 760, margin: "24px auto" }}>상세 조회 실패</div>
        );
    }

    return (
        <div style={{ maxWidth: 760, margin: "24px auto" }}>
            <div style={{ display: "flex", justifyContent: "space-between", alignItems: "center", marginBottom: 12 }}>
                <h1>{isEdit ? "발주 수정/상세" : "발주 작성"}</h1>
                <Link to="/erp/purchase">← 목록</Link>
            </div>

            {/* 상단 메타 */}
            <div style={{ marginBottom: 12, color: "#666" }}>
                {isEdit && (
                    <>
                        <span style={{ marginRight: 12 }}>ID: {poId}</span>
                        <span>상태: {status ?? "-"}</span>
                    </>
                )}
            </div>

            {/* 기본 필드 */}
            <div style={{ display: "grid", gap: 8 }}>
                <input placeholder="번호(미입력시 자동)" value={poNo} onChange={(e) => setPoNo(e.target.value)} />
                <input placeholder="거래처" value={bpName} onChange={(e) => setBpName(e.target.value)} />
                <input type="date" value={orderDate} onChange={(e) => setOrderDate(e.target.value)} />
                <input placeholder="비고" value={remark} onChange={(e) => setRemark(e.target.value)} />
                {(!isEdit || status === "DRAFT") &&(
                <button type="button" onClick={addLine}>라인 추가</button>
                    )}
            </div>

            {/* 라인 편집 */}
            <div style={{ marginTop: 16, display: "grid", gap: 8 }}>
                {lines.map((ln, i) => (
                    <div key={i} style={{ display: "grid", gridTemplateColumns: "1.5fr 1fr 1fr auto", gap: 8 }}>
                        <select
                            value={ln.itemId}
                            onChange={(e) => {
                                const id = Number(e.target.value);
                                updateLine(i, "itemId", id);
                                const found = ITEMS.find((x) => x.id === id);
                                if (found) updateLine(i, "unitPrice", found.price);
                            }}
                        >
                            {ITEMS.map((item) => (
                                <option key={item.id} value={item.id}>
                                    {item.name}
                                </option>
                            ))}
                        </select>

                        <input
                            type="number"
                            placeholder="수량"
                            value={ln.qty}
                            onChange={(e) => updateLine(i, "qty", Number(e.target.value || 0))}
                        />
                        <input
                            type="number"
                            placeholder="단가"
                            value={ln.unitPrice}
                            onChange={(e) => updateLine(i, "unitPrice", Number(e.target.value || 0))}
                        />
                        {(!isEdit || status === "DRAFT") &&(
                        <button type="button" onClick={() => removeLine(i)}>삭제</button>
                            )
                        }
                    </div>
                ))}
            </div>

            {/* 하단 액션 */}
            <div style={{ marginTop: 12, display: "flex", gap: 8, alignItems: "center" }}>
                <div style={{ marginRight: "auto" }}>합계: {total.toLocaleString()}원</div>
                {isEdit && status === "DRAFT" && (
                    <button
                        type="button"
                        onClick={() => approveMut.mutate()}
                        disabled={approveMut.isPending}
                        title="승인하면 상태가 APPROVED로 바뀝니다"
                    >
                        {approveMut.isPending ? "승인 중…" : "승인"}
                    </button>
                )}
                {isEdit && status === "DRAFT" && (
                <button
                    type="button"
                    onClick={onSubmit}
                    disabled={createMut.isPending || updateMut.isPending}
                >
                    {isEdit ? (updateMut.isPending ? "수정 저장 중…" : "수정 저장") : (createMut.isPending ? "저장 중…" : "저장")}
                </button>
                )}
            </div>
        </div>
    );
}
