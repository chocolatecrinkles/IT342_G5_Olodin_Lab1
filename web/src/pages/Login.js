import { useState } from "react"
import { login } from "../services/AuthService"
import { useNavigate } from "react-router-dom"

export default function Login () {
    const [form, setForm] = useState({
        email: "",
        password: ""
    })
    const navigate = useNavigate();

    const handleChange = (e) => {
        setForm({ ...form, [e.target.name]: e.target.value })
    }

    const handleSubmit = async (e) => {
        e.preventDefault()
        try {
            alert(await login(form))
            navigate("/dashboard")
        } catch (err) {
            alert(err.message)
        }
    }

    return(
        <form onSubmit={handleSubmit}>
            <h2>Login</h2>

            <input name="email" placeholder="Email" onChange={handleChange}></input>
            <input name="password" placeholder="Password" type="password" onChange={handleChange} />

            <button type="submit">Login</button>
        </form>
    )
}