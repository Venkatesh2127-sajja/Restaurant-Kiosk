export default function Cart({ cart, changeQty, removeItem }) {
  const total = cart.reduce((s,c) => s + c.menuItem.price * c.quantity, 0);
  return (
    <div className="bg-white rounded-xl shadow p-4">
      <h2 className="text-xl font-bold mb-3">🛒 Cart</h2>
      {cart.length===0 ? <p className="text-gray-500">Cart is empty</p> : (
        <ul className="space-y-3">
          {cart.map(({menuItem, quantity}) => (
            <li key={menuItem.id} className="flex justify-between items-center">
              <div>
                <div className="font-medium">{menuItem.name}</div>
                <div className="text-sm text-gray-600">₹{menuItem.price.toFixed(2)}</div>
              </div>
              <div className="flex items-center gap-2">
                <input type="number" min="1" value={quantity} 
                  onChange={e => changeQty(menuItem.id, parseInt(e.target.value || 1,10))}
                  className="w-16 border rounded px-2 py-1"/>
                <button onClick={()=>removeItem(menuItem.id)} className="text-red-600">Remove</button>
              </div>
            </li>
          ))}
        </ul>
      )}
      <div className="mt-4 flex justify-between font-semibold">
        <span>Total</span><span>₹{total.toFixed(2)}</span>
      </div>
    </div>
  );
}
