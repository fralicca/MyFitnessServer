package com.example.myfitness.repository

import com.example.myfitness.model.Scheda
import com.example.myfitness.model.Utente
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.web.multipart.MultipartFile;
import java.net.URL
import java.nio.file.Path
import java.util.stream.Stream

@Repository
interface UserRepository : JpaRepository<Utente, String> {

   @Query("SELECT * FROM utente u WHERE u.mail = :email LIMIT 1", nativeQuery = true)
   fun findByEmailIgnoreCase(@Param("email") email: String): Utente

}
