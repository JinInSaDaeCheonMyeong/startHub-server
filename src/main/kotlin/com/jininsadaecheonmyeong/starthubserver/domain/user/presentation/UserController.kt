package com.jininsadaecheonmyeong.starthubserver.domain.user.presentation

import com.jininsadaecheonmyeong.starthubserver.domain.user.data.RefreshRequest
import com.jininsadaecheonmyeong.starthubserver.domain.user.data.UpdateUserProfileRequest
import com.jininsadaecheonmyeong.starthubserver.domain.user.data.UserRequest
import com.jininsadaecheonmyeong.starthubserver.domain.user.docs.UserDocs
import com.jininsadaecheonmyeong.starthubserver.domain.user.service.UserService
import com.jininsadaecheonmyeong.starthubserver.global.common.BaseResponse
import com.jininsadaecheonmyeong.starthubserver.global.security.token.support.UserAuthenticationHolder
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserController(
    private val userService: UserService,
) : UserDocs {
    @PostMapping("/sign-up")
    override fun signUp(@Valid request: UserRequest) =
        BaseResponse.of(userService.signUp(request), "회원가입 성공")

    @PostMapping("/sign-in")
    override fun signIn(request: UserRequest) =
        BaseResponse.of(userService.signIn(request), "로그인 성공")

    @PostMapping("/reissue")
    override fun reissue(request: RefreshRequest) =
        BaseResponse.of(userService.reissue(request), "토큰 재발급 성공")

    @PatchMapping("/profile")
    fun updateUserProfile(@RequestBody request: UpdateUserProfileRequest): ResponseEntity<BaseResponse<Unit>> {
        val currentUser = UserAuthenticationHolder.current()
        userService.updateUserProfile(currentUser, request.username, request.interests, request.profileImage)
        return BaseResponse.of("유저 프로필 설정 성공")
    }
}