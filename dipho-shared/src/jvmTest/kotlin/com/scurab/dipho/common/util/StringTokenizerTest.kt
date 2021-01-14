package com.scurab.dipho.common.util

import org.junit.Test
import kotlin.test.assertEquals

class StringTokenizerTest {
    @Test
    fun testTokenizer1() {
        val stringTokenizer = StringTokenizer("A\nBB\nAA\nC\n", listOf("\n", "A", "BB", "C"))
        assertEquals(
            stringTokenizer.tokens().map { it.text },
            listOf("A", "\n", "BB", "\n", "A", "A", "\n", "C", "\n")
        )
    }

    @Test
    fun testTokenizer2() {
        assertEquals(
            StringTokenizer("test", listOf("\n")).tokens(),
            listOf(StringTokenizer.Token.Text(0, "test"))
        )

        assertEquals(
            StringTokenizer("test", listOf("test")).tokens(),
            listOf(StringTokenizer.Token.Item(0, "test"))
        )
    }
}