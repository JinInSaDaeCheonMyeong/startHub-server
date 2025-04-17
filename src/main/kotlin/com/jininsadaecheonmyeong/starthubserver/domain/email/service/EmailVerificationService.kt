package com.jininsadaecheonmyeong.starthubserver.domain.email.service

import com.jininsadaecheonmyeong.starthubserver.domain.email.entity.Email
import com.jininsadaecheonmyeong.starthubserver.domain.email.exception.EmailAlreadyVerifiedException
import com.jininsadaecheonmyeong.starthubserver.domain.email.exception.EmailNotFoundException
import com.jininsadaecheonmyeong.starthubserver.domain.email.exception.ExpiredEmailException
import com.jininsadaecheonmyeong.starthubserver.domain.email.repository.EmailRepository
import org.springframework.stereotype.Service
import java.security.SecureRandom
import java.time.LocalDateTime

@Service
class EmailVerificationService(
    private val emailRepository: EmailRepository,
    private val emailService: EmailService
) {
    fun sendVerificationCode(email: String) {
        if (emailRepository.findByEmailAndIsVerifiedTrue(email) != null) {
            throw EmailAlreadyVerifiedException("이미 인증된 이메일")
        }

        val existingEmail = emailRepository.findByEmail(email)
        val code = generateVerificationCode()

        val emailVerification = existingEmail?.apply {
            verificationCode = code
        }
            ?: Email(
                email = email,
                verificationCode = code
            )

        emailRepository.save(emailVerification)

        emailService.sendEmail(email, code)
    }


    fun generateVerificationCode(): String {
        return String.format("%06d", SecureRandom().nextInt(1_000_000))
    }

    fun verifyCode(email: String, code: String) {
        val verification = emailRepository.findByEmailAndVerificationCode(email, code) ?: throw EmailNotFoundException("잘못된 이메일 인증코드")

        if (verification.expirationDate.isBefore(LocalDateTime.now())) {
            throw ExpiredEmailException("만료된 이메일")
        } else {
            verification.isVerified = true
            emailRepository.save(verification)
        }
    }
}
