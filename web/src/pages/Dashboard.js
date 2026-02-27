import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom"
import { logout } from "../services/AuthService"
import "./css/Dashboard.css"

export default function Dashboard() {
  const [user, setUser] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    const token = localStorage.getItem("token");

    fetch("http://localhost:8080/api/user/me", {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
    })
      .then((res) => {
        if (!res.ok) throw new Error("Unauthorized");
        return res.json();
      })
      .then((data) => setUser(data))
      .catch((err) => console.error(err));
  }, []);

  const handleLogout = () => {
    logout()
    navigate("/login")
  }

  if (!user) return <p>User not found.</p>;

  const initials =
    user.firstname.charAt(0).toUpperCase() +
    user.lastname.charAt(0).toUpperCase();

  return (
    <div className="dashboard-container">
        <div className="dashboard-card">

            <div className="avatar">{initials}</div>

            <h2 className="name">{user.firstname} {user.lastname}</h2>
            <p className="email">{user.email}</p>

            <button className="logout-btn" onClick={ handleLogout }>Logout</button>
        </div>
    </div>
  );
}
