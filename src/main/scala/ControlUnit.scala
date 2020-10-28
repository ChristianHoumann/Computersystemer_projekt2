import chisel3._
import chisel3.util._

class ControlUnit extends Module {
  val io = IO(new Bundle {
    //Define the module interface here (inputs/outputs)
    val opcode = Input(UInt(4.W))
    val branch = Output(Bool())
    //val memread = Output(Bool())
    val memtoreg = Output(Bool())
    val aluop = Output(UInt(3.W))
    val writeEnable = Output(Bool())
    val alusrc = Output(Bool())
    val regwrite = Output(Bool())
    val end = Output(Bool())

  })

  //Implement this module here
  io.branch := false.B
  //val memread = Output(Bool())
  io.memtoreg := false.B
  io.aluop := 0.U(3.W)
  io.writeEnable := false.B
  io.alusrc := false.B
  io.regwrite := false.B
  io.end := false.B

  switch (io.opcode) {
    is ("b0001".U) {
      //add
      io.aluop := "b001".U
      io.regwrite := true.B
    }
    is ("b0010".U) {
      //subi
      io.aluop := "b010".U
      io.regwrite := true.B
      io.alusrc := true.B
    }
    is ("b0011".U) {
      //mult
      io.aluop := "b011".U
      io.regwrite := true.B
    }
    is ("b0100".U) {
      //addi
      io.aluop := "b001".U
      io.regwrite := true.B
      io.alusrc := true.B
    }
    is ("b0101".U) {
      //li
      io.aluop := "b101".U
      io.regwrite := true.B
      io.alusrc := true.B
    }
    is ("b0110".U) {
      //ld
      io.aluop := "b100".U
      io.regwrite := true.B
      io.memtoreg := true.B
    }
    is ("b0111".U) {
      //sd
      io.aluop := "b100".U
      io.writeEnable := true.B
    }
    is ("b1000".U) {
      //jeq
      io.aluop := "b010".U
      io.branch := true.B
    }
    is ("b1001".U) {
      //jr
      io.aluop := "b010".U
      io.branch := true.B
    }
    is ("b1111".U) {
      //end
      io.end := true.B
    }
  }

}