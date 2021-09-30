package com.state.fish.data.network.repository
import com.google.firebase.auth.FirebaseUser
import com.state.fish.data.model.User
import com.state.fish.domain.IAuthRepository

@Suppress("UNREACHABLE_CODE")
class RepositoryAuth (private val dataSource: AuthDataSource) : IAuthRepository {
    override suspend fun signIn(email: String, password: String): FirebaseUser? =
        dataSource.signIn(email, password)

    override suspend fun signUp(user: User): FirebaseUser? =
        dataSource.signUp(user)
}