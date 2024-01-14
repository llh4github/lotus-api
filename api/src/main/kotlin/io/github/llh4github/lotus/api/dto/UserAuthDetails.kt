package io.github.llh4github.lotus.api.dto

import io.github.llh4github.lotus.model.auth.dto.UserAuthView
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

/**
 *
 *
 * Created At 2024/1/14 14:27
 * @author llh
 */
data class UserAuthDetails(
    val authInfo: UserAuthView
) : UserDetails {
    override fun getAuthorities(): List<SimpleGrantedAuthority> {
        val roles = authInfo.roles.map { it.code }
            .map { SimpleGrantedAuthority(it) }
            .toList()
        return roles
    }

    val userId = authInfo.id
    override fun getPassword(): String {
        return authInfo.password
    }

    override fun getUsername(): String {
        return authInfo.username
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}
