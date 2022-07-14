package sebastiani.app_rondon.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import sebastiani.app_rondon.Firebase.FirestoreClass
import sebastiani.app_rondon.MenuPrincipalActivity
import sebastiani.app_rondon.R
import sebastiani.app_rondon.databinding.ActivityLoginBinding
import sebastiani.app_rondon.view.BaseActivity


class LoginActivity : BaseActivity(), View.OnClickListener{

//    lateinit var binding: ActivityLoginBinding
//    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        tv_forgot_password.setOnClickListener(this)
        // Click event assigned to Login button.
        btn_login.setOnClickListener(this)
        // Click event assigned to Register text.
        tv_register.setOnClickListener(this)
            }
    override fun onClick(p0: View?) {
        if (p0 != null) {
            when (p0.id) {

                R.id.tv_forgot_password -> {
                    val intent = Intent(this@LoginActivity, ResetPasswordActivity::class.java)
                    startActivity(intent)
                }

                R.id.btn_login -> {



                    logInRegisteredUser()                    // END
                }

                R.id.tv_register -> {
                    // Launch the register screen when the user clicks on the text.
                    val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    private fun validateLoginDetails(): Boolean {
        return when {
            TextUtils.isEmpty(et_email.text.toString().trim { it <= ' ' }) -> {
                et_email.requestFocus()

                showErrorSnackBar(resources.getString(R.string.err_msg_enter_email), true)
                false
            }
            TextUtils.isEmpty(et_password.text.toString().trim { it <= ' ' }) -> {
                et_password.requestFocus()

                showErrorSnackBar(resources.getString(R.string.err_msg_enter_password), true)
                false
            }
            else -> {
                //   showErrorSnackBar("Your details are valid.", false)

                true
            }
        }
    }


    private fun logInRegisteredUser() {

        if (validateLoginDetails()) {

            // Show the progress dialog.
            showProgressDialog(resources.getString(R.string.please_wait))

            // Get the text from editText and trim the space
            val email = et_email.text.toString().trim { it <= ' ' }
            val password = et_password.text.toString().trim { it <= ' ' }

            // Log-In using FirebaseAuth
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->

                    // Hide the progress dialog
                    hideProgressDialog()

                    if (task.isSuccessful) {
                        //FirestoreClass().getUserDetails(this@LoginActivity)
                        // showErrorSnackBar("You are logged in successfully.", false)
                        Toast.makeText(this, "Te has logueado exitosamente!,$et_email", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        showErrorSnackBar(task.exception!!.message.toString(), true)
                    }
                }
        }
    }


}