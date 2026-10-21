package com.runly.auth.domain

interface PatternValidator {
    fun matches(value: String): Boolean
}