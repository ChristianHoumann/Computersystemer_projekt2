import chisel3._
import chisel3.iotesters.PeekPokeTester

class RegisterFileTester(dut: RegisterFile) extends PeekPokeTester(dut) {

  // Write 22 to R0
  poke(dut.io.writeEnable, true)
  poke(dut.io.writeData,22)
  poke(dut.io.writeSel,0)
  poke(dut.io.aSel,3)
  poke(dut.io.bSel,4)
  step(5)

  // Write 42 to R1
  poke(dut.io.writeEnable, true)
  poke(dut.io.writeData,42)
  poke(dut.io.writeSel,1)
  poke(dut.io.aSel,3)
  poke(dut.io.bSel,4)
  step(5)

  // read R0 and R1
  poke(dut.io.writeEnable, false)
  poke(dut.io.writeData,22)
  poke(dut.io.writeSel,0)
  poke(dut.io.aSel,0)
  poke(dut.io.bSel,1)
  step(5)

}

object RegisterFileTester {
  def main(args: Array[String]): Unit = {
    println("Testing the RegisterFile")
    iotesters.Driver.execute(
      Array("--generate-vcd-output", "on",
        "--target-dir", "generated",
        "--top-name", "RegisterFile"),
      () => new RegisterFile()) {
      c => new RegisterFileTester(c)
    }
  }
}
