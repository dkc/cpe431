#include <stdio.h>
#include <string.h>
#include <stdlib.h>

extern unsigned int llvm_fun();

int main(){
	llvm_fun();
	return 0;
}

int test(int argc,char** argv){
	if(argc != 3){
		fprintf(stderr,"Error: wrong # args to test\n");
		exit(-1);
	}
	printf("*******************************\n");
	printf("Test: %s\n",argv[1]);
	printf("Expected Output: %s\n",argv[2]);
	unsigned int act = llvm_fun();
	unsigned int tag = act & 3;
	act = act >> 2;
	//dispatch_fun(act, &act);
	int passed = 0;
	if(tag == 0){//integer
		printf("Actual Output: %d\n",act);
		unsigned int tst = atoi(argv[2]);
		if(tst != act){
			passed = 1;//failed
		}
	}else if(tag == 1){//pointer
		int* oid = (int*) act;
		int otag = *oid & 3;
		switch(otag){
		case 0:
			printf("Actual Output: object\n");
			if(strcmp("object",argv[2])){
				passed = 1;
			}
			break;
		case 1:
			printf("Actual Output: closure\n");
			if(strcmp("closure",argv[2])){
				passed = 1;
			}
			break;
		case 2:
			printf("Actual Output: string\n");
			if(strcmp("string",argv[2])){
				passed = 1;
			}
			break;
		case 3:
			printf("Actual Output: float\n");
			if(strcmp("float",argv[2])){
				passed = 1;
			}
			break;
		default:
			passed = 1;
			break;
		}
	}else if(tag == 3){//bool/void
		switch(act){
		case 1:
			printf("Actual Output: true\n");
			if(strcmp("true",argv[2])){
				passed = 1;
			}
			break;
		case 0:
			printf("Actual Output: false\n");
			if(strcmp("false",argv[2])){
				passed = 1;
			}
			break;
		case 2:
			printf("Actual Output: void\n");
			if(strcmp("void",argv[2])){
				passed = 1;
			}
			break;
		}
	}else{
		fprintf(stderr,"Error: unexpected output from program\n");
		exit(-1);
	}

	if(passed == 0){
		printf("Test Passed\n");
	}else{
		printf("Test  XxXxX Failed XxXxX\n");
	}
	printf("*******************************\n");
	return 0;
}

int string_len(char* str){
	return strlen(str);
}

void footle_print(int val){
	int tag = val & 3;
	val = val >> 2;
	//printf("in print with val: %d\n",val);

	if(tag == 0){//integer
		printf("#%d\n",val);
	}else if(tag == 1){//pointer
		int* oid = (int*) val;
		int otag = *oid & 3;
		char* stringptr;
		switch(otag){
		case 0:
			printf("#plain object constructor fid: \n");
			break;
		case 1:
			printf("#closure object fid: %d\n",(*oid >> 2));
			break;
		case 2:
			stringptr = (char*) oid[2];
			printf("#string object value: %s\n",stringptr);
			break;
		case 3:
			printf("#float\n");
			break;
		default:
			break;
		}
	}else if(tag == 3){//bool/void
		switch(val){
		case 1:
			printf("#true\n");
			break;
		case 0:
			printf("#false\n");
			break;
		case 2:
			printf("#void\n");
			break;
		}
	}else{
		fprintf(stderr,"Error: non footle value passed to print\n");
		exit(-1);
	}
}

void type_check(unsigned int type, unsigned int test){
	unsigned int shfttype = type & 3;
	if(shfttype != test){
		switch(test){
		case 0: 	
			fprintf(stderr,"Runtime Error Type: Expected Integer, ");
			break;
		case 1: 	
			fprintf(stderr,"Runtime Error Type: Expected Pointer, ");
			break;
		case 3: 	
			fprintf(stderr,"Runtime Error Type: Expected Boolean/Void, ");
			break;
		}
		switch(shfttype){
		case 0: 	
			fprintf(stderr,"got Integer\n");
			break;
		case 1: 	
			fprintf(stderr,"got Pointer\n");
			break;
		case 3: 	
			fprintf(stderr,"got Boolean/Void\n");
			break;
		}
		exit(-1);
	}
}

int instance_int_check(unsigned int type){
	int shfttype = type & 3;
	if(shfttype != 0){
		return 3;//3 for false
	}
	return 7;//7 for true in footle
}

int instance_bool_check(unsigned int type){
	if((type == 3) || (type == 7)){
		return 7;
	}
	return 3;
}

int instance_void_check(unsigned int type){
	if(type == 11){
		return 7;
	}
	return 3;
}

int instance_obj_check(unsigned int type, unsigned int test){
	unsigned int ftype = type & 3;
	if(ftype == 1){
		int* oid = (int*) type;
		int otag = *oid & 3;
		if(otag == test){
			return 7;
		}
		return 3;
	}
	return 3;
}

void obj_type_check(unsigned int type, unsigned int test){
	unsigned int shfttype = type & 3;
	if(shfttype != test){
		switch(test){
		case 0: 	
			fprintf(stderr,"Runtime Error Type: Expected Object, ");
			break;
		case 1: 	
			fprintf(stderr,"Runtime Error Type: Expected Closure, ");
			break;
		case 2: 	
			fprintf(stderr,"Runtime Error Type: Expected String, ");
			break;
		case 3: 	
			fprintf(stderr,"Runtime Error Type: Expected Float, ");
			break;
		}
		switch(shfttype){
		case 0: 	
			fprintf(stderr,"got Object\n");
			break;
		case 1: 	
			fprintf(stderr,"got Closure\n");
			break;
		case 2: 	
			fprintf(stderr,"got String\n");
			break;
		case 3: 	
			fprintf(stderr,"got Float\n");
			break;
		}
		exit(-1);
	}
}


void neg_float_check(unsigned int type){
	unsigned int shfttype = type & 3;
	if(shfttype == 3){
		fprintf(stderr,"Runtime Error Type: Field lookup/mutation may not be performed on floats\n");
		exit(-1);
	}
}
