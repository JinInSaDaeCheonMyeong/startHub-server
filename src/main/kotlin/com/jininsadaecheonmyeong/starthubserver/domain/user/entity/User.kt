package com.jininsadaecheonmyeong.starthubserver.domain.user.entity

import com.jininsadaecheonmyeong.starthubserver.domain.user.enumeration.AuthProvider
import java.util.*
import jakarta.persistence.*
import com.jininsadaecheonmyeong.starthubserver.domain.user.enumeration.UserRole
import com.jininsadaecheonmyeong.starthubserver.global.common.BaseEntity

@Entity(name = "user_tbl")
class User(
    @Id
    @Column(nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @Column(nullable = false, unique = true)
    val email: String,

    var password: String? = null,

    @Enumerated(EnumType.STRING)
    val role: UserRole = UserRole.USER,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var provider: AuthProvider,

    @Column(nullable = true)
    var providerId: String? = null
) : BaseEntity()