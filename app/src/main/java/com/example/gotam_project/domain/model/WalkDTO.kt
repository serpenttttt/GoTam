package com.example.gotam_project.domain.model

data class WalkDTO(
    val id: Int = 0,
    val date: Long,
    val duration: Long,
    val steps: Int,
    val path: String
)