
function main(){
function f(){
3;

}
var myobj = (new f());
(myobj).constructor = 13;
return (instanceof(myobj,f));
}

print(main());

