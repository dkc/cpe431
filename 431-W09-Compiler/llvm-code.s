%eframe = type{%eframe*, i32, [ 0 x i32 ]}
define i32 @llvm_fun(){
%scopereg0 = malloc { %eframe*, i32, [ 2 x i32 ] }
%r0 = add i32 0, 4
%ptrreg1 = getelementptr %eframe* %scopereg0, i32 2, i32 0
store i32 %r0, i32* %ptrreg1
%r1 = add i32 0, %r0
%r2 = add i32 0, 8
%ptrreg3 = getelementptr %eframe* %scopereg0, i32 2, i32 1
store i32 %r2, i32* %ptrreg3
%r3 = add i32 0, %r2
%r4 = add i32 0, 2
%tstreg9 = icmp eq i32 2, %r4
br i1 %tstreg9, label %then, label %else
then:
%eframeptr1 = getelementptr %eframe* %scopereg1,i32 1
%scopereg1 = load i32* %eframeptr1
%pttrreg5 = getelementptr %eframe* %scopereg0, i32 2, i32 0
%r5 = load i32* %pttrreg5
%r6 = add i32 0, %r5
br label %end
else:
%eframeptr1 = getelementptr %eframe* %scopereg1,i32 1
%scopereg1 = load i32* %eframeptr1
%pttrreg7 = getelementptr %eframe* %scopereg0, i32 2, i32 1
%r7 = load i32* %pttrreg7
%r8 = add i32 0, %r7
br label %end
end:
%r9 = phi i32 [%r6,%then], [%r8,%else]
%r10 = add i32 0, %r9
ret i32 %r10
}