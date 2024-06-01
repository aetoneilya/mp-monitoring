package com.machrist.mpmonitoring.domain

import com.machrist.mpmonitoring.model.User
import com.machrist.mpmonitoring.repository.UserRepository
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class UserService(
    private val userRepository: UserRepository,
) {
    /**
     * Создание пользователя
     *
     * @return созданный пользователь
     */
    fun create(user: User): User {
        if (userRepository.existsByLogin(user.username)) {
            throw IllegalArgumentException("Пользователь с таким именем уже существует")
        }

        return userRepository.save(user)
    }

    /**
     * Получение пользователя по имени пользователя
     *
     * @return пользователь
     */
    fun getByUsername(username: String) = userRepository.findByLogin(username)

    /**
     * Получение пользователя по имени пользователя
     *
     *
     * Нужен для Spring Security
     *
     * @return пользователь
     */
    fun userDetailsService() = UserDetailsService(::getByUsername)

    /**

     * Получение пользователя по имени пользователя
     *
     *
     * Нужен для Spring Security
     *
     * @return пользователь
     */
    fun reactiveUserDetailsService() = ReactiveUserDetailsService {
        Mono.fromCallable {
            getByUsername(it)
        }
    }
}
