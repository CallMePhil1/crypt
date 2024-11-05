package com.github.callmephil1.crypt.data

import android.content.Context
import kotlin.math.pow
import kotlin.random.Random

class PasswordGenerator(
    private val context: Context
) {
    fun generate(length: Int, maximumDigitCount: Int = 4): String {
        val adjectivesStream = context.assets.open("english-adjectives.txt")
        val adjectives = adjectivesStream.reader().readLines().sortedBy { it.length }
        val smallestAdjectiveLength = adjectives.first().length

        val maximumNounLength = length - smallestAdjectiveLength - 2
        val nounsStream = context.assets.open("english-nouns.txt")
        val nouns = nounsStream.reader().readLines().filter { it.length <= maximumNounLength }

        val noun = nouns.random().trim().replaceFirstChar { it.titlecase() }
        val maxAdjLength = length - noun.length - 2
        val minAdjLength = maxAdjLength - maximumDigitCount

        var adjective: String

        while(true) {
            adjective = adjectives.random().trim().replaceFirstChar { it.titlecase() }

            if (adjective.length in minAdjLength .. maxAdjLength)
                break
        }

        val digitCount = length - (noun.length + adjective.length + 2)
        val endDigit = if (digitCount == 0) "" else Random.nextInt(
            10.0.pow((digitCount).toDouble()).toInt()
        ).toString().padStart(digitCount, '0')

        val plainPassword = listOf(adjective, noun, endDigit).joinToString(SEPARATORS.random().toString())
        val randomReplacement = REPLACEMENTS.keys.filter { plainPassword.contains(it) }.random()

        return plainPassword
            .replace(randomReplacement, REPLACEMENTS[randomReplacement]!!)

    }

    companion object {
        private const val SEPARATORS = "-_=+.^*"
        private val REPLACEMENTS = mapOf(
            'a' to '4',
            'b' to '6',
            'e' to '3',
            'g' to '9',
            'l' to '!',
            'o' to '0',
            's' to '$',
            't' to '+'
        )
    }
}