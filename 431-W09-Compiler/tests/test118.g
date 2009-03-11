
function main(){
function MT(){

}
function Cons(a,b){
(this).fst = a;
(this).rst = b;

}
function GSPair(getter,setter){
(this).getter = getter;
(this).setter = setter;

}
function empty?(a){
return (instanceof(a,MT));

}
function foldl(f,i,l){
if ((empty?(l))) {
return i;
} else {
return (foldl(f,(f((l).fst,i)),(l).rst));
}

}
function map(f,l){
if ((empty?(l))) {
return (new MT());
} else {
return (new Cons((f((l).fst)),(map(f,(l).rst))));
}

}
function length(l){
function add1(dc,n){
return (n + 1);

}
return (foldl(add1,0,l));

}
function addAllFuns(lst){
function h(p,n){
return ((method-call(p,getter)) + n);

}
return (foldl(h,0,lst));

}
function numToFun(n){
function dc(){
return n;

}
return dc;

}
function setEachToDifferentValue(lst,n){
if ((empty?(lst))) {
return 13;
} else {
((lst).fst).setter(n);
(setEachToDifferentValue((lst).rst,(n * 2)));
}

}
var funtbl = (new MT());
var x = 0;
function g(){
return x;

}
function h(newX){
x = newX;
return 4;

}
funtbl = (new Cons((new GSPair(g,h)),funtbl));
var x = 0;
function g(){
return x;

}
function h(newX){
x = newX;
return 4;

}
funtbl = (new Cons((new GSPair(g,h)),funtbl));
function f(){
var x = 0;
function g(){
return x;

}
function h(newX){
x = newX;
return 4;

}
funtbl = (new Cons((new GSPair(g,h)),funtbl));
function f1(){
var x = 0;
function g(){
return x;

}
function h(newX){
x = newX;
return 4;

}
funtbl = (new Cons((new GSPair(g,h)),funtbl));

}
(f1());
(f1());

}
(f());
(f());
(setEachToDifferentValue(funtbl,1));
return (addAllFuns(funtbl));
}

print(main());

