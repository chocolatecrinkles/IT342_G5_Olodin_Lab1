import { BrowserRouter, Route, Routes, Navigate} from "react-router-dom"
import Register from "./pages/Register"
import Login from "./pages/Login"
import Dashboard from "./pages/Dashboard"
import { isAuthenticated } from "./services/AuthService"

function PrivateRoute({children}) {
  return isAuthenticated() ? children : <Navigate to="/login" />
}

export default function App () {
  return(
    <BrowserRouter>
      <Routes>
        <Route path="/register" element={ <Register/> } />
        <Route path="/login" element={ <Login/> } />
        
        <Route path="/dashboard" 
        element={ 
          <PrivateRoute>
            <Dashboard/> 
          </PrivateRoute>
          } />

        <Route path="*" element={ <Login /> } />
      </Routes>
    </BrowserRouter>
  )
};