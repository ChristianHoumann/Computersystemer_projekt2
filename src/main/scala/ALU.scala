import chisel3._
import chisel3.util._

class ALU extends Module {
  val io = IO(new Bundle {
    //Define the module interface here (inputs/outputs)
    val zero = Output(Bool())

    val input1 = Input(SInt(32.W))
    val input2 = Input(SInt(32.W))
    val sel = Input(UInt(3.W))
    val result = Output(SInt(32.W))

  })
  //Implement this module here
  io.result := 0.S(32.W)
  io.zero := false.B
  switch (io.sel) {
    is ("b001".U) {
      //a
      io.result := io.input1 + io.input2
      io.zero := ( io.input1+io.input2 === 0.S(32.W) )
    }
    is ("b010".U) {
      //S
      io.result := io.input1-io.input2
      io.zero := ( io.input1-io.input2 === 0.S(32.W) )
    }
    is ("b011".U) {
      io.result := io.input1 * io.input2
      io.zero := ( io.input1*io.input2 === 0.S(32.W) )
    }
    is ("b100".U) {
      io.result := io.input1
      io.zero := ( io.input1 === 0.S(32.W) )
    }
    is ("b101".U) {
      io.result := io.input2
      io.zero := ( io.input2 === 0.S(32.W) )
    }
  }


}