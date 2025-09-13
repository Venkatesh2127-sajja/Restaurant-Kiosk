import { useEffect, useState } from "react";
import api from "../api";

export default function Dashboard({ refreshKey }) {
  const [orders, setOrders] = useState([]);
  const [stats, setStats] = useState({});

  const load = () => {
    api.get("/orders").then(r=>setOrders(r.data)).catch(e=>console.error(e));
    api.get("/dashboard/stats").then(r=>setStats(r.data)).catch(()=>{});
  };

  useEffect(()=>{ load(); }, []);
  useEffect(()=>{ load(); }, [refreshKey]);

  const setStatus = async (id, status) => {
    await api.put(`/orders/${id}/status`, null, { params: { status }});
    load();
  };

  return (
    <div className="p-6">
      <h2 className="text-2xl font-bold mb-4">📊 Dashboard</h2>

      <div className="grid grid-cols-1 md:grid-cols-3 gap-4 mb-6">
        <div className="bg-white rounded p-4 shadow"><div className="text-sm">Orders</div><div className="text-xl font-bold">{stats.totalOrders ?? '-'}</div></div>
        <div className="bg-white rounded p-4 shadow"><div className="text-sm">Sales</div><div className="text-xl font-bold">₹{(stats.totalSales||0).toFixed? (stats.totalSales||0).toFixed(2) : (stats.totalSales||0)}</div></div>
        <div className="bg-white rounded p-4 shadow"><div className="text-sm">Categories</div>
          <ul className="mt-2 text-sm">
            {stats.menuCategories ? Object.entries(stats.menuCategories).map(([k,v])=> <li key={k}>{k}: {v}</li>) : <li>-</li>}
          </ul>
        </div>
      </div>

      <div className="bg-white rounded shadow overflow-x-auto">
        <table className="w-full">
          <thead className="bg-gray-200">
            <tr><th className="p-2">ID</th><th className="p-2">Customer</th><th className="p-2">Status</th><th className="p-2">Items</th><th className="p-2">Total</th><th className="p-2">Actions</th></tr>
          </thead>
          <tbody>
            {orders.map(o => (
              <tr key={o.id} className="border-t">
                <td className="p-2">{o.id}</td>
                <td className="p-2">{o.customerName}</td>
                <td className="p-2">{o.status}</td>
                <td className="p-2">
                  <ul className="list-disc ml-5">
                    {o.items.map(it => <li key={it.menuItemId}>{it.name} x {it.quantity}</li>)}
                  </ul>
                </td>
                <td className="p-2">₹{o.totalAmount?.toFixed(2)}</td>
                <td className="p-2 space-x-2">
                  <button onClick={()=>setStatus(o.id,'PREPARING')} className="px-2 py-1 bg-yellow-500 rounded text-white">Preparing</button>
                  <button onClick={()=>setStatus(o.id,'COMPLETED')} className="px-2 py-1 bg-green-600 rounded text-white">Complete</button>
                  <button onClick={()=>setStatus(o.id,'CANCELLED')} className="px-2 py-1 bg-red-600 rounded text-white">Cancel</button>
                </td>
              </tr>
            ))}
            {orders.length===0 && <tr><td className="p-4 text-center" colSpan={6}>No orders</td></tr>}
          </tbody>
        </table>
      </div>
    </div>
  );
}
