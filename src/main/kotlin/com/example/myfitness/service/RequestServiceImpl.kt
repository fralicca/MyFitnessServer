package com.example.myfitness.service

import com.example.myfitness.model.Richiesta
import com.example.myfitness.repository.RequestRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class RequestServiceImpl: RequestService {

    @Autowired
    lateinit var requestRepository: RequestRepository


    override fun findAllRequests() = requestRepository.findAll()

    override fun insertRequest(request: Richiesta) = requestRepository.save(request)

    override fun findRequestById(requestId: Int) = requestRepository.findById(requestId)

    override fun deleteRequestId(requestId: Int) = requestRepository.deleteById(requestId)

    override fun deleteAllRequests() = requestRepository.deleteAll()

    override fun getTrainerRequests(usernameId: String) = requestRepository.getTrainerRequests(usernameId)

}