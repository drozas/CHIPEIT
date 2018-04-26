package es.chipeit.lib.core

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import org.mockito.invocation.InvocationOnMock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.stubbing.Answer

import es.chipeit.lib.interfaces.IMemory
import es.chipeit.lib.interfaces.IRegisters
import es.chipeit.lib.interfaces.ITimer
import kotlin.test.assertNotEquals
import es.chipeit.lib.core.WrappedGraphicMemoryTests.Companion.checkCleanMemory

class InstructionSetTests {
    @Test
    fun clsTest() {
        val graphicMemory = Mockito.mock(IMemory::class.java) as IMemory<Byte>
        val registers = Registers(ByteMemory(ByteArray(16)))

        registers.pc = 0x200

        cls(registers, graphicMemory)

        assertEquals(0x200 + 2, registers.pc)
        Mockito.verify(
                graphicMemory,
                times(1)
        ).fill(0)
    }

    @Test
    fun jpAddrTest() {
        val registersMock = Mockito.mock(IRegisters::class.java)

        jpAddr(0x1225, registersMock)
        jpAddr(0x1512, registersMock)
        jpAddr(0x1FFF, registersMock)

        Mockito.verify(
                registersMock,
                times(1)
        ).pc = 0x0225

        Mockito.verify(
                registersMock,
                times(1)
        ).pc = 0x0512

        Mockito.verify(
                registersMock,
                times(1)
        ).pc = 0x0FFF
    }

    @Test
    fun ldVxByteTest() {
        val registersMemoryMock = Mockito.mock(IMemory::class.java) as IMemory<Byte>
        Mockito.`when`(registersMemoryMock.size).thenReturn(16)

        val registersMock = Registers(registersMemoryMock)
        registersMock.pc = 0x200

        ldVxByte(0x6011, registersMock)
        assertEquals(0x0200 + 2, registersMock.pc)

        ldVxByte(0x6122, registersMock)
        assertEquals(0x0200 + 4, registersMock.pc)

        ldVxByte(0x6233, registersMock)
        assertEquals(0x0200 + 6, registersMock.pc)

        ldVxByte(0x6344, registersMock)
        assertEquals(0x0200 + 8, registersMock.pc)

        ldVxByte(0x6455, registersMock)
        assertEquals(0x0200 + 10, registersMock.pc)

        ldVxByte(0x6566, registersMock)
        assertEquals(0x0200 + 12, registersMock.pc)

        ldVxByte(0x6677, registersMock)
        assertEquals(0x0200 + 14, registersMock.pc)

        ldVxByte(0x6788, registersMock)
        assertEquals(0x0200 + 16, registersMock.pc)

        ldVxByte(0x6899, registersMock)
        assertEquals(0x0200 + 18, registersMock.pc)

        ldVxByte(0x69AA, registersMock)
        assertEquals(0x0200 + 20, registersMock.pc)

        ldVxByte(0x6ABB, registersMock)
        assertEquals(0x0200 + 22, registersMock.pc)

        ldVxByte(0x6BCC, registersMock)
        assertEquals(0x0200 + 24, registersMock.pc)

        ldVxByte(0x6CDD, registersMock)
        assertEquals(0x0200 + 26, registersMock.pc)

        ldVxByte(0x6DEE, registersMock)
        assertEquals(0x0200 + 28, registersMock.pc)

        ldVxByte(0x6EFF, registersMock)
        assertEquals(0x0200 + 30, registersMock.pc)

        ldVxByte(0x6F00, registersMock)
        assertEquals(0x0200 + 32, registersMock.pc)

        Mockito.verify(
                registersMemoryMock,
                times(1)
        )[0] = 0x11

        Mockito.verify(
                registersMemoryMock,
                times(1)
        )[1] = 0x22

        Mockito.verify(
                registersMemoryMock,
                times(1)
        )[2] = 0x33

        Mockito.verify(
                registersMemoryMock,
                times(1)
        )[3] = 0x44

        Mockito.verify(
                registersMemoryMock,
                times(1)
        )[4] = 0x55

        Mockito.verify(
                registersMemoryMock,
                times(1)
        )[5] = 0x66

        Mockito.verify(
                registersMemoryMock,
                times(1)
        )[6] = 0x77

        Mockito.verify(
                registersMemoryMock,
                times(1)
        )[7] = 0x88.toByte()

        Mockito.verify(
                registersMemoryMock,
                times(1)
        )[8] = 0x99.toByte()

        Mockito.verify(
                registersMemoryMock,
                times(1)
        )[9] = 0xAA.toByte()

        Mockito.verify(
                registersMemoryMock,
                times(1)
        )[10] = 0xBB.toByte()

        Mockito.verify(
                registersMemoryMock,
                times(1)
        )[11] = 0xCC.toByte()

        Mockito.verify(
                registersMemoryMock,
                times(1)
        )[12] = 0xDD.toByte()

        Mockito.verify(
                registersMemoryMock,
                times(1)
        )[13] = 0xEE.toByte()

        Mockito.verify(
                registersMemoryMock,
                times(1)
        )[14] = 0xFF.toByte()

        Mockito.verify(
                registersMemoryMock,
                times(1)
        )[15] = 0x00
    }

