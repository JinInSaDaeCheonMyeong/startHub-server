package com.jininsadaecheonmyeong.starthubserver.global.infra.oauth.google.service

import com.jininsadaecheonmyeong.starthubserver.domain.user.exception.InvalidTokenException
import com.jininsadaecheonmyeong.starthubserver.global.infra.oauth.google.configuration.GoogleProperties
import com.jininsadaecheonmyeong.starthubserver.global.infra.oauth.google.data.GoogleTokenResponse
import com.jininsadaecheonmyeong.starthubserver.global.infra.oauth.google.data.GoogleUserInfo
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono

@Service
class GoogleService(
    private val googleProperties: GoogleProperties,
    webClientBuilder: WebClient.Builder
) {
    private val webClient = webClientBuilder.build()

    fun exchangeCodeForUserInfo(code: String): GoogleUserInfo {
        val tokenResponse = webClient.post()
            .uri(googleProperties.tokenUri)
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                BodyInserters.fromFormData("client_id", googleProperties.clientId)
                    .with("client_secret", googleProperties.clientSecret)
                    .with("code", code)
                    .with("redirect_uri", googleProperties.redirectUri)
                    .with("grant_type", googleProperties.grantType)
            )
            .retrieve()
            .bodyToMono<GoogleTokenResponse>()
            .block()

        val accessToken = tokenResponse?.access_token
            ?: throw InvalidTokenException("유효하지 않은 엑세스 토큰")

        return webClient.get()
            .uri(googleProperties.userInfoUri)
            .headers { it.setBearerAuth(accessToken) }
            .retrieve()
            .bodyToMono<GoogleUserInfo>()
            .block() ?: throw InvalidTokenException("사용자 정보 조회 실패")
    }
}