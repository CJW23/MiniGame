/*
���� : �����
�� : 1���� JAVA(1��)
���α׷� �̸� : Pair.java
���α׷� ���� : DB�����͸� ������ ���׸� Ŭ����
*/
package rank;



public class Pair <T, F>{
	public T first;
	public F second;
	
	public Pair(final T f, final F s){			//�ڷ����� ���� �ʱ�ȭ
		this.first = f;		//id ����
		this.second = s;	//���� ����
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
