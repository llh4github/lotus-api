package io.github.llh4github.lotus.api.service.auth.impl

import io.github.llh4github.lotus.api.dao.UserDao
import io.github.llh4github.lotus.api.exceptions.auth.UserException
import io.github.llh4github.lotus.api.service.BaseServiceImpl
import io.github.llh4github.lotus.api.service.auth.UserService
import io.github.llh4github.lotus.model.auth.User
import io.github.llh4github.lotus.model.auth.dto.UserAddInput
import io.github.llh4github.lotus.model.auth.dto.UserUpdateInput
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    dao: UserDao,
    private val passwordEncoder: PasswordEncoder
) : BaseServiceImpl<User, UserDao>(dao), UserService {
    private val logger = KotlinLogging.logger {}
    override fun add(dto: UserAddInput): User? {
        if (baseDao.findByUsername(dto.username) != null) {
            throw UserException.usernameExist("${dto.username} 已存在")
        }
        val pwd = passwordEncoder.encode(dto.passwordInput)
        val toSave = dto.copy(password = pwd)
        return transactionTemplate.execute {
            baseDao.insert(toSave)
        }
    }

    override fun update(dto: UserUpdateInput): User? {
        return transactionTemplate.execute {
            baseDao.update(dto)
        }
    }
}