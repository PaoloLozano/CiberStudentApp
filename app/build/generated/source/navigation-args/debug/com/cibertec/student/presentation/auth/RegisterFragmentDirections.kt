package com.cibertec.student.presentation.auth

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.cibertec.student.R

public class RegisterFragmentDirections private constructor() {
  public companion object {
    public fun actionRegisterToLogin(): NavDirections =
        ActionOnlyNavDirections(R.id.action_register_to_login)
  }
}