    @Test
    fun addVxVyTest() {
        val vMock = Mockito.mock(IMemory::class.java) as IMemory<Byte>

        Mockito.`when`(vMock.size).thenReturn(16)

        Mockito.`when`(vMock[0x0]).thenReturn(0)
        Mockito.`when`(vMock[0x1]).thenReturn(127)

        Mockito.`when`(vMock[0x2]).thenReturn(32)

        Mockito.`when`(vMock[0x3]).thenReturn(1)
        Mockito.`when`(vMock[0x4]).thenReturn(127)

        Mockito.`when`(vMock[0x5]).thenReturn(0)
        Mockito.`when`(vMock[0x6]).thenReturn(128.toByte())

        Mockito.`when`(vMock[0x7]).thenReturn(255.toByte())
        Mockito.`when`(vMock[0x8]).thenReturn(255.toByte())

        Mockito.`when`(vMock[0x9]).thenReturn(255.toByte())
        Mockito.`when`(vMock[0xA]).thenReturn(1)

        val registers = Registers(vMock)
        registers.pc = 0x200

        addVxVy(0x8014, registers)

        Mockito.verify(
                vMock,
                times(1)
        )[0x0] = 127

        Mockito.verify(
                vMock,
                times(1)
        )[0x0]

        Mockito.verify(
                vMock,
                times(1)
        )[0x1]

        Mockito.verify(
                vMock,
                times(1)
        )[0xF] = 0

        addVxVy(0x8104, registers)

        Mockito.verify(
                vMock,
                times(1)
        )[0x1] = 127

        Mockito.verify(
                vMock,
                times(2)
        )[0x0]

        Mockito.verify(
                vMock,
                times(2)
        )[0x1]

        Mockito.verify(
                vMock,
                times(2)
        )[0xF] = 0

        addVxVy(0x8224, registers)

        Mockito.verify(
                vMock,
                times(1)
        )[0x2] = 64

        Mockito.verify(
                vMock,
                times(2)
        )[0x2]

        Mockito.verify(
                vMock,
                times(3)
        )[0xF] = 0

        addVxVy(0x8344, registers)

        Mockito.verify(
                vMock,
                times(1)
        )[0x3] = 128.toByte()

        Mockito.verify(
                vMock,
                times(1)
        )[0x3]

        Mockito.verify(
                vMock,
                times(1)
        )[0x4]

        Mockito.verify(
                vMock,
                times(4)
        )[0xF] = 0

        addVxVy(0x8564, registers)

        Mockito.verify(
                vMock,
                times(1)
        )[0x5] = 128.toByte()

        Mockito.verify(
                vMock,
                times(1)
        )[0x5]

        Mockito.verify(
                vMock,
                times(1)
        )[0x6]

        Mockito.verify(
                vMock,
                times(5)
        )[0xF] = 0

        addVxVy(0x8784, registers)

        Mockito.verify(
                vMock,
                times(1)
        )[0x7] = 254.toByte()

        Mockito.verify(
                vMock,
                times(1)
        )[0x7]

        Mockito.verify(
                vMock,
                times(1)
        )[0x8]

        Mockito.verify(
                vMock,
                times(1)
        )[0xF] = 1

        addVxVy(0x89A4, registers)

        Mockito.verify(
                vMock,
                times(1)
        )[0x9] = 0

        Mockito.verify(
                vMock,
                times(1)
        )[0x9]

        Mockito.verify(
                vMock,
                times(1)
        )[0xA]

        Mockito.verify(
                vMock,
                times(2)
        )[0xF] = 1

        assertEquals(0x200 + 7 * 0x2, registers.pc)
    }

