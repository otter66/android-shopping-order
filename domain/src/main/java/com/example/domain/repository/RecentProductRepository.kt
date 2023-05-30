package com.example.domain.repository

import com.example.domain.Product
import com.example.domain.RecentProduct
import java.time.LocalDateTime

interface RecentProductRepository {
    fun getAll(): List<RecentProduct>
    fun getRecentProduct(productId: Int): RecentProduct?
    fun getMostRecentProduct(): RecentProduct?
    fun addRecentProduct(product: Product, viewedDateTime: LocalDateTime)
}
