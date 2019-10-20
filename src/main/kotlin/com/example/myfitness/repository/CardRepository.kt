package com.example.myfitness.repository

import com.example.myfitness.model.Scheda
import com.example.myfitness.model.Utente
import org.springframework.core.io.Resource;
import org.springframework.data.annotation.QueryAnnotation
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.web.multipart.MultipartFile;
import java.net.URL
import java.nio.file.Path
import java.util.stream.Stream

@Repository
interface CardRepository : JpaRepository<Scheda, Int> {

    @Modifying
    @Query("DELETE FROM Scheda s WHERE s.possessore = :username")
    fun deleteAllUserSchede(@Param("username")username: String)

    @Modifying
    @Query("UPDATE Scheda s SET s.is_current = 1 WHERE s.possessore = :username AND s.schedaId = :idScheda")
    fun setAsCurrentCard(@Param("username") username: String, @Param("idScheda") idScheda: Int)

    @Modifying
    @Query("UPDATE Scheda s SET s.is_current = 0 WHERE s.possessore = :username")
    fun removeCurrentCard(@Param("username") username: String)

    @Query("SELECT * FROM Scheda s WHERE s.possessore = :username", nativeQuery = true)
    fun getUserCards(@Param("username") username: String): List<Scheda>?

    @Query("SELECT * FROM Scheda s WHERE s.is_current = 1 AND s.possessore = :username LIMIT 1", nativeQuery = true)
    fun getCurrentCard(@Param("username") username: String): Scheda?

    @Query("SELECT * FROM Scheda s WHERE s.autore = :username AND possessore != :username", nativeQuery = true)
    fun getCompletedRequest(@Param("username") username: String):  List<Scheda>?

}