    @Test
    fun subVxVyTest() {
        val vMock = Mockito.mock(IMemory::class.java) as IMemory<Byte>

        Mockito.`when`(vMock.size).thenReturn(16)

        Mockito.`when`(vMock[0x0]).thenReturn(32)
        Mockito.`when`(vMock[0x1]).thenReturn(16)

        Mockito.`when`(vMock[0x2]).thenReturn(32)
        Mockito.`when`(vMock[0x3]).thenReturn(33)

        Mockito.`when`(vMock[0x4]).thenReturn(32)
        Mockito.`when`(vMock[0x5]).thenReturn(32)

        Mockito.`when`(vMock[0x6]).thenReturn(32)

        Mockito.`when`(vMock[0x7]).thenReturn(128.toByte())
        Mockito.`when`(vMock[0x8]).thenReturn(255.toByte())
        Mockito.`when`(vMock[0x9]).thenReturn(0)
        Mockito.`when`(vMock[0xA]).thenReturn(1)

        val registers = Registers(vMock)
        registers.pc = 0x200

        subVxVy(0x8015, registers)

        Mockito.verify(
                vMock,
                times(1)
        )[0x0] = 16

        Mockito.verify(
                vMock,
                times(1)
        )[0x0]

        Mockito.verify(
                vMock,
                times(1)
        )[0x1]

        Mockito.verify(
                vMock,
                times(1)
        )[0xF] = 1

        subVxVy(0x8235, registers)

        Mockito.verify(
                vMock,
                times(1)
        )[0x2] = 255.toByte()

        Mockito.verify(
                vMock,
                times(1)
        )[0x2]

        Mockito.verify(
                vMock,
                times(1)
        )[0x3]

        Mockito.verify(
                vMock,
                times(1)
        )[0xF] = 0

        subVxVy(0x8455, registers)

        Mockito.verify(
                vMock,
                times(1)
        )[0x4] = 0

        Mockito.verify(
                vMock,
                times(1)
        )[0x4]

        Mockito.verify(
                vMock,
                times(1)
        )[0x5]

        Mockito.verify(
                vMock,
                times(2)
        )[0xF] = 0

        subVxVy(0x8665, registers)

        Mockito.verify(
                vMock,
                times(1)
        )[0x6] = 0

        Mockito.verify(
                vMock,
                times(2)
        )[0x6]

        Mockito.verify(
                vMock,
                times(3)
        )[0xF] = 0

        subVxVy(0x8795, registers)

        Mockito.verify(
                vMock,
                times(1)
        )[0x7] = 128.toByte()

        Mockito.verify(
                vMock,
                times(2)
        )[0xF] = 1

        subVxVy(0x8975, registers)

        Mockito.verify(
                vMock,
                times(1)
        )[0x9] = 128.toByte()

        Mockito.verify(
                vMock,
                times(4)
        )[0xF] = 0

        subVxVy(0x87A5, registers)

        Mockito.verify(
                vMock,
                times(1)
        )[0x7] = 127

        Mockito.verify(
                vMock,
                times(3)
        )[0xF] = 1

        subVxVy(0x8A75, registers)

        Mockito.verify(
                vMock,
                times(1)
        )[0xA] = 129.toByte()

        Mockito.verify(
                vMock,
                times(5)
        )[0xF] = 0

        subVxVy(0x8895, registers)

        Mockito.verify(
                vMock,
                times(1)
        )[0x8] = 255.toByte()

        Mockito.verify(
                vMock,
                times(4)
        )[0xF] = 1

        subVxVy(0x8985, registers)

        Mockito.verify(
                vMock,
                times(1)
        )[0x9] = 1

        Mockito.verify(
                vMock,
                times(6)
        )[0xF] = 0

        subVxVy(0x88A5, registers)

        Mockito.verify(
                vMock,
                times(1)
        )[0x8] = 254.toByte()

        Mockito.verify(
                vMock,
                times(5)
        )[0xF] = 1

        subVxVy(0x8A85, registers)

        Mockito.verify(
                vMock,
                times(1)
        )[0xA] = 2

        Mockito.verify(
                vMock,
                times(7)
        )[0xF] = 0

        Mockito.verify(
                vMock,
                times(4)
        )[0x7]

        Mockito.verify(
                vMock,
                times(4)
        )[0x8]

        Mockito.verify(
                vMock,
                times(4)
        )[0x9]

        Mockito.verify(
                vMock,
                times(4)
        )[0xA]

        assertEquals(0x200 + 12 * 0x2, registers.pc)
    }

