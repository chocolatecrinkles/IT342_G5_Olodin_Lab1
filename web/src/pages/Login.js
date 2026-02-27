import { useState } from "react"
import { login } from "../services/AuthService"
import { useNavigate } from "react-router-dom"
import "./css/Login.css"
export default function Login () {
    const [form, setForm] = useState({
        email: "",
        password: ""
    })

    const [modal, setModal] = useState({
        show: false,
        message: ""
    })

    const navigate = useNavigate();

    const handleChange = (e) => {
        setForm({ ...form, [e.target.name]: e.target.value })
    }

    const handleSubmit = async (e) => {
        e.preventDefault()
        try {
            await login(form)
            navigate("/dashboard")
        } catch (err) {
            setModal({
                show: true,
                message: err.message
            })
        }
    }

    const closeModal = () => {
        setModal({
            show: false,
            message: ""
        })
    }

    return(
        <div className="login-container">
            <form className="login-card" onSubmit={handleSubmit}>
                <h2>Login</h2>

                <input name="email" placeholder="Email" onChange={handleChange}></input>
                <input name="password" placeholder="Password" type="password" onChange={handleChange} />

                <button type="submit">Login</button>

                <p className="register-text">
                    Don't have an account? {""}
                    <span onClick={ () => navigate("/register")}>
                    Register
                    </span>
                </p>
            </form>

            {modal.show && (
                <div className="modal-overlay" onClick={ closeModal }>
                    <div className="modal">

                        <h3>Login Failed</h3>
                        <p>{modal.message}</p>
                        <button onClick={ closeModal }>Close</button>

                    </div>
                </div>
            )}
        </div>
        
    )
}