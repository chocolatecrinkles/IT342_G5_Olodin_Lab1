import { useState } from "react"
import { register } from "../services/AuthService"

export default function Register () {
    const [form, setForm] = useState({
        email: "",
        password: "",
        firstname: "",
        lastname: "",
    })

    const handleChange = (e) => {
        setForm({ ...form, [e.target.name]: e.target.value })
    }

    const handleSubmit = async (e) => {
        e.preventDefault()
        try {
            alert(await register(form))
        } catch (err) {
            alert(err.message)
        }
    }

    return (
        <form onSubmit={handleSubmit}>
            <h2>Register</h2>
            
            <input name="firstname" placeholder="Firstname" onChange={handleChange} />
            <input name="lastname" placeholder="Lastname" onChange={handleChange} />
            <input name="email" placeholder="Email" onChange={handleChange} />
            <input name="password" placeholder="Password" type="password" onChange={handleChange} />

            <button type="submit">Register</button>
        </form>
    )
};