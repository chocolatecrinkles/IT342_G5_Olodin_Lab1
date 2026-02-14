const API_URL = "http://localhost:8080/api";

export const register = async (data) => {
    const res = await fetch (`${API_URL}/auth/register`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(data),
    });

    if(!res.ok) throw new Error(await res.text());
    return res.text();
}

export const login = async (data) => {
    const res = await fetch(`${API_URL}/auth/login`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(data),
    });

    if (!res.ok) throw new Error(await res.text());

    localStorage.setItem("loggedIn", "true");
    localStorage.setItem("email", data.email);

    return res.text()
};

export const logout = () => {
  localStorage.clear();
};

export const isAuthenticated = () => {
  return localStorage.getItem("loggedIn") === "true";
};
