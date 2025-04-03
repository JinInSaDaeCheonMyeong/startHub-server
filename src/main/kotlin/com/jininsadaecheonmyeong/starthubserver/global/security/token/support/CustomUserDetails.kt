package com.jininsadaecheonmyeong.starthubserver.global.security.token.support

import com.jininsadaecheonmyeong.starthubserver.domain.user.entity.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

data class CustomUserDetails(
    val user: User
): UserDetails {
    override fun getAuthorities(): Collection<GrantedAuthority> {
        return listOf(GrantedAuthority { user.role.value })
    }

    override fun getPassword(): String = user.password

    override fun getUsername(): String = user.email

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true
}