package service

import entity.Client
import kotlin.jvm.Throws

interface TransactionService {
    @Throws(IllegalArgumentException::class)
    fun withdraw(client: Client, amount: Int, comment: String)
    @Throws(IllegalArgumentException::class)
    fun deposit(client: Client, amount: Int, comment: String)
    @Throws(IllegalArgumentException::class)
    fun getBalance(client: Client): Int
}