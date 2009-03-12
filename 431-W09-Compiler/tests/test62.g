
function main(){
function G(){
(this).x = 13;

}
function dummy(){
return 3;

}
(dummy).m = G;
(dummy).m();
return (dummy).x;
}

print(main());

