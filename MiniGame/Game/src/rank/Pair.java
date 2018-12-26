/*
구현 : 최재우
조 : 1등을 JAVA(1조)
프로그램 이름 : Pair.java
프로그램 설명 : DB데이터를 저장할 제네릭 클래스
*/
package rank;



public class Pair <T, F>{
	public T first;
	public F second;
	
	public Pair(final T f, final F s){			//자료형에 따른 초기화
		this.first = f;		//id 저장
		this.second = s;	//점수 저장
	}

	public T getFirst() {
		return first;
	}

	public void setFirst(T first) {
		this.first = first;
	}

	public F getSecond() {
		return second;
	}

	public void setSecond(F second) {
		this.second = second;
	}

}
