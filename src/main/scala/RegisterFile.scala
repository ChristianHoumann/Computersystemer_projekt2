import chisel3._
import chisel3.util._

class RegisterFile extends Module {
  val io = IO(new Bundle {
    //Define the module interface here (inputs/outputs)
    val aSel = Input(UInt(3.W))
    val bSel = Input(UInt(3.W))
    val writeData = Input(SInt(32.W))
    val writeSel = Input(UInt(3.W))
    val writeEnable = Input(Bool())

    val a = Output(SInt(32.W))
    val b = Output(SInt(32.W))
  })
  //Implement this module here
  val registerFile = Reg(Vec(8, SInt(32.W)))

  //write
  when (io.writeEnable) {
    registerFile(io.writeSel) := io.writeData
  }

  //read
  io.a := registerFile(io.aSel)
  io.b := registerFile(io.bSel)


}
