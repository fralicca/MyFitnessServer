package com.example.myfitness

import com.example.myfitness.model.Utente
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.slf4j.LoggerFactory
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.*
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import java.net.URI
import java.util.*


@RunWith(SpringRunner::class)
@SpringBootTest(classes = [MyfitnessApplication::class])
class MyfitnessApplicationTests {

    var logger = LoggerFactory.getLogger(MyfitnessApplicationTests::class.java)

//    // Per testare il server in locale
//    val baseUrl = "https://myfitnessserver.herokuapp.com/api/"

    //Per testare il server in remoto
    val baseUrl = "https://myfitnessserver.herokuapp.com/api/"

    val restTemplate = RestTemplate()
    val verbose = true; // Flag per logger


    @Test
    fun apiUserInsert() {
        val uri = URI(baseUrl + "user/insert/")
        val user = Utente("Test", "francesco.licca@hotmail.it", "test", false, "test",
                "test", 24, "", "", 100.0, 175, 'M', "", null)

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON

        val request = HttpEntity(user, headers)

        try {
            val result = restTemplate.postForEntity(uri, request, String::class.java)

            assertNotNull(result)
            assertEquals(result?.statusCode, HttpStatus.OK)
            assertEquals(result?.body, "Inserito l'utente ${user.usernameId}")

        } catch (ex: HttpClientErrorException) {
            Assert.assertEquals(500 or 501, ex.rawStatusCode)
        }
    }

    @Test
    fun apiUserExists() {
        val uri = URI(baseUrl + "user/exists/francesco/")

        try {

            val headers = HttpHeaders()
            headers.accept = Collections.singletonList(MediaType.APPLICATION_JSON)
            headers.set(HttpHeaders.AUTHORIZATION, "Bearer b7d5a347-3248-4cfb-b1b2-a35930fb0612"); // Token precedentemente inserito nel db

            val entity = HttpEntity(null, headers)

            var response = restTemplate.exchange(uri, HttpMethod.GET, entity, Utente::class.java)

            assertNotNull(response)
            assertEquals(HttpStatus.OK, response.statusCode)

            if(verbose)
                logger.info("Response apiUserExists: $response")

        } catch (ex: HttpClientErrorException) {
            Assert.assertEquals(500 or 501, ex.rawStatusCode)
        }

    }

    @Test
    fun apiUserUpdate() {
        val uri = URI(baseUrl + "user/update/Test/")

        try {

            val headers = HttpHeaders()
            headers.accept = Collections.singletonList(MediaType.APPLICATION_JSON)
            headers.set(HttpHeaders.AUTHORIZATION, "Bearer b7d5a347-3248-4cfb-b1b2-a35930fb0612"); // Token precedentemente inserito nel db
            val user = Utente("Test", "francesco.licca@hotmail.it", "test", false, "Update",
                    "testUpdate", 25, "", "", 120.0, 176, 'M', "", null)

            val entity = HttpEntity(user, headers)

            var response = restTemplate.exchange(uri, HttpMethod.PUT, entity, Utente::class.java)

            assertNotNull(response)
            assertEquals(HttpStatus.OK, response.statusCode)

            if(verbose)
                logger.info("Response apiUserUpdate: $response")

        } catch (ex: HttpClientErrorException) {
            Assert.assertEquals(500 or 501 or 405, ex.rawStatusCode)
        }

    }



}

