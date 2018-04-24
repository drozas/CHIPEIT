package es.chipeit.lib.core

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import org.mockito.BDDMockito.*
import org.mockito.Mockito
import org.mockito.Mockito.never
import org.mockito.Mockito.times

import es.chipeit.lib.interfaces.IMemory
import es.chipeit.lib.interfaces.IRegisters
import es.chipeit.lib.interfaces.ITimer
import es.chipeit.lib.io.IUserKeyboard

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
    fun retTest() {
        val registers = Registers(ByteMemory(ByteArray(16)))
        val stackMock = Mockito.mock(IMemory::class.java) as IMemory<Int>

        Mockito.`when`(stackMock[2]).thenReturn(0x222)
        Mockito.`when`(stackMock[1]).thenReturn(0x456)
        Mockito.`when`(stackMock[0]).thenReturn(0x789)

        registers.sp = 2

        ret(registers, stackMock)

        assertEquals(1, registers.sp)
        assertEquals(0x222, registers.pc)
        Mockito.verify(
                stackMock,
                times(1)
        )[2]

        ret(registers, stackMock)

        assertEquals(0, registers.sp)
        assertEquals(0x456, registers.pc)
        Mockito.verify(
                stackMock,
                times(1)
        )[1]

        ret(registers, stackMock)

        assertEquals(-1, registers.sp)
        assertEquals(0x789, registers.pc)
        Mockito.verify(
                stackMock,
                times(1)
        )[0]
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
        ).pc = 0x225

        Mockito.verify(
                registersMock,
                times(1)
        ).pc = 0x512

        Mockito.verify(
                registersMock,
                times(1)
        ).pc = 0xFFF
    }

    @Test
    fun callTest() {
        val registers = Registers(ByteMemory(ByteArray(16)))
        val stackMock = Mockito.mock(IMemory::class.java) as IMemory<Int>

        registers.sp = -1
        registers.pc = 0x789

        call(0x2345, registers, stackMock)

        assertEquals(0, registers.sp)
        assertEquals(0x345, registers.pc)
        Mockito.verify(
                stackMock,
                times(1)
        )[0] = 0x789

        call(0x2200, registers, stackMock)

        assertEquals(1, registers.sp)
        assertEquals(0x200, registers.pc)
        Mockito.verify(
                stackMock,
                times(1)
        )[1] = 0x345

        call(0x2FFF, registers, stackMock)

        assertEquals(2, registers.sp)
        assertEquals(0xFFF, registers.pc)
        Mockito.verify(
                stackMock,
                times(1)
        )[2] = 0x200
    }

    @Test
    fun ldVxByteTest() {
        val registersMemoryMock = Mockito.mock(IMemory::class.java) as IMemory<Byte>
        Mockito.`when`(registersMemoryMock.size).thenReturn(16)

        val registersMock = Registers(registersMemoryMock)
        registersMock.pc = 0x200

        ldVxByte(0x6011, registersMock)
        assertEquals(0x200 + 2, registersMock.pc)

        ldVxByte(0x6122, registersMock)
        assertEquals(0x200 + 4, registersMock.pc)

        ldVxByte(0x6233, registersMock)
        assertEquals(0x200 + 6, registersMock.pc)

        ldVxByte(0x6344, registersMock)
        assertEquals(0x200 + 8, registersMock.pc)

        ldVxByte(0x6455, registersMock)
        assertEquals(0x200 + 10, registersMock.pc)

        ldVxByte(0x6566, registersMock)
        assertEquals(0x200 + 12, registersMock.pc)

        ldVxByte(0x6677, registersMock)
        assertEquals(0x200 + 14, registersMock.pc)

        ldVxByte(0x6788, registersMock)
        assertEquals(0x200 + 16, registersMock.pc)

        ldVxByte(0x6899, registersMock)
        assertEquals(0x200 + 18, registersMock.pc)

        ldVxByte(0x69AA, registersMock)
        assertEquals(0x200 + 20, registersMock.pc)

        ldVxByte(0x6ABB, registersMock)
        assertEquals(0x200 + 22, registersMock.pc)

        ldVxByte(0x6BCC, registersMock)
        assertEquals(0x200 + 24, registersMock.pc)

        ldVxByte(0x6CDD, registersMock)
        assertEquals(0x200 + 26, registersMock.pc)

        ldVxByte(0x6DEE, registersMock)
        assertEquals(0x200 + 28, registersMock.pc)

        ldVxByte(0x6EFF, registersMock)
        assertEquals(0x200 + 30, registersMock.pc)

        ldVxByte(0x6F00, registersMock)
        assertEquals(0x200 + 32, registersMock.pc)

        Mockito.verify(
                registersMemoryMock,
                times(1)
        )[0x0] = 0x11

        Mockito.verify(
                registersMemoryMock,
                times(1)
        )[0x1] = 0x22

        Mockito.verify(
                registersMemoryMock,
                times(1)
        )[0x2] = 0x33

        Mockito.verify(
                registersMemoryMock,
                times(1)
        )[0x3] = 0x44

        Mockito.verify(
                registersMemoryMock,
                times(1)
        )[0x4] = 0x55

        Mockito.verify(
                registersMemoryMock,
                times(1)
        )[0x5] = 0x66

        Mockito.verify(
                registersMemoryMock,
                times(1)
        )[0x6] = 0x77

        Mockito.verify(
                registersMemoryMock,
                times(1)
        )[0x7] = 0x88.toByte()

        Mockito.verify(
                registersMemoryMock,
                times(1)
        )[0x8] = 0x99.toByte()

        Mockito.verify(
                registersMemoryMock,
                times(1)
        )[0x9] = 0xAA.toByte()

        Mockito.verify(
                registersMemoryMock,
                times(1)
        )[0xA] = 0xBB.toByte()

        Mockito.verify(
                registersMemoryMock,
                times(1)
        )[0xB] = 0xCC.toByte()

        Mockito.verify(
                registersMemoryMock,
                times(1)
        )[0xC] = 0xDD.toByte()

        Mockito.verify(
                registersMemoryMock,
                times(1)
        )[0xD] = 0xEE.toByte()

        Mockito.verify(
                registersMemoryMock,
                times(1)
        )[0xE] = 0xFF.toByte()

        Mockito.verify(
                registersMemoryMock,
                times(1)
        )[0xF] = 0x00
    }

    @Test
    fun skpVxTest() {
        val vMock = Mockito.mock(IMemory::class.java) as IMemory<Byte>
        Mockito.`when`(vMock.size).thenReturn(16)
        Mockito.`when`(vMock[0xA]).thenReturn(3)
        Mockito.`when`(vMock[0xB]).thenReturn(8)

        val soundTimerMock = Mockito.mock(ITimer::class.java)

        val keyboard = Keyboard(soundTimerMock)
        keyboard.pressKey(IUserKeyboard.Keys.KEY_3)
        keyboard.releaseKey(IUserKeyboard.Keys.KEY_8)

        val registersMock = Registers(vMock)
        registersMock.pc = 0x200

        skpVx(0xEA9E, registersMock, keyboard)

        // Next instruction was skipped
        assertEquals(0x200 + 4, registersMock.pc)
        Mockito.verify(
                vMock,
                times(1)
        )[0xA]

        skpVx(0xEB9E, registersMock, keyboard)

        // Next instruction was not skipped
        assertEquals(0x204 + 2, registersMock.pc)
        Mockito.verify(
                vMock,
                times(1)
        )[0xB]
    }

    @Test
    fun sknpVxTest() {
        val vMock = Mockito.mock(IMemory::class.java) as IMemory<Byte>
        Mockito.`when`(vMock.size).thenReturn(16)
        Mockito.`when`(vMock[0xA]).thenReturn(3)
        Mockito.`when`(vMock[0xB]).thenReturn(8)

        val soundTimerMock = Mockito.mock(ITimer::class.java)

        val keyboard = Keyboard(soundTimerMock)
        keyboard.releaseKey(IUserKeyboard.Keys.KEY_3)
        keyboard.pressKey(IUserKeyboard.Keys.KEY_8)

        val registersMock = Registers(vMock)
        registersMock.pc = 0x200

        sknpVx(0xEA9E, registersMock, keyboard)

        // Next instruction was skipped
        assertEquals(0x200 + 4, registersMock.pc)
        Mockito.verify(
                vMock,
                times(1)
        )[0xA]

        sknpVx(0xEB9E, registersMock, keyboard)

        // Next instruction was not skipped
        assertEquals(0x204 + 2, registersMock.pc)
        Mockito.verify(
                vMock,
                times(1)
        )[0xB]
    }

    @Test
    fun ldVxKTest() {
        val vRegsMock = Mockito.mock(IMemory::class.java) as IMemory<Byte>
        Mockito.`when`(vRegsMock.size).thenReturn(16)

        val registers = Registers(vRegsMock)
        registers.pc = 0x200

        var isSoundTimerActive = true

        val soundTimerMock = Mockito.mock(ITimer::class.java)
        Mockito.`when`(soundTimerMock.isActive()).then { isSoundTimerActive }

        val keyboard = Keyboard(soundTimerMock)
        val instruction = 0xF70A

        assertFalse(keyboard.isCapturingNextKeyRelease)

        ldVxK(instruction, registers, keyboard)

        assertTrue(keyboard.isCapturingNextKeyRelease)
        assertEquals(IUserKeyboard.Keys.NONE, keyboard.capturedKeyRelease)
        assertEquals(0x200, registers.pc)
        Mockito.verify(soundTimerMock, never()).setRegister(4)
        Mockito.verify(soundTimerMock, never()).isActive()

        ldVxK(instruction, registers, keyboard)

        assertTrue(keyboard.isCapturingNextKeyRelease)
        assertEquals(IUserKeyboard.Keys.NONE, keyboard.capturedKeyRelease)
        assertEquals(0x200, registers.pc)
        Mockito.verify(soundTimerMock, never()).setRegister(4)
        Mockito.verify(soundTimerMock, never()).isActive()

        keyboard.pressKey(IUserKeyboard.Keys.KEY_8)
        keyboard.pressKey(IUserKeyboard.Keys.KEY_5)
        Mockito.verify(soundTimerMock, times(2)).setRegister(4)

        ldVxK(instruction, registers, keyboard)

        assertTrue(keyboard.isCapturingNextKeyRelease)
        assertEquals(IUserKeyboard.Keys.NONE, keyboard.capturedKeyRelease)
        assertEquals(0x200, registers.pc)
        Mockito.verify(soundTimerMock, times(3)).setRegister(4)
        Mockito.verify(soundTimerMock, never()).isActive()

        ldVxK(instruction, registers, keyboard)

        assertTrue(keyboard.isCapturingNextKeyRelease)
        assertEquals(IUserKeyboard.Keys.NONE, keyboard.capturedKeyRelease)
        assertEquals(0x200, registers.pc)
        Mockito.verify(soundTimerMock, times(4)).setRegister(4)
        Mockito.verify(soundTimerMock, never()).isActive()

        keyboard.releaseKey(IUserKeyboard.Keys.KEY_5)
        keyboard.releaseKey(IUserKeyboard.Keys.KEY_8)

        assertEquals(IUserKeyboard.Keys.KEY_5, keyboard.capturedKeyRelease)

        ldVxK(instruction, registers, keyboard)

        assertFalse(keyboard.isCapturingNextKeyRelease)
        assertEquals(IUserKeyboard.Keys.KEY_5, keyboard.capturedKeyRelease)
        assertEquals(0x200, registers.pc)
        Mockito.verify(soundTimerMock, times(4)).setRegister(4)
        Mockito.verify(soundTimerMock, times(1)).isActive()

        ldVxK(instruction, registers, keyboard)

        assertFalse(keyboard.isCapturingNextKeyRelease)
        assertEquals(IUserKeyboard.Keys.KEY_5, keyboard.capturedKeyRelease)
        assertEquals(0x200, registers.pc)
        Mockito.verify(soundTimerMock, times(4)).setRegister(4)
        Mockito.verify(soundTimerMock, times(2)).isActive()

        isSoundTimerActive = false

        ldVxK(instruction, registers, keyboard)

        assertFalse(keyboard.isCapturingNextKeyRelease)
        assertEquals(IUserKeyboard.Keys.NONE, keyboard.capturedKeyRelease)
        assertEquals(0x200 + 2, registers.pc)
        Mockito.verify(
                vRegsMock,
                times(1)
        )[0x7] = IUserKeyboard.Keys.KEY_5.data.id
        Mockito.verify(soundTimerMock, times(3)).isActive()

        isSoundTimerActive = true

        ldVxK(instruction, registers, keyboard)

        assertTrue(keyboard.isCapturingNextKeyRelease)
        assertEquals(IUserKeyboard.Keys.NONE, keyboard.capturedKeyRelease)
        assertEquals(0x200 + 2, registers.pc)
        Mockito.verify(soundTimerMock, times(4)).setRegister(4)
        Mockito.verify(soundTimerMock, times(3)).isActive()

        keyboard.pressKey(IUserKeyboard.Keys.KEY_8)
        keyboard.pressKey(IUserKeyboard.Keys.KEY_5)
        Mockito.verify(soundTimerMock, times(6)).setRegister(4)

        keyboard.releaseKey(IUserKeyboard.Keys.KEY_8)
        keyboard.releaseKey(IUserKeyboard.Keys.KEY_5)

        assertEquals(IUserKeyboard.Keys.KEY_8, keyboard.capturedKeyRelease)

        ldVxK(instruction, registers, keyboard)

        assertFalse(keyboard.isCapturingNextKeyRelease)
        assertEquals(IUserKeyboard.Keys.KEY_8, keyboard.capturedKeyRelease)
        assertEquals(0x200 + 2, registers.pc)
        Mockito.verify(soundTimerMock, times(6)).setRegister(4)
        Mockito.verify(soundTimerMock, times(4)).isActive()

        isSoundTimerActive = false

        ldVxK(instruction, registers, keyboard)

        assertFalse(keyboard.isCapturingNextKeyRelease)
        assertEquals(IUserKeyboard.Keys.NONE, keyboard.capturedKeyRelease)
        assertEquals(0x200 + 4, registers.pc)
        Mockito.verify(
                vRegsMock,
                times(1)
        )[0x7] = IUserKeyboard.Keys.KEY_8.data.id
        Mockito.verify(soundTimerMock, times(5)).isActive()
    }
}
