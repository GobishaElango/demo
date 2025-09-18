

public class ExceptionExample {

	public ExceptionExample() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		/*try {
			int data=90/0;
		}catch(ArithmeticException e) {
			System.out.println(e);
		}
		System.out.println("rest of the code");
		
	}*/
		int i=50;
		int j=0;
		int data;
		try {
			data=i/j;
		}
		catch(Exception e)
		{
			throw new Exception(e);
		}
			System.out.println("rest of the code");
		}

}
