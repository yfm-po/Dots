import React from "react";
import { getAuth, signOut } from "firebase/auth";
import { app } from "./firebaseConfig";
import { useNavigate } from "react-router-dom";

function Logout() {
  const navigate = useNavigate();

  const handleLogout = async () => {
    const auth = getAuth(app);
    await signOut(auth);
    navigate("/login");
  };

  return (
    <button className="nav-link btn btn-link" onClick={handleLogout}>
      Logout
    </button>
  );
}

export default Logout;
