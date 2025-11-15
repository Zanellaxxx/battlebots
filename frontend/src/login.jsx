import { useState } from "react";
import "./login.css";

export default function Login() {
    const [email, setEmail] = useState("");
    const [senha, setSenha] = useState("");

    const handleSubmit = (e) => {
        e.preventDefault();

        if (!email || !senha) {
            alert("Preencha todos os campos!");
            return;
        }

        console.log("Dados enviados:", { email, senha });
        alert("Login realizado com sucesso!");
    };

    return (
        <div className="login-container">
            <div className="login-card">
                <h2>Login</h2>

                <form onSubmit={handleSubmit}>
                    <input
                        type="email"
                        placeholder="Seu e-mail"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                    />

                    <input
                        type="password"
                        placeholder="Sua senha"
                        value={senha}
                        onChange={(e) => setSenha(e.target.value)}
                    />

                    <button type="submit">Entrar</button>
                </form>
            </div>
        </div>
    );
}
