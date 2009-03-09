%field = type {i32, i32}
%slots = type {%field, %slots*}
@emptyslots = constant %slots undef
%eframe = type{%eframe*, i32, [0 x i32]}
%sobj = type{i32, %slots*, i8*}
%fobj = type{i32, float}
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
define i32 @dispatch_fun(%cobj* %clos, i32 %len, i32* %args, i32 %parent){
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
define i32 @while12( %eframe* %scopereg0 ){
%pttrreg2 = getelementptr %eframe* %scopereg0, i32 0, i32 2, i32 0
%r2 = load i32* %pttrreg2
%r3 = add i32 0, 400
%lrshft4 = lshr i32 %r2, 2
%rrshft4 = lshr i32 %r3, 2
%boolreg4 = icmp slt i32 %lrshft4, %rrshft4
%shftans4 = zext i1 %boolreg4 to i32
%shftreg4 = shl i32 %shftans4, 2
%r4 = add i32 2, %shftreg4
%testreg12 = icmp eq i32 6, %r4
br i1 %testreg12, label %cont, label %fin
cont:
%scomalreg12 = malloc {%eframe*, i32, [0 x i32]}, align 4
%scopereg12 = bitcast {%eframe*, i32, [0 x i32]}* %scomalreg12 to %eframe*
%envlinkptr12 = getelementptr %eframe* %scopereg12, i32 0, i32 0
store %eframe* %scopereg0, %eframe** %envlinkptr12
%eframeptr5 = getelementptr %eframe* %scopereg12, i32 0, i32 0
%scopereg0_5 = load %eframe** %eframeptr5
%pttrreg5 = getelementptr %eframe* %scopereg0_5, i32 0, i32 2, i32 0
%r5 = load i32* %pttrreg5
%r6 = add i32 0, 4
%lrshft7 = lshr i32 %r5, 2
%rrshft7 = lshr i32 %r6, 2
%shftans7 = mul i32 %lrshft7, %rrshft7
%shftreg7 = shl i32 %shftans7, 2
%r7 = add i32 0, %shftreg7
%r8 = add i32 0, 8
%lrshft9 = lshr i32 %r7, 2
%rrshft9 = lshr i32 %r8, 2
%shftans9 = add i32 %lrshft9, %rrshft9
%shftreg9 = shl i32 %shftans9, 2
%r9 = add i32 0, %shftreg9
%eframeptr10 = getelementptr %eframe* %scopereg12, i32 0, i32 0
%scopereg0_10 = load %eframe** %eframeptr10
%ptrreg10 = getelementptr %eframe* %scopereg0_10, i32 0, i32 2, i32 0
store i32 %r9, i32* %ptrreg10
%r10 = add i32 0, 10
%retreg12 = call i32 @while12( %eframe* %scopereg0)
ret i32 %retreg12
fin:
ret i32 10
}
define i32 @llvm_fun() {%scomalreg0 = malloc {%eframe*, i32, [1 x i32]}
%scopereg0 = bitcast {%eframe*, i32, [1 x i32]}* %scomalreg0 to %eframe*
%r0 = add i32 0, 0
%ptrreg1 = getelementptr %eframe* %scopereg0, i32 0, i32 2, i32 0
store i32 %r0, i32* %ptrreg1
%r1 = add i32 0, 10
call i32 @while12( %eframe* %scopereg0)
%r12 = add i32 0, 10
%pttrreg13 = getelementptr %eframe* %scopereg0, i32 0, i32 2, i32 0
%r13 = load i32* %pttrreg13
