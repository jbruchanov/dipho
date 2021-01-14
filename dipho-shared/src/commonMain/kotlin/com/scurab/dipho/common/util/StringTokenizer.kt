package com.scurab.dipho.common.util

class StringTokenizer(private val text: String, list: List<String>) : Iterable<StringTokenizer.Token> {

    val tokensInfo: List<Token.Item>

    init {
        val indexes = mutableListOf<Token.Item>()
        list.forEach { toSearch ->
            text.indexesOf(toSearch).forEach {
                indexes.add(Token.Item(it, toSearch))
            }
        }
        tokensInfo = indexes.sortedBy { it.startIndex }
    }

    fun tokens(): List<Token> {
        val result = mutableListOf<Token>()
        val iterator = iterator()
        while (iterator.hasNext()) {
            result.add(iterator.next())
        }
        return result
    }

    private fun String.indexesOf(value: String, ignoreCase: Boolean = false): List<Int> {
        val result = mutableListOf<Int>()
        var startIndex = 0
        while (true) {
            val newIndex = indexOf(value, startIndex, ignoreCase)
            if (newIndex >= 0) {
                result.add(newIndex)
                startIndex = newIndex + value.length
            } else {
                break
            }
        }
        return result
    }

    interface Token {
        val startIndex: Int
        val text: String

        data class Text(override val startIndex: Int, override val text: String) : Token
        data class Item(override val startIndex: Int, override val text: String) : Token
    }

    override fun iterator(): Iterator<Token> = StringTokenizerIterator(text, tokensInfo)

    class StringTokenizerIterator(val text: String, val tokens: List<Token.Item>) : Iterator<Token> {
        private var tokenIndex: Int = 0
        private var textIndex: Int = 0

        override fun hasNext(): Boolean = tokenIndex < tokens.size || textIndex < text.length

        override fun next(): Token {
            if (tokens.isEmpty()) {
                //to finish
                textIndex = Int.MAX_VALUE
                tokenIndex = Int.MAX_VALUE
                return Token.Text(0, text)
            }
            val token = tokens.getOrNull(tokenIndex)
            return if (textIndex == token?.startIndex) {
                tokenIndex++
                textIndex = token.startIndex + token.text.length
                token
            } else {
                val end = tokens.getOrNull(tokenIndex)?.startIndex ?: text.length
                val subStr = text.substring(textIndex, end)
                textIndex += subStr.length
                Token.Text(textIndex, subStr)
            }
        }
    }
}