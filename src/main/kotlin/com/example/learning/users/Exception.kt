package com.example.learning.users

class EmailAlreadyInUseException : Exception("Email já está em uso!")

class EmailNotExist: Exception("Email não cadastrado")