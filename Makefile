all: testconst testbinop testif testvars testfunc testobjs

testconst: testint testbool teststr

testbinop: testadd testsub testmul testdiv testless testlesseq testgreater testgreatereq testeqeq testand testor testnot

testfunc: testfuncdef testfuncall

testvars: testbind testvarref

testobjs: testfmut testflookup testmethodcall

runtest:
	llvm-as -f llvm-code.s
	llvm-ld -o test-footle llvm-code.s.bc helper.o

helper: helper.o
	@~clements/llvm-gcc4.2-2.2-x86-linux-RHEL4/bin/llvm-gcc -c -emit-llvm helper.c

testint: llvm-code.s helper
	@java -jar footle.jar -emit-llvm tests/integer1
	@llvm-as -f llvm-code.s
	@llvm-ld -o test-footle llvm-code.s.bc helper.o
	@test-footle "Integer Test 1" 42

testbool: llvm-code.s helper
	@java -jar footle.jar tests/bool1
	@llvm-as -f llvm-code.s
	@llvm-ld -o test-footle llvm-code.s.bc helper.o
	@test-footle "Bool Test 1" true

teststr: llvm-code.s helper
	@java -jar footle.jar tests/str1
	@llvm-as -f llvm-code.s
	@llvm-ld -o test-footle llvm-code.s.bc helper.o
	@test-footle "String Test 1" string

testadd: llvm-code.s helper
	@java -jar footle.jar tests/add1
	@llvm-as -f llvm-code.s
	@llvm-ld -o test-footle llvm-code.s.bc helper.o
	@test-footle "Add Test 1" 3

testsub: llvm-code.s helper
	@java -jar footle.jar tests/sub1
	@llvm-as -f llvm-code.s
	@llvm-ld -o test-footle llvm-code.s.bc helper.o
	@test-footle "Subtract Test 1" 5

testmul: llvm-code.s helper
	@java -jar footle.jar tests/mul1
	@llvm-as -f llvm-code.s
	@llvm-ld -o test-footle llvm-code.s.bc helper.o
	@test-footle "Multiply Test 1" 20

testdiv: llvm-code.s helper
	@java -jar footle.jar tests/div1
	@llvm-as -f llvm-code.s
	@llvm-ld -o test-footle llvm-code.s.bc helper.o
	@test-footle "Division Test 1" 9

testless: llvm-code.s helper
	@java -jar footle.jar tests/lessthan1
	@llvm-as -f llvm-code.s
	@llvm-ld -o test-footle llvm-code.s.bc helper.o
	@test-footle "Less Than Test 1" true

testlesseq: llvm-code.s helper
	@java -jar footle.jar tests/lessequal1
	@llvm-as -f llvm-code.s
	@llvm-ld -o test-footle llvm-code.s.bc helper.o
	@test-footle "Less Than Equal Test 1" true

	@java -jar footle.jar tests/lessequal2
	@llvm-as -f llvm-code.s
	@llvm-ld -o test-footle llvm-code.s.bc helper.o
	@test-footle "Less Than Equal Test 2" true

testgreater: llvm-code.s helper
	@java -jar footle.jar tests/greaterthan1
	@llvm-as -f llvm-code.s
	@llvm-ld -o test-footle llvm-code.s.bc helper.o
	@test-footle "Greater Than Test 1" true

testgreatereq: llvm-code.s helper
	@java -jar footle.jar tests/greaterequal1
	@llvm-as -f llvm-code.s
	@llvm-ld -o test-footle llvm-code.s.bc helper.o
	@test-footle "Greater Than Equal Test 1" true

	@java -jar footle.jar tests/greaterequal2
	@llvm-as -f llvm-code.s
	@llvm-ld -o test-footle llvm-code.s.bc helper.o
	@test-footle "Greater Than Equal Test 2" true

testeqeq: llvm-code.s helper
	@java -jar footle.jar tests/equalequal1
	@llvm-as -f llvm-code.s
	@llvm-ld -o test-footle llvm-code.s.bc helper.o
	@test-footle "Equal Equal Test 1" true

testand: llvm-code.s helper
	@java -jar footle.jar tests/and1
	@llvm-as -f llvm-code.s
	@llvm-ld -o test-footle llvm-code.s.bc helper.o
	@test-footle "And Test 1" true

