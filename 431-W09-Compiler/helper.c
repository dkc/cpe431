#include <stdio.h>
#include <string.h>
#include <stdlib.h>

extern unsigned int llvm_fun();

void type_check(unsigned int type, unsigned int test);
void obj_type_check(unsigned int type, unsigned int test);
void neg_float_check(unsigned int type);
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

int string_len(int str){
	//pointer check
	type_check(str, 1);
	//string check
	int stradd1 = str >> 2;
	int* oid1 = (int*) stradd1;
	obj_type_check(*oid1, 2);
	//get string pointers
	char* stringptr1;
	stringptr1 = (char*) oid1[2];
	//strlen
	return strlen(stringptr1);
}

int string_eq(int str1, int str2){
	//pointer check
	type_check(str1, 1);
	type_check(str2, 1);
	//string check
	int stradd1 = str1 >> 2;
	int* oid1 = (int*) stradd1;
	obj_type_check(*oid1, 2);
	int stradd2 = str2 >> 2;
	int* oid2 = (int*) stradd2;
	obj_type_check(*oid2, 2);
	//get string pointers
	char* stringptr1;
	stringptr1 = (char*) oid1[2];
	char* stringptr2;
	stringptr2 = (char*) oid2[2];
	//compare
	if(strcmp(stringptr1,stringptr2) == 0){
		return 7;
	}
	return 3;
}

char* string_append(int str1, int str2){
	//pointer check
	type_check(str1, 1);
	type_check(str2, 1);
	//string check
	int stradd1 = str1 >> 2;
	int* oid1 = (int*) stradd1;
	obj_type_check(*oid1, 2);
	int stradd2 = str2 >> 2;
	int* oid2 = (int*) stradd2;
	obj_type_check(*oid2, 2);
	//get string pointers
	char* stringptr1;
	stringptr1 = (char*) oid1[2];
	char* stringptr2;
	stringptr2 = (char*) oid2[2];
	//append
	int len1 = strlen(stringptr1);
	int len2 = strlen(stringptr2);
	char* newstr = malloc(sizeof(char)*(len1 + len2 + 1));
	char* newptr = newstr;
	strncpy(newptr,stringptr1,len1);
	newptr += len1;
	strcpy(newptr,stringptr2);
	//newptr[len1+len2] = '\0';
	return newstr;
}

int string_comp(int str1, int str2){
	//pointer check
	type_check(str1, 1);
	type_check(str2, 1);
	//string check
	int stradd1 = str1 >> 2;
	int* oid1 = (int*) stradd1;
	obj_type_check(*oid1, 2);
	int stradd2 = str2 >> 2;
	int* oid2 = (int*) stradd2;
	obj_type_check(*oid2, 2);
	//get string pointers
	char* stringptr1;
	stringptr1 = (char*) oid1[2];
	char* stringptr2;
	stringptr2 = (char*) oid2[2];
	//compare
	if(strcmp(stringptr1, stringptr2) < 0){
		return 7;
	}
	return 3;
}

char* string_sub(int str1, int start,int end){
	//pointer check
	type_check(str1, 1);
	//int checks
	type_check(start,0);
	type_check(end,0);
	//string check
	int straddr = str1 >> 2;
	int* oid1 = (int*) straddr;
	obj_type_check(*oid1, 2);
	//get string pointer
	char* stringptr1;
	stringptr1 = (char*) oid1[2];
	//get ints
	start = start >> 2;
	end = end >>2;
	//copy str
	int len = end - start;
	stringptr1 += start;
	char* newstr = malloc((len + 1) * sizeof(char));
	strncpy(newstr, stringptr1, len);
	newstr[len] = '\0';
	return newstr;
}

int instance_of(int obj, int func){
	//pointer check
	type_check(obj, 1);
	type_check(func, 1);
	//func check
	int funcaddr = func >> 2;
	int* oid1 = (int*) funcaddr;
	obj_type_check(*oid1, 1);
	//get slots on obj
	int objaddr = obj >> 2;
	int* oid2 = (int*) objaddr;
	neg_float_check(*oid2);
	oid2++;
	int* slotid2 = (int*) *oid2;
	int* fid2 = (int*) slotid2;
	fid2++;
	//get pointer to func

	if(*fid2 == func){
		return 7;
	}
	return 3;
}

int int_to_float(unsigned int ieee_float){
	//unsigned int signtst = ieee_float & 2147483648;//1 and 31 zeroes
	int signtst = 0;
	int sign = 1;
	if(!signtst){
		sign = -1;
	}
	int exp = ieee_float & 2139095040;//011111111000...
	int mant = ieee_float & 8388607;
	return ieee_float;
}

char* read_line(){
	char buffer[1000];
	fgets(buffer,1000,stdin);
	int len = strlen(buffer);
	len++;//for null byte
	char* newstr = malloc(len * sizeof(char));
	strcpy(newstr,buffer);
	return newstr;
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
		float floatval;
		float* floatptr;
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
			floatptr = (float*) oid;
			floatval = (float) floatptr[1];
			printf("#float object value: %f\n",floatval);
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

void print_fieldlookup_err(){
	fprintf(stderr,"Runtime Error FieldLookup: attempt to access non-existant field\n");
	exit(-1);
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

int instance_int_check(int type){
	int shfttype = type & 3;
	if(shfttype != 0){
		return 3;//3 for false
	}
	return 7;//7 for true in footle
}

int instance_bool_check(int type){
	if((type == 3) || (type == 7)){
		return 7;
	}
	return 3;
}

int instance_void_check(int type){
	if(type == 11){
		return 7;
	}
	return 3;
}

int instance_obj_check(unsigned int type, unsigned int test){
	unsigned int ftype = type & 3;
	if(ftype == 1){
		int typeaddr = type >> 2;
		int* oid = (int*) typeaddr;
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

void args_check(int args, int params){
	if(args != params){
		fprintf(stderr,"Runtime Error Function Call: # args != # params defined\n");
		exit(-1);
	}
}
