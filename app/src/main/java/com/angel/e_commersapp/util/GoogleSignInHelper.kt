package com.angel.e_commersapp.util

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

object GoogleSignInHelper {
    private const val WEB_CLIENT_ID ="378417506504-q5r7755v1rrp8kejetapaso9d9h4fsi0.apps.googleusercontent.com"
    fun getGoogleSignInClient(context: Context): GoogleSignInClient{
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(WEB_CLIENT_ID)
            .requestEmail()
            .requestProfile()
            .build()

        return GoogleSignIn.getClient(context,gso)
    }
}