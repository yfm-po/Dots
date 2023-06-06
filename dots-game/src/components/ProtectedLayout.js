import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { app } from "./firebaseConfig";
import { getAuth, onAuthStateChanged } from "firebase/auth";

function ProtectedLayout({ children }) {
		const navigate = useNavigate();

		useEffect(() => {
				const auth = getAuth(app);
				const unsubscribe = onAuthStateChanged(auth, (user) => {
						if (!user) {
								navigate("/login");
						}
				});

				return () => {
						unsubscribe();
				};
		}, [navigate]);

		return <>{children}</>;
}

export default ProtectedLayout;