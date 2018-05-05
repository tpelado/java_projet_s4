package niveau2;

import java.sql.Timestamp;
import java.util.concurrent.ThreadLocalRandom; // Utilisé pour générer des nombres pseudos aléatoires 
import org.bitcoinj.core.Address;
import org.bitcoinj.core.ECKey;

import com.google.protobuf.Message;


public class Transaction
{
	private int index;
	private Timestamp timestamp;
	private String emetteur; 
	private String destinataire;
	private int montant;
	private String signature;

	/**
	 * @param index
	 * @param timestamp
	 * @param émetteur
	 * @param destinataire
	 * @param montant
	 * @param signature_em
	 */
	public Transaction(int index, String emetteur, String destinataire, int montant, String signature)
	{
		this.index = index;
		this.timestamp = new Timestamp(System.currentTimeMillis());
		this.emetteur = emetteur;
		this.destinataire = destinataire;
		this.montant = montant;
		this.signature = signature;
	}
	static public Transaction genererGenesis()
	{
		Transaction tempo = new Transaction(0,"Genesis","Genesis",0,"Genesis");
		return tempo;
	}
	
	static public Transaction[] genererListeTransaction(int nbrTransaction)
	{
		int i=0;
		int j=0;
		int max = 50; //valeur hasardeuse sur le montant maximal d'une transaction
		Transaction[] tabTransaction = new Transaction[nbrTransaction];
		ECKey[] tabKey = niveau2.CreateAddress.genererNKey(nbrTransaction*2); // 2 clés par transaction 
		Address[] tabAddress = niveau2.CreateAddress.keyToAdresse(tabKey); // génère le tableau d'adresses correspondant au tableau de clé 
		for(i=0;i<nbrTransaction;i++)	
		{
			int montant = ThreadLocalRandom.current().nextInt(0, max + 1);
			Address emetteur = tabAddress[j];
			Address destinataire = tabAddress[j+1];
			Transaction tempo = new Transaction(i,emetteur.toString(),destinataire.toString(),montant,"");
			String concat = tempo.getTimestamp()+tempo.getEmetteur()+tempo.getDestinataire()+tempo.getMontant();
			concat = tabKey[i].signMessage(concat);
			tempo.setSignature(concat);
			tabTransaction[i] = tempo;
			j += 2;
		}
		return tabTransaction;
	}
	public void afficherTransaction()
	{
		System.out.println("------Transaction n° "+this.index+"------");
		System.out.println("\tIndex : "+this.getIndex());
		System.out.println("\tTimestamp : "+this.getTimestamp());
		System.out.println("\tAdresse Emetteur : "+this.getEmetteur());
		System.out.println("\tAdresse Destinataire : "+this.getDestinataire());
		System.out.println("\tMontant : "+this.getMontant());
		System.out.println("\tSignature : "+this.getSignature());
		
	}
	public String getStringTransaction()
	{
		return this.getIndex()+this.getTimestamp()+this.getEmetteur()+this.getDestinataire()+this.getMontant()+this.getSignature();
	}
	
	public String getVerifGenesis()
	{
		return this.getEmetteur()+this.getDestinataire()+this.getSignature();
	}
	
	/**
	 * @return the emetteur
	 */
	private String getEmetteur()
	{
		return emetteur;
	}

	/**
	 * @param emetteur the emetteur to set
	 */
	private void setEmetteur(String emetteur)
	{
		this.emetteur = emetteur;
	}

	/**
	 * @return the destinataire
	 */
	private String getDestinataire()
	{
		return destinataire;
	}

	/**
	 * @param destinataire the destinataire to set
	 */
	private void setDestinataire(String destinataire)
	{
		this.destinataire = destinataire;
	}

	/**
	 * @return the montant
	 */
	private int getMontant()
	{
		return montant;
	}

	/**
	 * @param montant the montant to set
	 */
	private void setMontant(int montant)
	{
		this.montant = montant;
	}

	/**
	 * @return the index
	 */
	private int getIndex()
	{
		return index;
	}

	/**
	 * @param index the index to set
	 */
	private void setIndex(int index)
	{
		this.index = index;
	}

	/**
	 * @return the signature
	 */
	private String getSignature()
	{
		return signature;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	private void setTimestamp(Timestamp timestamp)
	{
		this.timestamp = timestamp;
	}

	private String getTimestamp()
	{
		return this.timestamp.toString();
	}
	
	private void setSignature(String sign)
	{
		this.signature = sign;
	}
	
	
}