testor: llvm-code.s helper
	@java -jar footle.jar tests/or1
	@llvm-as -f llvm-code.s
	@llvm-ld -o test-footle llvm-code.s.bc helper.o
	@test-footle "Or Test 1" true

testnot: llvm-code.s helper
	@java -jar footle.jar tests/not1
	@llvm-as -f llvm-code.s
	@llvm-ld -o test-footle llvm-code.s.bc helper.o
	@test-footle "Not Test 1" true

testvarref: llvm-code.s helper
	@java -jar footle.jar tests/varref1
	@llvm-as -f llvm-code.s
	@llvm-ld -o test-footle llvm-code.s.bc helper.o
	@test-footle "Var Ref Test 1" 2

	@java -jar footle.jar tests/varref2
	@llvm-as -f llvm-code.s
	@llvm-ld -o test-footle llvm-code.s.bc helper.o
	@test-footle "Var Ref Decrement Test 2" 0

testif: llvm-code.s helper
	@java -jar footle.jar tests/if1
	@llvm-as -f llvm-code.s
	@llvm-ld -o test-footle llvm-code.s.bc helper.o
	@test-footle "If Test 1" 1

	@java -jar footle.jar tests/if2
	@llvm-as -f llvm-code.s
	@llvm-ld -o test-footle llvm-code.s.bc helper.o
	@test-footle "If Test 2" 5

	@java -jar footle.jar tests/ifelse1
	@llvm-as -f llvm-code.s
	@llvm-ld -o test-footle llvm-code.s.bc helper.o
	@test-footle "If Test 3" 2

testbind: llvm-code.s helper
	@java -jar footle.jar tests/bind1
	@llvm-as -f llvm-code.s
	@llvm-ld -o test-footle llvm-code.s.bc helper.o
	@test-footle "Bind Test 1" void

testwhile: llvm-code.s helper
	@java -jar footle.jar tests/while1
	@llvm-as -f llvm-code.s
	@llvm-ld -o test-footle llvm-code.s.bc helper.o
	@test-footle "While Test 1" 0

testfuncdef: llvm-code.s helper
	@java -jar footle.jar tests/funcdef1
	@llvm-as -f llvm-code.s
	@llvm-ld -o test-footle llvm-code.s.bc helper.o
	@test-footle "Funtion Definition Test 1" void

testfuncall: llvm-code.s helper
	@java -jar footle.jar tests/funcall1
	@llvm-as -f llvm-code.s
	@llvm-ld -o test-footle llvm-code.s.bc helper.o
	@test-footle "Funtion Call Test 1" 1

	@java -jar footle.jar tests/funcall2
	@llvm-as -f llvm-code.s
	@llvm-ld -o test-footle llvm-code.s.bc helper.o
	@test-footle "Funtion Call Test 2" 10

	@java -jar footle.jar tests/funcall3
	@llvm-as -f llvm-code.s
	@llvm-ld -o test-footle llvm-code.s.bc helper.o
	@test-footle "Funtion Call Test 3" 3

testfmut: llvm-code.s helper
	@java -jar footle.jar tests/fieldmut1
	@llvm-as -f llvm-code.s
	@llvm-ld -o test-footle llvm-code.s.bc helper.o
	@test-footle "Field Mutation Test 1" void

testflookup: llvm-code.s helper
	@java -jar footle.jar tests/fieldlookup1
	@llvm-as -f llvm-code.s
	@llvm-ld -o test-footle llvm-code.s.bc helper.o
	@test-footle "Field Lookup Test 1" 1

	@java -jar footle.jar tests/fieldlookup2
	@llvm-as -f llvm-code.s
	@llvm-ld -o test-footle llvm-code.s.bc helper.o
	@test-footle "Field Lookup Test 2" string

testmethodcall: llvm-code.s helper
	@java -jar footle.jar tests/methodcall1
	@llvm-as -f llvm-code.s
	@llvm-ld -o test-footle llvm-code.s.bc helper.o
	@test-footle "Method Call Test 1" true

testcompound: llvm-code.s helper
	@java -jar footle.jar tests/compound1
	@llvm-as -f llvm-code.s
	@llvm-ld -o test-footle llvm-code.s.bc helper.o
	@test-footle "Compound Test 1" 80

clean:
	rm -f test-footle*
	rm -f test-footle.s.bc