    @Test
    fun subnVxVyTest() {
        val vMock = Mockito.mock(IMemory::class.java) as IMemory<Byte>

        Mockito.`when`(vMock.size).thenReturn(16)

        Mockito.`when`(vMock[0x0]).thenReturn(16)
        Mockito.`when`(vMock[0x1]).thenReturn(32)

        Mockito.`when`(vMock[0x2]).thenReturn(33)
        Mockito.`when`(vMock[0x3]).thenReturn(32)

        Mockito.`when`(vMock[0x4]).thenReturn(32)
        Mockito.`when`(vMock[0x5]).thenReturn(32)

        Mockito.`when`(vMock[0x6]).thenReturn(32)

        Mockito.`when`(vMock[0x7]).thenReturn(128.toByte())
        Mockito.`when`(vMock[0x8]).thenReturn(255.toByte())
        Mockito.`when`(vMock[0x9]).thenReturn(0)
        Mockito.`when`(vMock[0xA]).thenReturn(1)

        val registers = Registers(vMock)
        registers.pc = 0x200

        subnVxVy(0x8017, registers)

        Mockito.verify(
                vMock,
                times(1)
        )[0x0] = 16

        Mockito.verify(
                vMock,
                times(1)
        )[0x0]

        Mockito.verify(
                vMock,
                times(1)
        )[0x1]

        Mockito.verify(
                vMock,
                times(1)
        )[0xF] = 1

        subnVxVy(0x8237, registers)

        Mockito.verify(
                vMock,
                times(1)
        )[0x2] = 255.toByte()

        Mockito.verify(
                vMock,
                times(1)
        )[0x2]

        Mockito.verify(
                vMock,
                times(1)
        )[0x3]

        Mockito.verify(
                vMock,
                times(1)
        )[0xF] = 0

        subnVxVy(0x8457, registers)

        Mockito.verify(
                vMock,
                times(1)
        )[0x4] = 0

        Mockito.verify(
                vMock,
                times(1)
        )[0x4]

        Mockito.verify(
                vMock,
                times(1)
        )[0x5]

        Mockito.verify(
                vMock,
                times(2)
        )[0xF] = 0

        subnVxVy(0x8667, registers)

        Mockito.verify(
                vMock,
                times(1)
        )[0x6] = 0

        Mockito.verify(
                vMock,
                times(2)
        )[0x6]

        Mockito.verify(
                vMock,
                times(3)
        )[0xF] = 0

        subnVxVy(0x8797, registers)

        Mockito.verify(
                vMock,
                times(1)
        )[0x7] = 128.toByte()

        Mockito.verify(
                vMock,
                times(4)
        )[0xF] = 0

        subnVxVy(0x8977, registers)

        Mockito.verify(
                vMock,
                times(1)
        )[0x9] = 128.toByte()

        Mockito.verify(
                vMock,
                times(2)
        )[0xF] = 1

        subnVxVy(0x87A7, registers)

        Mockito.verify(
                vMock,
                times(1)
        )[0x7] = 129.toByte()

        Mockito.verify(
                vMock,
                times(5)
        )[0xF] = 0

        subnVxVy(0x8A77, registers)

        Mockito.verify(
                vMock,
                times(1)
        )[0xA] = 127

        Mockito.verify(
                vMock,
                times(3)
        )[0xF] = 1

        subnVxVy(0x8897, registers)

        Mockito.verify(
                vMock,
                times(1)
        )[0x8] = 1

        Mockito.verify(
                vMock,
                times(6)
        )[0xF] = 0

        subnVxVy(0x8987, registers)

        Mockito.verify(
                vMock,
                times(1)
        )[0x9] = 255.toByte()

        Mockito.verify(
                vMock,
                times(4)
        )[0xF] = 1

        subnVxVy(0x88A7, registers)

        Mockito.verify(
                vMock,
                times(1)
        )[0x8] = 2

        Mockito.verify(
                vMock,
                times(7)
        )[0xF] = 0

        subnVxVy(0x8A87, registers)

        Mockito.verify(
                vMock,
                times(1)
        )[0xA] = 254.toByte()

        Mockito.verify(
                vMock,
                times(5)
        )[0xF] = 1

        Mockito.verify(
                vMock,
                times(4)
        )[0x7]

        Mockito.verify(
                vMock,
                times(4)
        )[0x8]

        Mockito.verify(
                vMock,
                times(4)
        )[0x9]

        Mockito.verify(
                vMock,
                times(4)
        )[0xA]

        assertEquals(0x200 + 12 * 0x2, registers.pc)
    }

