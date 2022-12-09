package service

import entity.User

interface UserService {

    fun find(username: String): User?

}