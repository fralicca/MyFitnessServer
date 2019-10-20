package com.example.myfitness.model

import javax.persistence.*

@Entity
class FileImage (
        @Id
        var usernameId: String,
        @Lob
        @Basic(fetch = FetchType.LAZY)
        @Column(length=1000000)
        var image: ByteArray
)
{


}