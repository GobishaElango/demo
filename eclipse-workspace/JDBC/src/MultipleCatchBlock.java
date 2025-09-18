

public class MultipleCatchBlock {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			int a[]=new int[5];
			a[5]=30/0;
			/*String s=null;
			System.out.println(s.length());*/
		}
		
		catch(ArithmeticException e) {
			System.out.println("task1 is completed");
		}
		catch(ArrayIndexOutOfBoundsException e) {
			System.out.println("task2 completed");
		}
		
		/*catch(ArithmeticException e)
		{
			System.out.println("Arithmetic Exception occurs");
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			System.out.println("ArrayOutOfBounds Exception occurs");
			
		}
		catch(Exception e) {
			System.out.println("Parent Exception occurs ");
		}*/
		System.out.println("rest of the code");
	}

}
