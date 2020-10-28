import chisel3._
import chisel3.iotesters
import chisel3.iotesters.PeekPokeTester

class ControlUnitTester(dut: ControlUnit) extends PeekPokeTester(dut) {

  //Hold for 5 clock cycles
  poke(dut.io.opcode, "b0001".U)
  step(5)

  poke(dut.io.opcode, "b0010".U)
  step(5)

  poke(dut.io.opcode, "b0011".U)
  step(5)

  poke(dut.io.opcode, "b0100".U)
  step(5)

  poke(dut.io.opcode, "b0101".U)
  step(5)

  poke(dut.io.opcode, "b0110".U)
  step(5)

  poke(dut.io.opcode, "b0111".U)
  step(5)

  poke(dut.io.opcode, "b1000".U)
  step(5)

  poke(dut.io.opcode, "b1001".U)
  step(5)

  poke(dut.io.opcode, "b1111".U)
  step(5)

}

  object ControlUnitTester {
    def main(args: Array[String]): Unit = {
      println("Running the ControlUnit tester")
      iotesters.Driver.execute(
        Array("--generate-vcd-output", "on",
          "--target-dir", "generated",
          "--top-name", "ControlUnit"),
        () => new ControlUnit()) {
        c => new ControlUnitTester(c)
      }
    }
  }


