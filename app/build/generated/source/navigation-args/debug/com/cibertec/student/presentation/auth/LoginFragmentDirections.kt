package com.cibertec.student.presentation.auth

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.cibertec.student.R

public class LoginFragmentDirections private constructor() {
  public companion object {
    public fun actionLoginToRegister(): NavDirections =
        ActionOnlyNavDirections(R.id.action_login_to_register)
  }
}
