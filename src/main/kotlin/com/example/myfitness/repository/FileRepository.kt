package com.example.myfitness.repository

import com.example.myfitness.model.FileImage
import org.springframework.data.jpa.repository.JpaRepository


interface FileRepository : JpaRepository<FileImage, String> {

}