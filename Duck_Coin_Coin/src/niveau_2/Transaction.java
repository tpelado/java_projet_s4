package niveau_2;
import java.sql.Timestamp;
public class Transaction
{
	int index;
	String timestamp;
	String émetteur;
	String destinataire;
	int montant;
	String signature_em;
	/**
	 * @param index
	 * @param timestamp
	 * @param émetteur
	 * @param destinataire
	 * @param montant
	 * @param signature_em
	 */
	public Transaction(int index, String émetteur, String destinataire, int montant,
			String signature_em)
	{
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		this.index = index;
		this.timestamp = timestamp.toString();
		this.émetteur = émetteur;
		this.destinataire = destinataire;
		this.montant = montant;
		this.signature_em = signature_em;
	}
}