    @Test
    fun sneVxVyTest() {
        val vMock = Mockito.mock(IMemory::class.java) as IMemory<Byte>

        Mockito.`when`(vMock.size).thenReturn(16)

        Mockito.`when`(vMock[0x0]).thenReturn(0)
        Mockito.`when`(vMock[0x1]).thenReturn(0)

        Mockito.`when`(vMock[0x2]).thenReturn(32)
        Mockito.`when`(vMock[0x3]).thenReturn(32)

        Mockito.`when`(vMock[0x4]).thenReturn(64)
        Mockito.`when`(vMock[0x5]).thenReturn(32)

        Mockito.`when`(vMock[0x6]).thenReturn(128.toByte())
        Mockito.`when`(vMock[0x7]).thenReturn(128.toByte())

        Mockito.`when`(vMock[0x8]).thenReturn(128.toByte())
        Mockito.`when`(vMock[0x9]).thenReturn(129.toByte())

        Mockito.`when`(vMock[0xA]).thenReturn(2)
        Mockito.`when`(vMock[0xB]).thenReturn(128.toByte())

        val registers = Registers(vMock)
        registers.pc = 0x200

        sneVxVy(0x9010, registers)
        assertEquals(0x200 + 1 * 0x2, registers.pc)

        sneVxVy(0x9230, registers)
        assertEquals(0x200 + 2 * 0x2, registers.pc)

        sneVxVy(0x9450, registers)
        assertEquals(0x200 + 4 * 0x2, registers.pc)

        sneVxVy(0x9670, registers)
        assertEquals(0x200 + 5 * 0x2, registers.pc)

        sneVxVy(0x9890, registers)
        assertEquals(0x200 + 7 * 0x2, registers.pc)

        sneVxVy(0x9AB0, registers)
        assertEquals(0x200 + 9 * 0x2, registers.pc)
    }

    @Test
    fun drwVxVyByteIllegalMemoryTest() {
        val registersMemoryMock = Mockito.mock(IMemory::class.java) as IMemory<Byte>
        Mockito.`when`(registersMemoryMock.size).thenReturn(16)
        Mockito.`when`(registersMemoryMock[0]).thenReturn(0x00) // I don't care the value.

        val registers = Registers(registersMemoryMock)
        val memory = ByteMemory(ByteArray(0x1000))

        /*
            I don't care about the graphic memory size
            as long it can contain the tallest of the sprites.

            Sprites may be up to 15 bytes, and the width is fixed to 8 bits.
        */
        val graphicMemory = WrappedGraphicMemory(1, ByteMemory(ByteArray(15)));

        /*
            The hex font table starts at 0x000.
            Every character sprite has 5 bytes.

            Between the table and 0x200 there is a gap of illegal addresses.
            This limits begins 15 bytes ahead.
        */
        registers.i = 17 * 5 - 15;

        for (offset in 0..15) {
            for (size in 0..15 - offset) // assertNotFails
                drwVxVyByte(0xD000 or size, registers, memory, graphicMemory);

            for (size in 15 - offset + 1..15) {
                assertNotEquals(0, size) // With size equals zero is a no-op!
                assertFailsWith<IllegalStateException> {
                    drwVxVyByte(0xD000 or size, registers, memory, graphicMemory);
                }
            }

            registers.i++
        }

        registers.i++
        for (skips in registers.i..0x200 - 15) {
            // assertNotFails
            // With size equals zero is a no-op!
            drwVxVyByte(0xD000 or 0, registers, memory, graphicMemory);

            for (size in 1..15) {
                assertFailsWith<IllegalStateException> {
                    drwVxVyByte(0xD000 or size, registers, memory, graphicMemory);
                }
            }

            registers.i++
        }

        for (offset in 0..15) {
            // assertNotFails
            // With size equals zero is a no-op!
            drwVxVyByte(0xD000 or 0, registers, memory, graphicMemory);

            for (size in 1..15 - offset) {
                assertNotEquals(15, offset) // FIXME: Remove after use.
                assertFailsWith<IllegalStateException> {
                    drwVxVyByte(0xD000 or size, registers, memory, graphicMemory);
                }
            }

            for (size in 15 - offset + 1..15) // assertNotFails
                drwVxVyByte(0xD000 or size, registers, memory, graphicMemory);

            registers.i++
        }
    }

