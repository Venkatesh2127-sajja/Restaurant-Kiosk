import { useState } from "react";

export default function MenuGrid({ menu = [], onAdd }) {
  const [category, setCategory] = useState("All");
  const [search, setSearch] = useState("");

  const cats = ["All", ...Array.from(new Set(menu.map(m => m.category))).filter(Boolean)];

  const filtered = menu.filter(item => 
    (category === "All" || item.category === category) &&
    (search.trim() === "" || item.name.toLowerCase().includes(search.toLowerCase()))
  );

  return (
    <div>
      <div className="mb-4 flex flex-wrap gap-3 items-center">
        <div className="flex gap-2 flex-wrap">
          {cats.map(c => (
            <button key={c} onClick={()=>setCategory(c)}
              className={`px-3 py-1 rounded ${category===c ? 'bg-red-600 text-white' : 'bg-gray-200'}`}>
              {c}
            </button>
          ))}
        </div>
        <input value={search} onChange={e=>setSearch(e.target.value)}
               placeholder="Search items..." className="ml-auto border rounded px-3 py-1 w-48"/>
      </div>

      <div className="grid grid-cols-1 sm:grid-cols-2 xl:grid-cols-3 gap-6">
        {filtered.map(item => (
          <div key={item.id} className="bg-white rounded-xl shadow p-4">
            {item.imageUrl && <img src={item.imageUrl} alt={item.name} className="h-40 w-full object-cover rounded mb-3"/>}
            <div className="flex justify-between items-start">
              <div>
                <h3 className="text-lg font-semibold">{item.name}</h3>
                <p className="text-sm text-gray-600">{item.description}</p>
                <span className="inline-block mt-2 text-xs bg-gray-100 px-2 py-0.5 rounded">{item.category}</span>
              </div>
              <div className="text-right">
                <div className="font-bold">₹{item.price.toFixed(2)}</div>
                <button onClick={()=>onAdd(item)} className="mt-3 bg-red-600 text-white px-3 py-1 rounded">Add</button>
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}
