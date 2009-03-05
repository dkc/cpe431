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
define i32 @footle_fun0(%eframe* %scopereg11){
%pttrreg0 = getelementptr %eframe* %scopereg11, i32 0, i32 2, i32 0
%r0 = load i32* %pttrreg0
%r1 = add i32 0, 0
%lrshft2 = lshr i32 %r0, 2
%rrshft2 = lshr i32 %r1, 2
%boolreg2 = icmp eq i32 %lrshft2, %rrshft2
%shftans2 = zext i1 %boolreg2 to i32
%r2 = shl i32 %shftans2, 2
%r2 = add i32 2, %r2
%tstreg9 = icmp eq i32 2, %r2
br i1 %tstreg9, label %then, label %else
then:
%r3 = add i32 0, 3
%r3 = add i32 0, 3
br label %end
else:
%r6 = add i32 0, 2
%r6 = add i32 0, 2
br label %end
end:
%r9 = phi i32 [%r4,%then], [%r7,%else]
ret i32 10
}
define i32 @llvm_fun(){
%scomalreg0 = malloc {%eframe*, i32, [2 x i32]}
%scopereg0 = bitcast {%eframe*, i32, [2 x i32]}* %scomalreg0 to %eframe*
%r-1 = add i32 0, 10
%pttrreg13 = getelementptr %eframe* %scopereg0, i32 0, i32 2, i32 0
%r13 = load i32* %pttrreg13
call void @type_check( i32 %r13, i32 1)
%shftreg14 = lshr i32 %r13, 2
%clos14 = inttoptr i32 %shftreg14 to %cobj*
%idslots14 = getelementptr %cobj* %clos14, i32 0, i32 0
%objid14 = load %slots** %idslots14
call void @obj_type_check( i32 %objid14, i32 1 )
%argptr14 = malloc [1 x i32], align 4
%argsreg14 = bitcast [1 x i32]* %argptr14 to i32*
%r12 = add i32 0, 16
%14argslistptr0 = getelementptr i32* %argsreg14, i32 0
store i32 %r12, i32* %14argslistptr0
%r14 = call i32 @dispatch_fun( %cobj* %clos14, i32 1, i32* %argsreg14 ) nounwind
%ptrreg15 = getelementptr %eframe* %scopereg0, i32 0, i32 2, i32 1
store i32 %r14, i32* %ptrreg15
%r15 = add i32 0, 10
%r17 = add i32 0, 20
%pttrreg16 = getelementptr %eframe* %scopereg0, i32 0, i32 2, i32 1
%r16 = load i32* %pttrreg16
call void @type_check( i32 %r16, i32 1 )
%shftreg18 = lshr i32 %r16, 2
%castreg18 = inttoptr i32 %shftreg18 to %pobj*
%idslots18 = getelementptr %pobj* %castreg18, i32 0, i32 0
%objid18 = load %slots** %idslots18
call void @neg_float_check( i32 %objid18 )
%slotptrsreg18 = getelementptr %pobj* %castreg18, i32 0, i32 1
%slotsreg18 = load %slots** %slotptrsreg18
%newslotreg18 = malloc %slots
%newsptr18 = getelementptr %slots* %newslotreg18, i32 0, i32 1
store %slots* %slotsreg18, %slots** %newsptr18
%fieldptr18 = getelementptr %slots* %newslotreg18, i32 0, i32 0
%fidptr18 = getelementptr %field*%fieldptr18, i32 0, i32 0
store i32 null, i32* %fidptr18
%valptr18 = getelementptr %field*%fieldptr18, i32 0, i32 1
store i32 %r17, i32* %valptr18
store %slots* %newslotreg18, %slots** %slotptrsreg18
%r18 = add i32 0, %r17
ret i32 %r18
}