
function main(){
function add(){
return ((this).x + (this).y);

}
function G(y){
(this).x = 13;
(this).y = y;
(this).f = add;

}
var lg = (new G(19));
(lg).f();
}

print(main());