    /*
        It's necessary the graphic memory size be enough to contain the tallest of the sprites.

        The width is fixed to 8 bits,
        and two bytes for checking wrapping would be enough to test.

        However it's probable that the stupid tired programmer duplicated the implementation
        (aligned to byte vs not aligned).

        Measures must be taken to check both implementations.
    */
    @Test
    fun drwVxVyTallestSpriteTest() {
        val registersMemoryMock = Mockito.mock(IMemory::class.java) as IMemory<Byte>
        Mockito.`when`(registersMemoryMock.size).thenReturn(16)
        Mockito.`when`(registersMemoryMock[0]).thenReturn(0)
        Mockito.`when`(registersMemoryMock[1]).thenReturn(1)
        Mockito.`when`(registersMemoryMock[2]).thenReturn(2)
        Mockito.`when`(registersMemoryMock[3]).thenReturn(3)
        Mockito.`when`(registersMemoryMock[4]).thenReturn(10)

        val registers = Registers(registersMemoryMock)
        val memory = ByteMemory(ByteArray(2))
        val graphicMemory = WrappedGraphicMemory(1, ByteMemory(ByteArray(2)));

        /*
            1111 0000b
            0000 1111b
        */
        memory[0] = 0xF0.toByte()
        memory[1] = 0x0F

        checkCleanMemory(graphicMemory)

        drwVxVyByte(0xD002, registers, memory, graphicMemory)
        assertEquals(0xF0.toByte(), graphicMemory[0, 0])
        assertEquals(0x0F, graphicMemory[0, 1])

        graphicMemory.fill(0)
        checkCleanMemory(graphicMemory)

        drwVxVyByte(0xD022, registers, memory, graphicMemory)
        assertEquals(0xF0.toByte(), graphicMemory[0, 0])
        assertEquals(0x0F, graphicMemory[0, 1])

        graphicMemory.fill(0)
        checkCleanMemory(graphicMemory)

        /* With size equals zero is a no-op! */

        drwVxVyByte(0xD000, registers, memory, graphicMemory)
        checkCleanMemory(graphicMemory)

        /*
            0000 1111b
            1111 0000b
        */
        drwVxVyByte(0xD012, registers, memory, graphicMemory)
        assertEquals(0x0F, graphicMemory[0, 0])
        assertEquals(0xFF.toByte(), graphicMemory[0, 1])

        graphicMemory.fill(0)
        checkCleanMemory(graphicMemory)

        drwVxVyByte(0xD032, registers, memory, graphicMemory)
        assertEquals(0x0F, graphicMemory[0, 0])
        assertEquals(0xFF.toByte(), graphicMemory[0, 1])

        graphicMemory.fill(0)
        checkCleanMemory(graphicMemory)

        /*
            0011 1100b
            1100 0011b
        */
        drwVxVyByte(0xD202, registers, memory, graphicMemory)
        assertEquals(0x3C, graphicMemory[0, 0])
        assertEquals(0xC3.toByte(), graphicMemory[0, 1])

        graphicMemory.fill(0)
        checkCleanMemory(graphicMemory)

        drwVxVyByte(0xD402, registers, memory, graphicMemory)
        assertEquals(0x3C, graphicMemory[0, 0])
        assertEquals(0xC3.toByte(), graphicMemory[0, 1])

        graphicMemory.fill(0)
        checkCleanMemory(graphicMemory)

        /*
            1100 0011b
            0011 1100b
        */
        drwVxVyByte(0xD212, registers, memory, graphicMemory)
        assertEquals(0xC3.toByte(), graphicMemory[0, 0])
        assertEquals(0x3C, graphicMemory[0, 1])

        graphicMemory.fill(0)
        checkCleanMemory(graphicMemory)

        drwVxVyByte(0xD422, registers, memory, graphicMemory)
        assertEquals(0xC3.toByte(), graphicMemory[0, 0])
        assertEquals(0x3C, graphicMemory[0, 1])
    }

