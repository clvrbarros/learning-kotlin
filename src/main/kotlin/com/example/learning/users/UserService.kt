package com.example.learning.users

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
class UserService {

    @Autowired
    private lateinit var userRepository: UserRepository

    private fun isEmailAvaible(email:String): Boolean{
        try{
            userRepository.findByEmail(email)
            return false
        }catch (e: Exception){
            return true
        }
    }

    @Throws(EmailAlreadyInUseException::class)
    @Transactional(rollbackFor = [Exception::class], propagation = Propagation.REQUIRED)
    fun onCreateUserAccount(user: User): User{
        val isEmailAvailable = isEmailAvaible(user.email!!)
        if (!isEmailAvailable){
            throw EmailAlreadyInUseException()
        }
        userRepository.save(user)
        return user
    }

    @Throws(EmailNotExist::class)
    @Transactional(rollbackFor = [Exception::class], propagation = Propagation.REQUIRED)
    fun updateUserAccount(user: User): User {
        val isEmailAvailable = isEmailAvaible(user.email!!)
        if (!isEmailAvailable){
            userRepository.save(user)
            return user
        }
        throw EmailNotExist()
    }

    fun deleteUser(id: String): String{
        try{
            userRepository.deleteById(id)
            return "Deleted"
        }catch(e: Exception){
            return "Not deleted"
        }

    }

    fun getAllUsers(): List<User>{
        try{
            val users = userRepository.findAll()
            return users
        }catch (e: Exception){
            throw e
        }
    }
}