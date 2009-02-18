define i32 @llvm_fun(){

%eframe = type{%eframe*, i32, [ 0 x i32 ]}
%scoperegr-1 = malloc { %eframe*, i32, [ 0 x i32 ] }
%r0 = add i32 0, 2
%tst = icmp eq i32 0, %r0
br i1 %tst, label %then, label %else

then:
%r1 = add i32 0, 4
br label %end

else:
%r2 = add i32 0, 8
br label %end

end:
%r3 = phi i32 [%r1,%then], [%r2,%else]
ret i32 %r3
