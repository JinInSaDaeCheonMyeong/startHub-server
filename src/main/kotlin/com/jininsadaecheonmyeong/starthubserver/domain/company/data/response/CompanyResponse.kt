package com.jininsadaecheonmyeong.starthubserver.domain.company.data.response

import com.jininsadaecheonmyeong.starthubserver.domain.company.entity.Company
import com.jininsadaecheonmyeong.starthubserver.domain.user.enums.BusinessType
import java.time.LocalDateTime
import java.util.*

data class CompanyResponse(
    val id: Long,
    val companyName: String,
    val companyDescription: String,
    val companyCategory: BusinessType,
    val businessDescription: String,
    val founderId: UUID,
    val founderName: String?,
    val companyUrl: String?,
    val contactEmail: String,
    val contactNumber: String,
    val address: String?,
    val employeeCount: Int,
    val logoImage: String?,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?
) {
    companion object {
        fun from(company: Company): CompanyResponse {
            return CompanyResponse(
                id = company.id!!,
                companyName = company.companyName,
                companyDescription = company.companyDescription,
                companyCategory = company.companyCategory,
                businessDescription = company.businessDescription,
                founderId = company.founder.id!!,
                founderName = company.founder.username,
                companyUrl = company.companyUrl,
                contactEmail = company.contactEmail,
                contactNumber = company.contactNumber,
                address = company.address,
                employeeCount = company.employeeCount,
                logoImage = company.logoImage,
                createdAt = company.createdAt,
                updatedAt = company.updatedAt
            )
        }
    }
}