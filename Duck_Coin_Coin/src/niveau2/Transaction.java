package niveau2;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.concurrent.ThreadLocalRandom; // Utilis� pour g�n�rer des nombres pseudos al�atoires 

import org.bitcoinj.core.Address;
import org.bitcoinj.core.ECKey;

public class Transaction
{
	private int index;
	private String timestamp;
	private String emetteur;
	private String destinataire;
	private int montant;
	private String signature;

	/**
	 * @param index
	 * @param timestamp
	 * @param �metteur
	 * @param destinataire
	 * @param montant
	 * @param signature_em
	 */
	public Transaction(int index, String emetteur, String destinataire, int montant, String signature)
	{
		this.index = index;
		final SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss a");
		Timestamp test = new Timestamp(System.currentTimeMillis());
		this.timestamp = sdf.format(test);
		this.emetteur = emetteur;
		this.destinataire = destinataire;
		this.montant = montant;
		this.signature = signature;
	}

	/**
	 * sert � la g�n�ratinon block du genesis pour une transaction de niveau 2
	 * 
	 * @return une transation sp�ciale g�n�sis
	 */
	static public Transaction genererGenesis()
	{
		Transaction tempo = new Transaction(0, "Genesis", "Genesis", 0, "Genesis");
		return tempo;
	}

	/**
	 * g�n�re une liste de transaction de niveau 2
	 * 
	 * @param nbrTransaction
	 *            le nombre de transactions � g�r�rer
	 * @return un tableau de transactions
	 */
	static public Transaction[] genererListeTransaction(int nbrTransaction)
	{
		int i = 0;
		int j = 0;
		int max = 50; // valeur hasardeuse sur le montant maximal d'une transaction
		Transaction[] tabTransaction = new Transaction[nbrTransaction];
		ECKey[] tabKey = niveau2.CreateAddress.genererNKey(nbrTransaction * 2); // 2 cl�s par transaction
		Address[] tabAddress = niveau2.CreateAddress.keyToAdresse(tabKey); // g�n�re le tableau d'adresses correspondant au tableau de cl�
		for (i = 0; i < nbrTransaction; i++)
		{
			int montant = ThreadLocalRandom.current().nextInt(0, max + 1);
			Address emetteur = tabAddress[j];
			Address destinataire = tabAddress[j + 1];
			Transaction tempo = new Transaction(i, emetteur.toString(), destinataire.toString(), montant, "");
			String concat = tempo.getTimestamp() + tempo.getEmetteur() + tempo.getDestinataire() + tempo.getMontant();
			concat = tabKey[i].signMessage(concat);
			tempo.setSignature(concat);
			tabTransaction[i] = tempo;
			j += 2;
		}
		return tabTransaction;
	}

	/**
	 * @deprecated pas utilis� ici
	 */
	public void afficherTransaction()
	{
		System.out.println("------Transaction n� " + this.index + "------");
		System.out.println("\tIndex : " + this.getIndex());
		System.out.println("\tTimestamp : " + this.getTimestamp());
		System.out.println("\tAdresse Emetteur : " + this.getEmetteur());
		System.out.println("\tAdresse Destinataire : " + this.getDestinataire());
		System.out.println("\tMontant : " + this.getMontant());
		System.out.println("\tSignature : " + this.getSignature());

	}

	/**
	 * g�n�re une concat�nation de la transaction pour la signature
	 * 
	 * @return un string de la transaction concat�n�e
	 */
	public String getStringTransaction()
	{
		return this.getIndex() + this.getTimestamp() + this.getEmetteur() + this.getDestinataire() + this.getMontant() + this.getSignature();
	}

	/**
	 * retourne une concat�nation sp�ciale pour la v�rification du block g�n�sis
	 * 
	 * @return
	 */
	public String getVerifGenesis()
	{
		return this.getEmetteur() + this.getDestinataire() + this.getSignature();
	}

	/*** ------ getters && setters ------- ***/
	/**
	 * @return the emetteur
	 */
	private String getEmetteur()
	{
		return emetteur;
	}

	

	/**
	 * @return the destinataire
	 */
	private String getDestinataire()
	{
		return destinataire;
	}



	/**
	 * @return the montant
	 */
	private int getMontant()
	{
		return montant;
	}

	
	/**
	 * @return the index
	 */
	private int getIndex()
	{
		return index;
	}


	/**
	 * @return the signature
	 */
	private String getSignature()
	{
		return signature;
	}

	private String getTimestamp()
	{
		return this.timestamp;
	}

	private void setSignature(String sign)
	{
		this.signature = sign;
	}

}