    /*
        This test uses a two-byte width to ensure
        a special implementation for alignment takes care of the position.
    */
    @Test
    fun drwVxVyAlignedExtraTest() {
        val registersMemoryMock = Mockito.mock(IMemory::class.java) as IMemory<Byte>
        Mockito.`when`(registersMemoryMock.size).thenReturn(16)
        Mockito.`when`(registersMemoryMock[0]).thenReturn(0)
        Mockito.`when`(registersMemoryMock[1]).thenReturn(1)
        Mockito.`when`(registersMemoryMock[2]).thenReturn(8)

        val registers = Registers(registersMemoryMock)
        val memory = ByteMemory(ByteArray(2))

        val graphicMemory = WrappedGraphicMemory(2, ByteMemory(ByteArray(4)));

        /*
            1111 0000b
            0000 1111b
        */
        memory[0] = 0xF0.toByte()
        memory[1] = 0x0F

        checkCleanMemory(graphicMemory)

        /*
            0000 1111 0000 0000b
            1111 0000 0000 0000b
        */
        drwVxVyByte(0xD012, registers, memory, graphicMemory)
        assertEquals(0x0F, graphicMemory[0, 0])
        assertEquals(0xF0.toByte(), graphicMemory[0, 1])
        assertEquals(0x00, graphicMemory[1, 0])
        assertEquals(0x00, graphicMemory[1, 1])

        graphicMemory.fill(0)
        checkCleanMemory(graphicMemory)

        /*
            0000 0000 1111 0000b
            0000 0000 0000 1111b
        */
        drwVxVyByte(0xD202, registers, memory, graphicMemory)
        assertEquals(0x00, graphicMemory[0, 0])
        assertEquals(0x00, graphicMemory[0, 1])
        assertEquals(0xF0.toByte(), graphicMemory[1, 0])
        assertEquals(0x0F, graphicMemory[1, 1])

        graphicMemory.fill(0)
        checkCleanMemory(graphicMemory)

        /*
            0000 0000 0000 1111b
            0000 0000 1111 0000b
        */
        drwVxVyByte(0xD212, registers, memory, graphicMemory)
        assertEquals(0x00, graphicMemory[0, 0])
        assertEquals(0x00, graphicMemory[0, 1])
        assertEquals(0x0F, graphicMemory[1, 0])
        assertEquals(0xF0.toByte(), graphicMemory[1, 1])
    }

    @Test
    fun ldVxTimerTest() {
        val tAnswer = object : Answer<Byte> {
            var t: Byte = 0

            override fun answer(invocation: InvocationOnMock?): Byte {
                return t
            }
        }

        val timerMock = Mockito.mock(ITimer::class.java)
        Mockito.`when`(timerMock.t).thenAnswer(tAnswer)

        val registers = Registers(ByteMemory(ByteArray(16)))

        registers.pc = 0x200

        assertEquals(0, registers.v[0x0])

        ldVxTimer(0xF007, registers, timerMock)
        assertEquals(0, registers.v[0x0])

        tAnswer.t = 50

        ldVxTimer(0xF007, registers, timerMock)
        assertEquals(50, registers.v[0x0])

        tAnswer.t = 255.toByte()

        ldVxTimer(0xF007, registers, timerMock)
        assertEquals(255.toByte(), registers.v[0x0])

        tAnswer.t = 128.toByte()

        ldVxTimer(0xF107, registers, timerMock)
        assertEquals(255.toByte(), registers.v[0x0])
        assertEquals(128.toByte(), registers.v[0x1])

        assertEquals(0x200 + 4 * 0x2, registers.pc)
    }

    @Test
    fun ldTimerVxTest() {
        val timerMock = Mockito.mock(ITimer::class.java)
        val registers = Registers(ByteMemory(ByteArray(16)))

        registers.pc = 0x200

        assertEquals(0, registers.v[0x0])

        ldTimerVx(0xF015, registers, timerMock)
        assertEquals(0, registers.v[0x0])
        Mockito.verify(
                timerMock,
                times(1)
        ).t = 0

        registers.v[0x0] = 50

        ldTimerVx(0xF015, registers, timerMock)
        assertEquals(50, registers.v[0x0])
        Mockito.verify(
                timerMock,
                times(1)
        ).t = 50

        registers.v[0x1] = 80

        ldTimerVx(0xF115, registers, timerMock)
        assertEquals(50, registers.v[0x0])
        assertEquals(80, registers.v[0x1])
        Mockito.verify(
                timerMock,
                times(1)
        ).t = 80

        assertEquals(0x200 + 3 * 0x2, registers.pc)
    }

