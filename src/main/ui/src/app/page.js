"use client";
import Simmulator from "./components/simmulator.js";

// Button Component
const Button = ({ onClick, className, label }) => (
  <button
    // onClick={onClick}
    className={`${className} text-white bg-slate-800 hover:bg-slate-600 p-4 rounded-lg transition-all shadow-md`}
    type="submit"
  >
    {label}
  </button>
);

export default function Home() {
  return (
    <div className="flex flex-col min-h-screen items-center justify-evenly p-8 bg-slate-700 sm:p-8">
      {/*Header*/}
      <div className="w-full max-w-4xl items-center">
        <h1 className="text-4xl px-4 pb-4 pt-1 text-slate-800 text-center cursor-pointer hover:text-slate-600 transition-all">
          Pendulum App
        </h1>
      </div>
      {/*Body*/}
      <div className="grid grid-cols-10 gap-4 shadow-md justify-between rounded-lg w-full mb-auto">
        <div className="col-span-6 p-4 text-black m-4">
          <Simmulator></Simmulator>
        </div>
        <div className="col-span-4 text-blackf m-4">
          <form className=" items-center text-black">
            <Button
              onClick={""}
              className="w-20 bg-slate-200"
              label="Refresh"
            />
          </form>
        </div>
      </div>
    </div>
  );
}
