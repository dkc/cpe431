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
switch i32 %fid, label %default [ i32 0, label %funccall0  i32 1, label %funccall1 ]
funccall0:
%funcenv = call %eframe* @createArgsList ( i32 %len, i32* %args, %eframe* %envframe, i32 0)
%ftest0 = icmp eq i32 %parent, 0
br i1 %ftest0,  label %ffun0, label %fmet0
ffun0:
%rfun0 = call i32 @footle_fun0 ( %eframe* %envframe )
ret i32 %rfun0
fmet0:
%rmet0 = call i32 @footle_met0 ( %eframe* %envframe, i32 %parent )
ret i32 %rmet0
funccall1:
%funcenv = call %eframe* @createArgsList ( i32 %len, i32* %args, %eframe* %envframe, i32 0)
%ftest1 = icmp eq i32 %parent, 0
br i1 %ftest1,  label %ffun1, label %fmet1
ffun1:
%rfun1 = call i32 @footle_fun1 ( %eframe* %envframe )
ret i32 %rfun1
fmet1:
%rmet1 = call i32 @footle_met1 ( %eframe* %envframe, i32 %parent )
ret i32 %rmet1
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
define i32 @footle_fun0(%eframe* %scopereg25){
%pttrreg10 = getelementptr %eframe* %scopereg25, i32 0, i32 2, i32 0
%r10 = load i32* %pttrreg10
%r11 = add i32 0, 0
%lrshft12 = lshr i32 %r10, 2
%rrshft12 = lshr i32 %r11, 2
%boolreg12 = icmp eq i32 %lrshft12, %rrshft12
%shftans12 = zext i1 %boolreg12 to i32
%r12 = shl i32 %shftans12, 2
%r12 = add i32 2, %r12
%tstreg23 = icmp eq i32 2, %r12
br i1 %tstreg23, label %then, label %else
then:
%r13 = add i32 0, 3
ret i32 %r13
br label %end
else:
%pttrreg19 = getelementptr %eframe* %scopereg0, i32 0, i32 2, i32 1
%r19 = load i32* %pttrreg19
call void @type_check( i32 %r19, i32 1)
%shftreg20 = lshr i32 %r19, 2
%clos20 = inttoptr i32 %shftreg20 to %cobj*
%idslots20 = getelementptr %cobj* %clos20, i32 0, i32 0
%objid20 = load i32* %idslots20
call void @obj_type_check( i32 %objid20, i32 1 )
%argptr20 = malloc [1 x i32], align 4
%argsreg20 = bitcast [1 x i32]* %argptr20 to i32*
%pttrreg16 = getelementptr %eframe* %scopereg25, i32 0, i32 2, i32 0
%r16 = load i32* %pttrreg16
%r17 = add i32 0, 4
%lrshft18 = lshr i32 %r16, 2
%rrshft18 = lshr i32 %r17, 2
%shftans18 = sub i32 %lrshft18, %rrshft18
%r18 = shl i32 %shftans18, 2
%alist20p0 = getelementptr i32* %argsreg20, i32 0
store i32 %r18, i32* %alist20p0
%r20 = call i32 @dispatch_fun( %cobj* %clos20, i32 1, i32* %argsreg20, i32 0 ) nounwind
ret i32 %r20
br label %end
end:
%r23 = phi i32 [%r14,%then], [%r21,%else]
ret i32 10
}
define i32 @footle_met0(%eframe* %scopereg25, i32 %this){
%pttrreg10 = getelementptr %eframe* %scopereg25, i32 0, i32 2, i32 0
%r10 = load i32* %pttrreg10
%r11 = add i32 0, 0
%lrshft12 = lshr i32 %r10, 2
%rrshft12 = lshr i32 %r11, 2
%boolreg12 = icmp eq i32 %lrshft12, %rrshft12
%shftans12 = zext i1 %boolreg12 to i32
%r12 = shl i32 %shftans12, 2
%r12 = add i32 2, %r12
%tstreg23 = icmp eq i32 2, %r12
br i1 %tstreg23, label %then, label %else
then:
%r13 = add i32 0, 3
ret i32 %r13
br label %end
else:
%pttrreg19 = getelementptr %eframe* %scopereg0, i32 0, i32 2, i32 1
%r19 = load i32* %pttrreg19
call void @type_check( i32 %r19, i32 1)
%shftreg20 = lshr i32 %r19, 2
%clos20 = inttoptr i32 %shftreg20 to %cobj*
%idslots20 = getelementptr %cobj* %clos20, i32 0, i32 0
%objid20 = load i32* %idslots20
call void @obj_type_check( i32 %objid20, i32 1 )
%argptr20 = malloc [1 x i32], align 4
%argsreg20 = bitcast [1 x i32]* %argptr20 to i32*
%pttrreg16 = getelementptr %eframe* %scopereg25, i32 0, i32 2, i32 0
%r16 = load i32* %pttrreg16
%r17 = add i32 0, 4
%lrshft18 = lshr i32 %r16, 2
%rrshft18 = lshr i32 %r17, 2
%shftans18 = sub i32 %lrshft18, %rrshft18
%r18 = shl i32 %shftans18, 2
%alist20p0 = getelementptr i32* %argsreg20, i32 0
store i32 %r18, i32* %alist20p0
%r20 = call i32 @dispatch_fun( %cobj* %clos20, i32 1, i32* %argsreg20, i32 0 ) nounwind
ret i32 %r20
br label %end
end:
%r23 = phi i32 [%r14,%then], [%r21,%else]
ret i32 10
}
define i32 @footle_fun1(%eframe* %scopereg41){
%pttrreg26 = getelementptr %eframe* %scopereg41, i32 0, i32 2, i32 0
%r26 = load i32* %pttrreg26
%r27 = add i32 0, 0
%lrshft28 = lshr i32 %r26, 2
%rrshft28 = lshr i32 %r27, 2
%boolreg28 = icmp eq i32 %lrshft28, %rrshft28
%shftans28 = zext i1 %boolreg28 to i32
%r28 = shl i32 %shftans28, 2
%r28 = add i32 2, %r28
%tstreg39 = icmp eq i32 2, %r28
br i1 %tstreg39, label %then, label %else
then:
%r29 = add i32 0, 2
ret i32 %r29
br label %end
else:
%pttrreg35 = getelementptr %eframe* %scopereg0, i32 0, i32 2, i32 0
%r35 = load i32* %pttrreg35
call void @type_check( i32 %r35, i32 1)
%shftreg36 = lshr i32 %r35, 2
%clos36 = inttoptr i32 %shftreg36 to %cobj*
%idslots36 = getelementptr %cobj* %clos36, i32 0, i32 0
%objid36 = load i32* %idslots36
call void @obj_type_check( i32 %objid36, i32 1 )
%argptr36 = malloc [1 x i32], align 4
%argsreg36 = bitcast [1 x i32]* %argptr36 to i32*
%pttrreg32 = getelementptr %eframe* %scopereg41, i32 0, i32 2, i32 0
%r32 = load i32* %pttrreg32
%r33 = add i32 0, 4
%lrshft34 = lshr i32 %r32, 2
%rrshft34 = lshr i32 %r33, 2
%shftans34 = sub i32 %lrshft34, %rrshft34
%r34 = shl i32 %shftans34, 2
%alist36p0 = getelementptr i32* %argsreg36, i32 0
store i32 %r34, i32* %alist36p0
%r36 = call i32 @dispatch_fun( %cobj* %clos36, i32 1, i32* %argsreg36, i32 0 ) nounwind
ret i32 %r36
br label %end
end:
%r39 = phi i32 [%r30,%then], [%r37,%else]
ret i32 10
}
define i32 @footle_met1(%eframe* %scopereg41, i32 %this){
%pttrreg26 = getelementptr %eframe* %scopereg41, i32 0, i32 2, i32 0
%r26 = load i32* %pttrreg26
%r27 = add i32 0, 0
%lrshft28 = lshr i32 %r26, 2
%rrshft28 = lshr i32 %r27, 2
%boolreg28 = icmp eq i32 %lrshft28, %rrshft28
%shftans28 = zext i1 %boolreg28 to i32
%r28 = shl i32 %shftans28, 2
%r28 = add i32 2, %r28
%tstreg39 = icmp eq i32 2, %r28
br i1 %tstreg39, label %then, label %else
then:
%r29 = add i32 0, 2
ret i32 %r29
br label %end
else:
%pttrreg35 = getelementptr %eframe* %scopereg0, i32 0, i32 2, i32 0
%r35 = load i32* %pttrreg35
call void @type_check( i32 %r35, i32 1)
%shftreg36 = lshr i32 %r35, 2
%clos36 = inttoptr i32 %shftreg36 to %cobj*
%idslots36 = getelementptr %cobj* %clos36, i32 0, i32 0
%objid36 = load i32* %idslots36
call void @obj_type_check( i32 %objid36, i32 1 )
%argptr36 = malloc [1 x i32], align 4
%argsreg36 = bitcast [1 x i32]* %argptr36 to i32*
%pttrreg32 = getelementptr %eframe* %scopereg41, i32 0, i32 2, i32 0
%r32 = load i32* %pttrreg32
%r33 = add i32 0, 4
%lrshft34 = lshr i32 %r32, 2
%rrshft34 = lshr i32 %r33, 2
%shftans34 = sub i32 %lrshft34, %rrshft34
%r34 = shl i32 %shftans34, 2
%alist36p0 = getelementptr i32* %argsreg36, i32 0
store i32 %r34, i32* %alist36p0
%r36 = call i32 @dispatch_fun( %cobj* %clos36, i32 1, i32* %argsreg36, i32 0 ) nounwind
ret i32 %r36
br label %end
end:
%r39 = phi i32 [%r30,%then], [%r37,%else]
ret i32 10
}
define i32 @llvm_fun(){
%scomalreg0 = malloc {%eframe*, i32, [3 x i32]}
%scopereg0 = bitcast {%eframe*, i32, [3 x i32]}* %scomalreg0 to %eframe*
%r0 = add i32 0, 4
%tstreg4 = icmp eq i32 2, %r0
br i1 %tstreg4, label %then, label %else
then:
%r1 = add i32 0, 8
br label %end
else:
br label %end
end:
%r4 = phi i32 [%r1,%then], [%r3,%else]
%r5 = add i32 0, 8
%r6 = add i32 0, 16
%lrshft7 = lshr i32 %r5, 2
%rrshft7 = lshr i32 %r6, 2
%shftans7 = mul i32 %lrshft7, %rrshft7
%r7 = shl i32 %shftans7, 2
%r8 = add i32 0, 20
%lrshft9 = lshr i32 %r7, 2
%rrshft9 = lshr i32 %r8, 2
%shftans9 = mul i32 %lrshft9, %rrshft9
%r9 = shl i32 %shftans9, 2
%fmalreg25 = malloc %cobj, align 4
%scomalreg25 = malloc {%eframe*, i32, [1 x i32]}, align 4
%scopereg25 = bitcast {%eframe*, i32, [1 x i32]}* %scomalreg25 to %eframe*
%envlkptr25 = getelementptr %eframe* %scopereg25, i32 0, i32 0
store %eframe* %scopereg0, %eframe** %envlkptr25
%typeptr25 = getelementptr %cobj* %fmalreg25, i32 0, i32 0
store i32 1, i32* %typeptr25
%efreg25 = getelementptr %cobj* %fmalreg25, i32 0, i32 2
store %eframe* %scopereg25, %eframe** %efreg25
%objreg25 = ptrtoint %cobj* %fmalreg25 to i32
%memaddreg25 = shl i32 %objreg25, 2
%r25 = add i32 1, %memaddreg25
%ptrreg25 = getelementptr %eframe* %scopereg0, i32 0, i32 2, i32 0
store i32 %r25, i32* %ptrreg25
%fmalreg41 = malloc %cobj, align 4
%scomalreg41 = malloc {%eframe*, i32, [1 x i32]}, align 4
%scopereg41 = bitcast {%eframe*, i32, [1 x i32]}* %scomalreg41 to %eframe*
%envlkptr41 = getelementptr %eframe* %scopereg41, i32 0, i32 0
store %eframe* %scopereg0, %eframe** %envlkptr41
%typeptr41 = getelementptr %cobj* %fmalreg41, i32 0, i32 0
store i32 5, i32* %typeptr41
%efreg41 = getelementptr %cobj* %fmalreg41, i32 0, i32 2
store %eframe* %scopereg41, %eframe** %efreg41
%objreg41 = ptrtoint %cobj* %fmalreg41 to i32
%memaddreg41 = shl i32 %objreg41, 2
%r41 = add i32 1, %memaddreg41
%ptrreg41 = getelementptr %eframe* %scopereg0, i32 0, i32 2, i32 1
store i32 %r41, i32* %ptrreg41
%r42 = add i32 0, 10
%r43 = add i32 0, 4
%ptrreg44 = getelementptr %eframe* %scopereg0, i32 0, i32 2, i32 2
store i32 %r43, i32* %ptrreg44
%r44 = add i32 0, 10
%r46 = add i32 0, 20
%pttrreg45 = getelementptr %eframe* %scopereg0, i32 0, i32 2, i32 2
%r45 = load i32* %pttrreg45
call void @type_check( i32 %r45, i32 1 )
%shftreg47 = lshr i32 %r45, 2
%castreg47 = inttoptr i32 %shftreg47 to %pobj*
%idslots47 = getelementptr %pobj* %castreg47, i32 0, i32 0
%objid47 = load %slots** %idslots47
call void @neg_float_check( i32 %objid47 )
%slotptrsreg47 = getelementptr %pobj* %castreg47, i32 0, i32 1
%slotsreg47 = load %slots** %slotptrsreg47
%newslotreg47 = malloc %slots
%newsptr47 = getelementptr %slots* %newslotreg47, i32 0, i32 1
store %slots* %slotsreg47, %slots** %newsptr47
%fieldptr47 = getelementptr %slots* %newslotreg47, i32 0, i32 0
%fidptr47 = getelementptr %field*%fieldptr47, i32 0, i32 0
store i32 null, i32* %fidptr47
%valptr47 = getelementptr %field*%fieldptr47, i32 0, i32 1
store i32 %r46, i32* %valptr47
store %slots* %newslotreg47, %slots** %slotptrsreg47
%r47 = add i32 0, %r46
%r48 = add i32 0, 12
%r49 = add i32 0, 16
%lrshft50 = lshr i32 %r48, 2
%rrshft50 = lshr i32 %r49, 2
%shftans50 = add i32 %lrshft50, %rrshft50
%r50 = shl i32 %shftans50, 2
%pttrreg51 = getelementptr %eframe* %scopereg0, i32 0, i32 2, i32 2
%r51 = load i32* %pttrreg51
call void @type_check( i32 %r51, i32 1 )
%shftreg52 = lshr i32 %r51, 2
%castreg52 = inttoptr i32 %shftreg52 to %pobj*
%idslots52 = getelementptr %pobj* %castreg52, i32 0, i32 0
%objid52 = load %slots** %idslots52
call void @neg_float_check( i32 %objid52 )
%slotptrsreg52 = getelementptr %pobj* %castreg52, i32 0, i32 1
%slotsreg52 = load %slots** %slotptrsreg52
%lookupreg52 = call i32* @lookup_field( i32 47, %slots* %slotsreg52
%r52 = load i32* %lookupreg52
UNARYOPERATION PLACEHOLDER: %r53 gets the result of not %r52
%r54 = add i32 0, 24
%lrshft55 = lshr i32 %r53, 2
%rrshft55 = lshr i32 %r54, 2
%shftans55 = mul i32 %lrshft55, %rrshft55
%r55 = shl i32 %shftans55, 2
%lrshft56 = lshr i32 %r50, 2
%rrshft56 = lshr i32 %r55, 2
%shftans56 = udiv i32 %lrshft56, %rrshft56
%r56 = shl i32 %shftans56, 2
%r57 = add i32 0, 20
%r58 = add i32 0, 24
%lrshft59 = lshr i32 %r57, 2
%rrshft59 = lshr i32 %r58, 2
%shftans59 = add i32 %lrshft59, %rrshft59
%r59 = shl i32 %shftans59, 2
%r60 = add i32 0, 28
%lrshft61 = lshr i32 %r59, 2
%rrshft61 = lshr i32 %r60, 2
%shftans61 = add i32 %lrshft61, %rrshft61
%r61 = shl i32 %shftans61, 2
%r62 = add i32 0, 2
%lrshft63 = lshr i32 %r61, 2
%rrshft63 = lshr i32 %r62, 2
%r63 = shl i32 %shftans63, 2
%r64 = add i32 0, 3
%lrshft65 = lshr i32 %r63, 2
%rrshft65 = lshr i32 %r64, 2
%r65 = shl i32 %shftans65, 2
%lrshft66 = lshr i32 %r56, 2
%rrshft66 = lshr i32 %r65, 2
%boolreg66 = icmp eq i32 %lrshft66, %rrshft66
%shftans66 = zext i1 %boolreg66 to i32
%r66 = shl i32 %shftans66, 2
%r66 = add i32 2, %r66
ret i32 %r66
}