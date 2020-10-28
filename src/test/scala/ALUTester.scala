import chisel3._
import chisel3.iotesters.PeekPokeTester

class ALUTester(dut: ALU) extends PeekPokeTester(dut) {

  // Add 7 and 4
  poke(dut.io.input1,7)
  poke(dut.io.input2,4)
  poke(dut.io.sel,"b001".U)
  step(5)

  // SUB 7 and 4
  poke(dut.io.input1,7)
  poke(dut.io.input2,4)
  poke(dut.io.sel,"b010".U)
  step(5)

  // MULT 7 and 4
  poke(dut.io.input1,7)
  poke(dut.io.input2,4)
  poke(dut.io.sel,"b011".U)
  step(5)

  // Parse 1
  poke(dut.io.input1,7)
  poke(dut.io.sel,"b100".U)
  step(5)

  // Parse 2
  poke(dut.io.input2,4)
  poke(dut.io.sel,"b101".U)
  step(5)

  // Test zero
  poke(dut.io.input1,7)
  poke(dut.io.input2,7)
  poke(dut.io.sel,"b010".U)
  step(5)

}

object ALUTester {
  def main(args: Array[String]): Unit = {
    println("Testing the ALU")
    iotesters.Driver.execute(
      Array("--generate-vcd-output", "on",
        "--target-dir", "generated",
        "--top-name", "ALU"),
      () => new ALU()) {
      c => new ALUTester(c)
    }
  }
}
