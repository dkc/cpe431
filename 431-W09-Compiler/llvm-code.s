%field = type {i32, i32}
%slots = type {%field, %slots*}
@emptyslots = constant %slots undef
%eframe = type{%eframe*, i32, [0 x i32]}
%strobj = type{i32, %slots*, i8*}
%floatobj = type{i32, float}
%cobj = type{i32, %slots*, %eframe*}
%pobj = type{i32, %slots*}
define i32* @field_lookup( i32 %fid, %slots* %slot){
%r0 = getelementptr %slots* %slot, i32 0, i32 0
%r1 = getelementptr %field* %r0, i32 0, i32 0
%r2 = load i32* %r1
%r3 = icmp eq i32 %fid, %r2
br i1 %r3, label %eqret, label %neqrec
neqrec:
%r4 = getelementptr %slots* %slot, i32 0, i32 1
%r5 = load %slots** %r4
%r14 = call i32* @field_lookup( i32 %fid, %slots* %r5)
ret i32* %r14
eqret:
%r15 = getelementptr %field* %r0, i32 0, i32 1
ret i32* %r15
}
define i32 @dispatch_fun(%cobj* %clos, i32 %len, i32* %args){
%frameptr = getelementptr %cobj* %clos, i32 0, i32 2
%envframe = load %eframe** %frameptr
%fidptr = getelementptr %cobj* %clos, i32 0, i32 0
%shftreg = load i32* %fidptr
%fid = lshr i32 %shftreg, 2
switch i32 %fid, label %default []
default:
ret i32 0
}
define %eframe* @createArgsList( i32 %len, i32* %args, %eframe* %env, i32 %index){
%tst = icmp eq i32 0, %len
br i1 %tst, label %end, label %rec
rec:
%r1 = getelementptr i32* %args, i32 %index
%r2 = load i32* %r1
%r3 = getelementptr %eframe* %env, i32 0, i32 2, i32 %index
store i32 %r2, i32* %r3
%nexti = add i32 1, %index
%newlen = add i32 -1, %len
%r5 = call %eframe* @createArgsList( i32 %newlen, i32* %args, %eframe* %env, i32 %nexti )
ret %eframe* %r5
end:
ret %eframe* %env
}
declare void @type_check(i32, i32)
declare void @obj_type_check(i32, i32)
declare void @neg_float_check(i32)
define i32 @llvm_fun(){
%scomalreg0 = malloc {%eframe*, i32, [2 x i32]}
%scopereg0 = bitcast {%eframe*, i32, [2 x i32]}* %scomalreg0 to %eframe*
%r0 = add i32 0, 12
%ptrreg1 = getelementptr %eframe* %scopereg0, i32 0, i32 2, i32 0
store i32 %r0, i32* %ptrreg1
%r1 = add i32 0, 10
%pttrreg2 = getelementptr %eframe* %scopereg0, i32 0, i32 2, i32 0
%r2 = load i32* %pttrreg2
%ptrreg3 = getelementptr %eframe* %scopereg0, i32 0, i32 2, i32 1
store i32 %r2, i32* %ptrreg3
%r3 = add i32 0, 10
%r4 = add i32 0, 2
%r4 = add i32 0, 2
%tstreg9 = icmp eq i32 2, %r4
br i1 %tstreg9, label %then, label %else
then:
%pttrreg5 = getelementptr %eframe* %scopereg0, i32 0, i32 2, i32 0
%r5 = load i32* %pttrreg5
br label %end
else:
%pttrreg7 = getelementptr %eframe* %scopereg0, i32 0, i32 2, i32 1
%r7 = load i32* %pttrreg7
br label %end
end:
%r9 = phi i32 [%r5,%then], [%r7,%else]
ret i32 %r9
}