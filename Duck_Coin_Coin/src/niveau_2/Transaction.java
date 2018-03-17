package niveau_2;
import java.sql.Timestamp;
public class Transaction
{
	int index;
	String timestamp;
	String �metteur;
	String destinataire;
	int montant;
	String signature_em;
	/**
	 * @param index
	 * @param timestamp
	 * @param �metteur
	 * @param destinataire
	 * @param montant
	 * @param signature_em
	 */
	public Transaction(int index, String �metteur, String destinataire, int montant,
			String signature_em)
	{
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		this.index = index;
		this.timestamp = timestamp.toString();
		this.�metteur = �metteur;
		this.destinataire = destinataire;
		this.montant = montant;
		this.signature_em = signature_em;
	}
}
