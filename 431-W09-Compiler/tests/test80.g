
function main(){
function f(){
3;

}
var myobj = "abc";
(myobj).constructor = f;
return (instanceof(myobj,f));
}

print(main());

