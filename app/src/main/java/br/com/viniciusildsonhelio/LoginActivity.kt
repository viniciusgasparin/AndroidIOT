package br.com.viniciusildsonhelio

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance();

        if(usuarioLogado()){
            proximaTelaLogada()
        }

        botaoEntrar.setOnClickListener {
            validarUsuario()
        }
    }
//
//    public override fun onStart() {
//        super.onStart()
//        // Check if user is signed in (non-null) and update UI accordingly.
//        val currentUser = mAuth.currentUser
//        updateUI(currentUser)
//    }

    private fun usuarioLogado(): Boolean {
        return mAuth.currentUser != null
    }

    private fun proximaTelaLogada() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun validarUsuario() {
        val email = inputEmail.editText?.text.toString()
        val senha = inputSenha.editText?.text.toString()
        if( email.isEmpty()){
            inputEmail.isErrorEnabled = true
            inputEmail.error = "favor preencher o email"
            return
        } else {
            inputEmail.isErrorEnabled = false
        }

        if( senha.isEmpty()){
            inputSenha.isErrorEnabled = true
            inputSenha.error = "favor preencher a senha"
            return
        } else {
            inputSenha.isErrorEnabled = false
        }

        mAuth.signInWithEmailAndPassword(
            email, senha)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    proximaTelaLogada()
                } else {
                    Toast.makeText(this, "Falha na autenticação", Toast.LENGTH_LONG).show()
                }
            }
    }
}
