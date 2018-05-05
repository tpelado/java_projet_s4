package niveau1;
import java.util.concurrent.ThreadLocalRandom; // Utilisé pour générer des nombres pseudos aléatoires 
/**
 * 
 * @author Tanguy
 *
 */
public class Transaction
{
	private String transaction;

	/**
	 * constructeur de l'objet Transaction
	 *
	 */
	public Transaction() // si on ne lui donne rien, alors génère une transaction aléatoire
	{
		int max = 100;
		this.transaction = "source-destination :"+ThreadLocalRandom.current().nextInt(0, max + 1);
	}
	/**
	 * constructeur alternatif de l'objet Transaction
	 * @param texte : texte contenu dans la transaction 
	 */
	public Transaction(String texte) // sinon génère une transaction qui possède comme attribut le string passé
	{
		this.transaction = texte;
	}
	
	public static Transaction[] genererListeTransaction(int nbrTransaction)
	{
		Transaction[] tab = new Transaction[nbrTransaction];
		int i=0;
		for(;i<nbrTransaction;i++)
		{
			tab[i] = new Transaction();
		}
		return tab;
	}
	/* affiche une transaction */ 
	public void afficherTransaction()
	{
		System.out.println(this.transaction);
	}
	/* getter de transaction. vu qu'on ne set jamais de transaction ailleurs dans le programme, un setter n'est pas utile */ 
	public String getTransaction()
	{
		return this.transaction;
	}
}
