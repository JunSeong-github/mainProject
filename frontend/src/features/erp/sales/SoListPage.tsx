// src/features/erp/sales/SoListPage.tsx
import {
    useQuery,
    keepPreviousData,
    type UseQueryResult,
} from "@tanstack/react-query";
import { listSO, type SO, type PageResp } from "../api";
import { Link } from "react-router-dom";
import { useState } from "react";

export default function SoListPage() {
    const [q, setQ] = useState("");
    const [page, setPage] = useState(0);

    // queryKey를 튜플로 고정 + 타입 안전하게
    const key = ["so", q, page] as const;

    // v5: 4개 제네릭 <TQueryFnData, TError, TData, TQueryKey>
    const { data, isLoading }: UseQueryResult<PageResp<SO>, Error> =
        useQuery<PageResp<SO>, Error, PageResp<SO>, typeof key>({
            queryKey: key,
            queryFn: () => listSO({ q, page, size: 10, sort: "orderDate,desc" }),
            // v5에서 keepPreviousData 대신 이렇게 사용
            placeholderData: keepPreviousData,
            staleTime: 30_000,
            refetchOnWindowFocus: false,
        });

    const rows: SO[] = data?.content ?? [];

    return (
        <div style={{ maxWidth: 960, margin: "24px auto" }}>
            <h1>주문</h1>

            <div style={{ display: "flex", gap: 8, marginBottom: 12 }}>
                <input
                    value={q}
                    onChange={(e) => setQ(e.target.value)}
                    placeholder="SO번호/고객명"
                />
                <Link to="/erp/sales/new">
                    <button>새 주문</button>
                </Link>
            </div>

            {isLoading ? (
                <p>로딩…</p>
            ) : (
                <table width="100%">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>번호</th>
                        <th>고객</th>
                        <th>상태</th>
                        <th>일자</th>
                    </tr>
                    </thead>
                    <tbody>
                    {rows.map((so) => (
                        <tr key={so.id}>
                            <td>{so.id}</td>
                            <td>{so.soNo}</td>
                            <td>{so.customerName}</td>
                            <td>{so.status}</td>
                            <td>{so.orderDate}</td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            )}

            <div style={{ marginTop: 12 }}>
                <button disabled={page <= 0} onClick={() => setPage((p) => p - 1)}>
                    이전
                </button>
                <span style={{ margin: "0 8px" }}>
          {(data?.number ?? 0) + 1} / {data?.totalPages ?? 1}
        </span>
                <button
                    disabled={(data?.number ?? 0) >= ((data?.totalPages ?? 1) - 1)}
                    onClick={() => setPage((p) => p + 1)}
                >
                    다음
                </button>
            </div>
        </div>
    );
}
