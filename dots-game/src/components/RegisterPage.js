import React, { useState } from "react";
import {
  getAuth,
  createUserWithEmailAndPassword,
  updateProfile,
  signInWithPopup,
  GoogleAuthProvider,
  signInAnonymously,
} from "firebase/auth";
import { useNavigate } from "react-router-dom";
import "./LoginPage.css";

function RegisterPage() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [verifyPassword, setVerifyPassword] = useState("");
  const [name, setName] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (password !== verifyPassword) {
      setError("Passwords do not match.");
      return;
    }

    const auth = getAuth();
    try {
      const userCredential = await createUserWithEmailAndPassword(
        auth,
        email,
        password
      );
      await updateProfile(userCredential.user, { displayName: name });
      navigate("/game");
    } catch (err) {
      setError(err.message);
    }
  };

  const handleGoogleSignIn = async () => {
    const auth = getAuth();
    const provider = new GoogleAuthProvider();
    try {
      await signInWithPopup(auth, provider);
      navigate("/game");
    } catch (err) {
      setError(err.message);
    }
  };

  const handleAnonymousSignIn = async () => {
    const auth = getAuth();
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
                Register
              </h1>
              <form onSubmit={handleSubmit}>
                <div className="form-group">
                  <label htmlFor="name" className="light-text">
                    Name:
                  </label>
                  <input
                    type="text"
                    id="name"
                    className="form-control"
                    autoComplete="off"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                  />
                </div>
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
                <div className="form-group">
                  <label htmlFor="verifyPassword" className="light-text">
                    Verify Password:
                  </label>
                  <input
                    type="password"
                    id="verifyPassword"
                    className="form-control"
                    autoComplete="off"
                    value={verifyPassword}
                    onChange={(e) => setVerifyPassword(e.target.value)}
                  />
                </div>
                <br />
                {error && <p className="text-danger">{error}</p>}
                <button type="submit" className="btn btn-primary w-100">
                  Register
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
                  Sign up with Google
                </button>
                <button
                  className="btn btn-secondary w-100"
                  onClick={handleAnonymousSignIn}
                >
                  Sign up as Guest
                </button>
              </div>
              <div className="text-center mt-3">
                <p
                  className="light-text"
                  style={{ textShadow: "1px 1px 2px rgba(0, 0, 0, 0.5)" }}
                >
                  Already have an account?{" "}
                  <span
                    className="font-weight-bold text-dark"
                    style={{ cursor: "pointer" }}
                    onClick={() => navigate("/login")}
                  >
                    Click here to Sign in
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

export default RegisterPage;
