package com.example.learning.users

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.persistence.Id
import javax.validation.Valid

@RestController
@RequestMapping("/v1/users")
class UserController {

    @Autowired
    private lateinit var userService: UserService

    @RequestMapping(method = [RequestMethod.DELETE])
    fun deleteUser(@RequestParam id: String) : ResponseEntity<Any> {
        try{
            return ResponseEntity(userService.deleteUser(id), HttpStatus.OK)
        }catch (e: Exception){
            return ResponseEntity(e.message, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @RequestMapping(method = [RequestMethod.POST])
    fun createUser(@Valid @RequestBody user: User) : ResponseEntity<Any> {
        try{
            return ResponseEntity(userService.onCreateUserAccount(user), HttpStatus.CREATED)
        }catch(e: EmailAlreadyInUseException){
            return ResponseEntity(e.message, HttpStatus.CONFLICT)
        }catch(e: Exception){
            e.printStackTrace()
            return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @RequestMapping(method = [RequestMethod.PUT])
    fun updateUser(@Valid @RequestBody user: User) : ResponseEntity<Any> {
        try{
            return ResponseEntity(userService.updateUserAccount(user), HttpStatus.CREATED)
        }catch(e: EmailNotExist){
            return ResponseEntity(e.message, HttpStatus.CONFLICT)
        }catch(e: Exception){
            e.printStackTrace()
            return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @RequestMapping( method = [RequestMethod.GET])
    fun getAllUsers() : ResponseEntity<Any> {
        try{
            return ResponseEntity(userService.getAllUsers(), HttpStatus.FOUND)
        }catch(e: Exception){
            e.printStackTrace()
            return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
}