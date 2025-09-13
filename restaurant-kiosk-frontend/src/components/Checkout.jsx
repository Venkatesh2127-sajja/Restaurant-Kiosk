import { useState } from "react";
import api from "../api";

export default function Checkout({ cart, clearCart, onOrdered }) {
  const [name, setName] = useState("");
  const [payment, setPayment] = useState("CASH");
  const [loading, setLoading] = useState(false);
  const total = cart.reduce((s,c)=>s + c.menuItem.price*c.quantity, 0);

  const placeOrder = async () => {
    if (cart.length === 0) return alert("Cart is empty");
    setLoading(true);
    try {
      const items = cart.map(c => ({ menuItemId: c.menuItem.id, quantity: c.quantity }));
      await api.post("/orders", { customerName: name || "Guest", paymentMethod: payment, items });
      clearCart();
      setName("");
      onOrdered?.();
      alert("Order placed!");
    } catch (e) {
      console.error(e); alert("Failed to place order");
    } finally { setLoading(false); }
  };

  return (
    <div className="bg-white rounded-xl shadow p-4">
      <h2 className="text-xl font-bold mb-3">💳 Checkout</h2>
      <input value={name} onChange={e=>setName(e.target.value)} placeholder="Customer name (optional)" className="w-full border px-3 py-2 mb-2 rounded"/>
      <select value={payment} onChange={e=>setPayment(e.target.value)} className="w-full border px-3 py-2 mb-3 rounded">
        <option value="CASH">Cash</option>
        <option value="CARD">Card</option>
        <option value="UPI">UPI</option>
      </select>
      <button disabled={loading} onClick={placeOrder} className="w-full bg-green-600 text-white py-2 rounded disabled:opacity-60">
        {loading ? "Placing..." : `Pay • ₹${total.toFixed(2)}`}
      </button>
    </div>
  );
}
