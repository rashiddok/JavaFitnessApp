package service

import entity.Client
import kotlin.jvm.Throws

interface ClientService {
    @Throws(IllegalArgumentException::class)
    fun create(firstName: String, secondName: String, patronymic: String): Client
    fun find(pattern: String): List<Client>
    fun getAll(): List<Client>
    @Throws(IllegalArgumentException::class)
    fun update(client: Client): Client
    fun remove(client: Client)
}