
function main(){
function even(x){
if ((x == 0)) {
return true;
} else {
return (odd((x - 1)));
}

}
function odd(x){
if ((x == 0)) {
return false;
} else {
return (even((x - 1)));
}

}
return ((odd(15)) && (even(14)));
}

print(main());

