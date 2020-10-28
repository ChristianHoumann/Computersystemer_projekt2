import chisel3._
import chisel3.util._

class CPUTop extends Module {
  val io = IO(new Bundle {
    val done = Output(Bool ())
    val run = Input(Bool ())
    //This signals are used by the tester for loading and dumping the memory content, do not touch
    val testerDataMemEnable = Input(Bool ())
    val testerDataMemAddress = Input(UInt (16.W))
    val testerDataMemDataRead = Output(UInt (32.W))
    val testerDataMemWriteEnable = Input(Bool ())
    val testerDataMemDataWrite = Input(UInt (32.W))
    //This signals are used by the tester for loading and dumping the memory content, do not touch
    val testerProgMemEnable = Input(Bool ())
    val testerProgMemAddress = Input(UInt (16.W))
    val testerProgMemDataRead = Output(UInt (32.W))
    val testerProgMemWriteEnable = Input(Bool ())
    val testerProgMemDataWrite = Input(UInt (32.W))
  })

  //Creating components
  val programCounter = Module(new ProgramCounter())
  val dataMemory = Module(new DataMemory())
  val programMemory = Module(new ProgramMemory())
  val registerFile = Module(new RegisterFile())
  val controlUnit = Module(new ControlUnit())
  val alu = Module(new ALU())

  //Connecting the modules
  programCounter.io.run := io.run
  programMemory.io.address := programCounter.io.programCounter

  ////////////////////////////////////////////
  //Continue here with your connections
  ////////////////////////////////////////////

  //// instruction split
  controlUnit.io.opcode := programMemory.io.instructionRead(31,28)
  registerFile.io.writeSel := programMemory.io.instructionRead(27,25)
  registerFile.io.aSel := programMemory.io.instructionRead(24,22)
  registerFile.io.aSel := programMemory.io.instructionRead(21,19)

  val intermediate: UInt = programMemory.io.instructionRead(15,0)
  val extendedIntermediate: UInt = Cat(0.U(16.W), intermediate)

  //// ControlUnit outputs
  io.done := controlUnit.io.`end`
  // branch
  val branch: Bool = (controlUnit.io.branch && alu.io.zero)
  programCounter.io.jump := branch
  programCounter.io.programCounterJump := intermediate

  //rest
  registerFile.io.writeEnable := controlUnit.io.regwrite
  dataMemory.io.writeEnable := controlUnit.io.writeEnable

  alu.io.sel := controlUnit.io.aluop
  //ALU
  alu.io.input1 := registerFile.io.a
  when (controlUnit.io.alusrc) {
    alu.io.input2 := extendedIntermediate
  }.otherwise {
    alu.io.input2 := registerFile.io.b
  }

  //registerfile memory
  when (controlUnit.io.memtoreg) {
    registerFile.io.writeData := dataMemory.io.dataRead
  }.otherwise {
    registerFile.io.writeData := alu.io.result
  }

  //data memory
  dataMemory.io.address := alu.io.result
  dataMemory.io.dataWrite := registerFile.io.b


  //This signals are used by the tester for loading the program to the program memory, do not touch
  programMemory.io.testerAddress := io.testerProgMemAddress
  io.testerProgMemDataRead := programMemory.io.testerDataRead
  programMemory.io.testerDataWrite := io.testerProgMemDataWrite
  programMemory.io.testerEnable := io.testerProgMemEnable
  programMemory.io.testerWriteEnable := io.testerProgMemWriteEnable
  //This signals are used by the tester for loading and dumping the data memory content, do not touch
  dataMemory.io.testerAddress := io.testerDataMemAddress
  io.testerDataMemDataRead := dataMemory.io.testerDataRead
  dataMemory.io.testerDataWrite := io.testerDataMemDataWrite
  dataMemory.io.testerEnable := io.testerDataMemEnable
  dataMemory.io.testerWriteEnable := io.testerDataMemWriteEnable
}