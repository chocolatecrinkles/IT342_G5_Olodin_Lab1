import { logout } from "../services/AuthService"
import { useNavigate} from "react-router-dom"

export default function Dashboard() {
    const email = localStorage.getItem("email")
    const navigate = useNavigate()

    const handleLogout = (e) => {
        logout();
        navigate("/login")
    }
    
    return(
        <>
            <h2>Dashboard</h2>
            <p>Email: {email}</p>

            <hr/>
            <button onClick={handleLogout}>Logout</button>
        </>
    )
};