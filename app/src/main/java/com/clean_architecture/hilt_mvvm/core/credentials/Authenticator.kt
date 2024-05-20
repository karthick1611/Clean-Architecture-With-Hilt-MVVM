package com.clean_architecture.hilt_mvvm.core.credentials

import javax.inject.Inject


class Authenticator @Inject constructor() {
    //Learning purpose: We assume the user is always logged in
    //Here you should put your own logic to return whether the user
    //is authenticated or not
    fun userLoggedIn() = true
}
