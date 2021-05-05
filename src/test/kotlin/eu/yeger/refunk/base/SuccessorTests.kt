package eu.yeger.refunk.base

import eu.yeger.refunk.shouldBe
import org.junit.jupiter.api.Test

class SuccessorTests {

    @Test
    fun testSuccessor() {
        Successor(0) shouldBe 1
        Successor(41) shouldBe 42
        Successor(41, 10, 20) shouldBe 42
    }
}
