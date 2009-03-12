
function main(){
function f(x){
function g(y){
return (x + y);

}
return g;

}
var myClosure = (f(13));
return (myClosure(6));
}

print(main());