    @Test
    fun ldFVxTest() {
        var registerArray = ByteArray(16)

        for (i in registerArray.indices)
            registerArray[i] = i.toByte()

        val registers = Registers(ByteMemory(registerArray))

        registers.i = 0xFFF
        registers.pc = 0x200

        for (i in registerArray.indices) {
            ldFVx(i shl 2 * 4 or 0xF029, registers)
            assertEquals(i.toByte(), registers.v[i])

            /*
                The hex font table starts at 0x000.
                Every character sprite has 5 bytes.
            */
            assertEquals(i * 5, registers.i)
        }

        for (i in 0x10..0xFF) {
            registers.v[0x0] = i.toByte()
            assertFailsWith<IllegalStateException> { ldFVx(0xF029, registers) }
        }

        assertEquals(0x200 + 16 * 0x2, registers.pc)
    }

    @Test
    fun ldBVxTest() {
        val vMock = Mockito.mock(IMemory::class.java) as IMemory<Byte>

        Mockito.`when`(vMock.size).thenReturn(16)

        Mockito.`when`(vMock[0x0]).thenReturn(0)
        Mockito.`when`(vMock[0x1]).thenReturn(1)
        Mockito.`when`(vMock[0x2]).thenReturn(89)
        Mockito.`when`(vMock[0x3]).thenReturn(100)
        Mockito.`when`(vMock[0x4]).thenReturn(123)
        Mockito.`when`(vMock[0x5]).thenReturn(127)
        Mockito.`when`(vMock[0x6]).thenReturn(128.toByte())
        Mockito.`when`(vMock[0x7]).thenReturn(255.toByte())

        val registers = Registers(vMock)
        registers.pc = 0x200
        registers.i = 0x100

        val memory = ByteMemory(ByteArray(0x1000))

        for (i in 0x0..0xF)
            for (j in 0..2) {
                assertEquals(0x00, memory[0x100 + i * 3 + j])
            }

        for (i in 0x0..0x7) {
            ldBVx(i shl 2 * 4 or 0xF033, registers, memory)
            registers.i += 3
        }

        assertEquals(0x200 + 8 * 0x2, registers.pc)

        assertEquals(0, memory[0x100 + 0 * 3 + 0x0])
        assertEquals(0, memory[0x100 + 0 * 3 + 0x1])
        assertEquals(0, memory[0x100 + 0 * 3 + 0x2])

        assertEquals(0, memory[0x100 + 1 * 3 + 0x0])
        assertEquals(0, memory[0x100 + 1 * 3 + 0x1])
        assertEquals(1, memory[0x100 + 1 * 3 + 0x2])

        assertEquals(0, memory[0x100 + 2 * 3 + 0x0])
        assertEquals(8, memory[0x100 + 2 * 3 + 0x1])
        assertEquals(9, memory[0x100 + 2 * 3 + 0x2])

        assertEquals(1, memory[0x100 + 3 * 3 + 0x0])
        assertEquals(0, memory[0x100 + 3 * 3 + 0x1])
        assertEquals(0, memory[0x100 + 3 * 3 + 0x2])

        assertEquals(1, memory[0x100 + 4 * 3 + 0x0])
        assertEquals(2, memory[0x100 + 4 * 3 + 0x1])
        assertEquals(3, memory[0x100 + 4 * 3 + 0x2])

        assertEquals(1, memory[0x100 + 5 * 3 + 0x0])
        assertEquals(2, memory[0x100 + 5 * 3 + 0x1])
        assertEquals(7, memory[0x100 + 5 * 3 + 0x2])

        assertEquals(1, memory[0x100 + 6 * 3 + 0x0])
        assertEquals(2, memory[0x100 + 6 * 3 + 0x1])
        assertEquals(8, memory[0x100 + 6 * 3 + 0x2])

        assertEquals(2, memory[0x100 + 7 * 3 + 0x0])
        assertEquals(5, memory[0x100 + 7 * 3 + 0x1])
        assertEquals(5, memory[0x100 + 7 * 3 + 0x2])
    }
}
