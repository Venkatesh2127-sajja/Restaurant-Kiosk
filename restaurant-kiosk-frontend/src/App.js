import { useEffect, useState } from "react";
import api from "./api";
import MenuGrid from "./components/MenuGrid";
import Cart from "./components/Cart";
import Checkout from "./components/Checkout";
import Dashboard from "./components/Dashboard";
import Navbar from "./components/Navbar";

export default function App() {
  const [menu, setMenu] = useState([]);
  const [cart, setCart] = useState([]);
  const [refreshOrders, setRefreshOrders] = useState(0);

  useEffect(() => { api.get("/menu").then(r => setMenu(r.data)).catch(e=>console.error(e)); }, []);

  const addToCart = (item) => {
    setCart(prev => {
      const idx = prev.findIndex(p => p.menuItem.id === item.id);
      if (idx >= 0) { const copy = [...prev]; copy[idx] = {...copy[idx], quantity: copy[idx].quantity+1}; return copy; }
      return [...prev, {menuItem: item, quantity:1}];
    });
  };
  const changeQty = (id, q) => setCart(prev => prev.map(c => c.menuItem.id===id? {...c, quantity: Math.max(1,q)}:c));
  const removeItem = (id) => setCart(prev => prev.filter(c => c.menuItem.id !== id));
  const clearCart = () => setCart([]);

  return (
    <div>
      <Navbar />
      <div className="grid grid-cols-1 lg:grid-cols-3 gap-6 p-6">
        <div className="lg:col-span-2">
          <MenuGrid menu={menu} onAdd={addToCart}/>
        </div>
        <div className="lg:col-span-1 space-y-6">
          <Cart cart={cart} changeQty={changeQty} removeItem={removeItem}/>
          <Checkout cart={cart} clearCart={clearCart} onOrdered={() => setRefreshOrders(x=>x+1)}/>
        </div>
      </div>
      <Dashboard refreshKey={refreshOrders}/>
    </div>
  );
}
