import { useState } from "react"
import { register } from "../services/AuthService"
import { useNavigate } from "react-router-dom"
import "./css/Register.css"

export default function Register () {
    const [form, setForm] = useState({
        email: "",
        password: "",
        firstname: "",
        lastname: "",
    })
    
    const [modal, setModal] = useState({
        show: false,
        message: "",
        type: ""
    })

    const navigate = useNavigate()

    const handleChange = (e) => {
        setForm({ ...form, [e.target.name]: e.target.value })
    }

    const handleSubmit = async (e) => {
        e.preventDefault()
        try {
            await register(form)
            setModal({
                show: true,
                message: "Account successfully created!",
                type: "success"
            })
        } catch (err) {
            setModal({
                show: true,
                message: err.message,
                type: "error"
            })
        }
    }

    const closeModal = () => {
        if(modal.type === "success") {
            navigate("/login")
        }
        setModal({
            show: false,
            message: "",
            type: ""
        })
    }

    return (
        <div className="register-container">
            <form className="register-card" onSubmit={handleSubmit}>
                <h2>Register</h2>
                
                <input name="firstname" placeholder="Firstname" onChange={handleChange} />
                <input name="lastname" placeholder="Lastname" onChange={handleChange} />
                <input name="email" placeholder="Email" onChange={handleChange} />
                <input name="password" placeholder="Password" type="password" onChange={handleChange} />

                <button type="submit">Register</button>

                <p className="login-text">
                    Already have an account? {""}
                    <span onClick={ () => navigate("/login") }>
                    Login
                    </span>
                </p>
            </form>

            {modal.show && (
                <div className="modal-overlay" onClick={ closeModal }>
                    <div className="modal">

                        <h3>{ modal.type === "success" ? "Registration Successful" : "Registration Failed" }</h3>
                        <p>{modal.message}</p>
                        <button onClick={ closeModal }>{ modal.type === "success" ? "Go to Login page" : "Close"}</button>

                    </div>
                </div>
            )}
        </div>
    )
};