package com.example.myfitness.controller

import com.example.myfitness.model.FileImage
import com.example.myfitness.model.Utente
import com.example.myfitness.repository.FileRepository
import com.example.myfitness.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI
import javax.validation.Valid
import org.springframework.mail.SimpleMailMessage
import com.example.myfitness.model.ConfirmationToken
import com.example.myfitness.repository.ConfirmationTokenRepository
import com.example.myfitness.service.EmailSenderService
import com.example.myfitness.service.UserAuthenticationService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.RequestMapping
import java.util.*





/** */
@RestController
@RequestMapping("/api/user")
class UserController {

    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var fileRepository: FileRepository

    @Autowired
    lateinit var confirmationTokenRepository: ConfirmationTokenRepository

    @Autowired
    lateinit var userAuthenticationService: UserAuthenticationService

    @Autowired
    lateinit var emailService: EmailSenderService


    @GetMapping("/all")
    fun getAll(): List<Utente> = userService.findAllUsers()

    @PostMapping("/insert")
    fun createUser(@Valid @RequestBody user: Utente): ResponseEntity<String> {

        var usr = userAuthenticationService.setPassword(user.password, user);

        userService.insertUser(usr)

        var token = ConfirmationToken(null, UUID.randomUUID().toString(), Date(), user)

        confirmationTokenRepository.save(token)

        val mailMessage = SimpleMailMessage()
        mailMessage.setTo(user.mail)
        mailMessage.setSubject("REGISTRAZIONE COMPLETATA!")
        mailMessage.setText("Per confermare, clicca sul link : "
                + "https://myfitnessserver.herokuapp.com/api/user/confirm-account?token=" + token.confirmationToken)

        emailService.sendEmail(mailMessage)

        return ResponseEntity.ok("Inserito l'utente ${user.usernameId}")
    }

    /** Viene gestita la chiamata get che permette di cercare la presenza di un membro all'interno del db*/
    @GetMapping("/exists/{usernameId}/")
    fun getMemberById(@PathVariable(value = "usernameId") usernameId: String): ResponseEntity<Utente> {
        return with(userService) {
            findUserById(usernameId).map { utente ->
                ResponseEntity.ok(utente)
            }.orElse(ResponseEntity.notFound().build())
        }
    }

    @PutMapping("/update/{usernameId}/")
    fun updateUserById(@PathVariable(value = "usernameId") usernameId: String, @Valid @RequestBody newUser: Utente): ResponseEntity<Utente> {
        return userService.findUserById(usernameId).map { existingUser ->
            val updatedUser: Utente = existingUser
                    .copy(mail = newUser.mail, password = newUser.password, flagAllenatore = newUser.flagAllenatore,
                            nome = newUser.nome, cognome = newUser.cognome, eta = newUser.eta, descrizione = newUser.descrizione,
                            imageURI = newUser.imageURI)
            ResponseEntity.ok().body(userService.insertUser(updatedUser))
        }.orElse(ResponseEntity.notFound().build())
    }

    @DeleteMapping("/delete/{usernameId}/")
    fun deleteMemberById(@PathVariable(value = "usernameId") usernameId: String): ResponseEntity<String> {
        return userService.findUserById(usernameId).map { user ->
            userService.deleteUser(user)
            ResponseEntity.ok("Cancellato l'utente $usernameId")
        }.orElse(ResponseEntity.notFound().build())
    }

    @DeleteMapping("/delete/all")
    fun deleteAllUser(): ResponseEntity<String> {
        userService.deleteAllUsers()
        return ResponseEntity.ok("Cancellati tutti gli utenti")
    }

    @GetMapping("/download/image/{filename}/")
    fun downloadFile(@PathVariable filename: String): ResponseEntity<ByteArray> {
        val fileImage = fileRepository.findById(filename.split(".")[0]).get()
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"$filename\"")
                .body(fileImage.image);
    }

    @PostMapping("/save/image")
    fun uploadMultipartFile(@RequestPart("file") file: MultipartFile): ResponseEntity<URI> {
        var fileImage = FileImage(file.resource.filename!!.split(".")[0], file.bytes)
        fileRepository.save(fileImage)
        return ResponseEntity.ok(URI(ServletUriComponentsBuilder.fromCurrentContextPath().toUriString() + "/api/user/download/image/" + file.resource.filename + "/"))
    }

    @RequestMapping("/confirm-account")
    fun confirmUserAccount(@RequestParam("token") confirmationToken: String): ResponseEntity<String> {
        var token = confirmationTokenRepository.findByConfirmationToken(confirmationToken)

        return if(token != null) {
            var user = userService.findUserById(token.user.usernameId).get();
            user.isEnabled = true
            userService.insertUser(user);

            ResponseEntity.ok("<h1> Benvenuto in MyFitness <h1> " +
                    "<p> La registrazione è andata a buon fine, utilizza subito l'app! <p>")
        } else {
            ResponseEntity.ok("Servizio non disponibile, contattare l'amministratore!")
        }
    }

    @GetMapping("/login/{usernameId}/{password}/")
    fun login(@PathVariable usernameId: String, @PathVariable password: String): ResponseEntity<String> {
        var token = userAuthenticationService.login(usernameId, password)

        return if(token != null)
            ResponseEntity.ok(token)
        else
            ResponseEntity.badRequest().body("token: " + token);
    }

}