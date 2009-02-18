target datalayout = "e-p:32:32:32-i1:8:8-i8:8:8-i16:16:16-i32:32:32-i64:32:64-f32:32:32-f64:32:64-v64:64:64-v128:128:128-a0:0:64-f80:32:32"
target triple = "i686-pc-linux-gnu"
@.str = internal constant [12 x i8] c"result: %d\0A\00"		; <[12 x i8]*> [#uses=1]

declare i32 @printf(i8* noalias , ...) nounwind 

define i32 @main(){
entry:
	%retval = alloca i32		; <i32*> [#uses=2]
	%tmp = alloca i32		; <i32*> [#uses=2]
	%"alloca point" = bitcast i32 0 to i32		; <i32> [#uses=0]
	%tmp1 = getelementptr [12 x i8]* @.str, i32 0, i32 0		; <i8*> [#uses=1]
	%tmp2 = call i32 (i8*, ...)* @printf( i8* noalias  %tmp1, i32 6 ) nounwind 		; <i32> [#uses=0]
	store i32 0, i32* %tmp, align 4
	%tmp3 = load i32* %tmp, align 4		; <i32> [#uses=1]
	store i32 %tmp3, i32* %retval, align 4
	br label %return

return:		; preds = %entry
	%retval4 = load i32* %retval		; <i32> [#uses=1]
	ret i32 %retval4
}

define i32 @llvm_fun(){
	%r1 = add i32 0, 6
	%r2 = call i32 @ifexp(i32 0)
	ret i32 %r2
}

define i32 @add(i32 %x,i32 %y){
	%sum = add i32 %x, %y
	ret i32 %sum
}

define i32 @sub(i32 %x,i32 %y){
	%sum = sub i32 %x, %y
	ret i32 %sum
}

define i32 @mult(i32 %x,i32 %y){
	%sum = mul i32 %x, %y
	ret i32 %sum
}

define i32 @div(i32 %x,i32 %y){
	%sum = udiv i32 %x, %y
	ret i32 %sum
}

define i32 @and(i32 %x,i32 %y){
	%sum = and i32 %x, %y
	ret i32 %sum
}

define i32 @or(i32 %x,i32 %y){
	%sum = or i32 %x, %y
	ret i32 %sum
}

define i32 @not(i32 %x){
	%sum = xor i32 %x, 1
	ret i32 %sum
}

define i1 @grt(i32 %x,i32 %y){
	%sum = icmp ugt i32 %x, %y
	ret i1 %sum
}

define i1 @lst(i32 %x,i32 %y){
	%sum = icmp ult i32 %x, %y
	ret i1 %sum
}

define i1 @gtq(i32 %x,i32 %y){
	%sum = icmp uge i32 %x, %y
	ret i1 %sum
}

define i1 @ltq(i32 %x,i32 %y){
	%retval = icmp ule i32 %x, %y
	ret i1 %retval
}

define i32 @ifexp(i32 %a){
test:
	%tst = icmp eq i32 0, %a
	br i1 %tst, label %then, label %else

then:
	%r1 = add i32 0, 4
	br label %end

else:
	%r2 = add i32 0, 5
	br label %end

end:
	%retval = phi i32 [%r1,%then], [%r2,%else]
	ret i32 %retval
}
