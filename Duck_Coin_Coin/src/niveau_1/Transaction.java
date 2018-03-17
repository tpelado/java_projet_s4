package niveau_1;
import java.util.concurrent.ThreadLocalRandom;
public class Transaction
{
	private String transaction;

	/**
	 * @param transaction
	 */
	public Transaction()
	{
		int max = 100;
		this.transaction = "source-destination :"+ThreadLocalRandom.current().nextInt(0, max + 1);
	}
	
	
	public void afficherTransaction(Transaction trans)
	{
		System.out.println(trans.transaction);
	}
}
