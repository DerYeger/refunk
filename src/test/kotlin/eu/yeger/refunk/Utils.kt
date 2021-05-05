package eu.yeger.refunk

import org.junit.jupiter.api.Assertions.assertEquals

infix fun ULong.shouldBe(other: Long) = assertEquals(this, other.toULong())
