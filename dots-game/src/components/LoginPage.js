import React, { useState } from "react";
import { app } from "./firebaseConfig";
import { useNavigate } from "react-router-dom";
import {
  getAuth,
  signInWithEmailAndPassword,
  signInWithPopup,
  GoogleAuthProvider,
  signInAnonymously,
} from "firebase/auth";
import "./LoginPage.css";

function LoginPage() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    const auth = getAuth(app);
    try {
      await signInWithEmailAndPassword(auth, email, password);
      navigate("/game");
    } catch (err) {
      setError(err.message);
    }
  };

  const handleGoogleSignIn = async () => {
    const auth = getAuth(app);
    const provider = new GoogleAuthProvider();
    try {
      await signInWithPopup(auth, provider);
      navigate("/game");
    } catch (err) {
      setError(err.message);
    }
  };

  const handleAnonymousSignIn = async () => {
    const auth = getAuth(app);
    try {
      await signInAnonymously(auth);
      navigate("/game");
    } catch (err) {
      setError(err.message);
    }
  };

  return (
    <div className="container">
      <div className="row justify-content-center">
        <div className="col-md-6">
          <div className="card mt-5 login-card">
            <div className="card-body">
              <h1
                className="text-center mb-4 light-text"
                style={{ textShadow: "1px 1px 2px rgba(0, 0, 0, 0.5)" }}
              >
                Login
              </h1>
              <form onSubmit={handleSubmit}>
                <div className="form-group">
                  <label htmlFor="email" className="light-text">
                    Email:
                  </label>
                  <input
                    type="email"
                    id="email"
                    className="form-control"
                    autoComplete="off"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    required
                  />
                </div>
                <div className="form-group">
                  <label htmlFor="password" className="light-text">
                    Password:
                  </label>
                  <input
                    type="password"
                    id="password"
                    className="form-control"
                    autoComplete="off"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                  />
                </div>
                <br />
                {error && <p className="text-danger">{error}</p>}
                <button type="submit" className="btn btn-primary w-100">
                  Login
                </button>
              </form>
              <div className="text-center mt-3">
                <p
                  className="light-text"
                  style={{ textShadow: "1px 1px 2px rgba(0, 0, 0, 0.5)" }}
                >
                  Or
                </p>
                <button
                  className="btn btn-danger mb-2 w-100"
                  onClick={handleGoogleSignIn}
                >
                  Sign In with Google
                </button>
                <button
                  className="btn btn-secondary w-100"
                  onClick={handleAnonymousSignIn}
                >
                  Sign In as Guest
                </button>
              </div>
              <div className="text-center mt-3">
                <p
                  className="light-text"
                  style={{ textShadow: "1px 1px 2px rgba(0, 0, 0, 0.5)" }}
                >
                  Not a member?{" "}
                  <span
                    className="font-weight-bold text-dark"
                    style={{ cursor: "pointer" }}
                    onClick={() => navigate("/register")}
                  >
                    Click here to register
                  </span>
                </p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default LoginPage;
