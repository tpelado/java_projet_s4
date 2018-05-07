package niveau1;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.concurrent.ThreadLocalRandom; // Utilis� pour g�n�rer des nombres pseudos al�atoires 
/**
 * 
 * @author Tanguy
 *
 */
public class Block
{
	private int index;
	private String timestamp;
	private String hash_precedent;
	private int nbr_transaction;
	private String merkleRoot;
	private String blockHash;
	private int nonce;
	private Transaction liste_transaction[];
	/**
	 * Le timestamp �tait originellement un objet Timestamp, mais vu qu'il pose probl�me 
	 * avec les JSON du programme C, c'est un string formatt� avec le format SDF ci dessous
	 */
	/**
	 * constructeur de l'objet block si l'index est donn�, alors on g�n�re un block al�atoire d'index "index"
	 * 
	 * @param index
	 *            valeur de l'index pour le block � construire
	 */
	public Block(int index, int nbrtransblk)
	{
		/* pour plus de d�tail sur le SimpleDateFormat, consulter la JavaDoc */
		final SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss a");
		Timestamp test = new Timestamp(System.currentTimeMillis());
		this.timestamp = sdf.format(test);
		this.nonce = 0;
		this.index = index;
		if(index == 0) // si c'est le block genesis
		{

			Transaction[] tab_trans = new Transaction[1];
			Transaction trans = new Transaction("Genesis");
			tab_trans[0] = trans;
			this.nbr_transaction = 1;
			this.liste_transaction = tab_trans;
		} else
		{
			this.nbr_transaction = ThreadLocalRandom.current().nextInt(1, nbrtransblk + 1); // on g�n�re un nombre de transactions pseudos al�atoire entre 1 et 10
			this.liste_transaction = niveau1.Transaction.genererListeTransaction(this.nbr_transaction);

		}
		this.hash_precedent = "0";
		this.blockHash = null;
		this.calculerMerkelBlock();
	}

	/**
	 * 
	 * constructeur alternatif de l'objet block si l'index n'est pas donn�, alors on g�n�re un block vide
	 **/
	public Block()
	{
		this.timestamp = null;
		this.liste_transaction = null;
		this.nbr_transaction = 0;
		this.hash_precedent = null;
		this.nonce = 0;
		this.blockHash = null;
		this.merkleRoot = null;
	}
	/**
	 * @deprecated dans la version GUI de ce projet
	 */
	public void afficherBlock()
	{
		int i = 0;
		System.out.println("index = " + this.index);
		System.out.println("Nonce = " + this.nonce);
		System.out.println("timestamp = " + this.timestamp);
		System.out.println("hash_pr�c�dent = " + this.hash_precedent);
		System.out.println("nombre de transactions = " + this.nbr_transaction);
		System.out.println("transactions = ");
		for (i = 0; i < this.nbr_transaction; i++)
		{
			System.out.println("transaction : " + this.liste_transaction[i].getTransaction());
		}
		System.out.println("blockHash   = " + this.blockHash);
		System.out.println("merkleRoot  = " + this.merkleRoot);

	}

	/**
	 * calcule le hash de merkle en it�ratif
	 * 
	 **/
	public void calculerMerkelBlock()
	{
		int i = 0;
		String tempo1;
		int nbr_trans = this.nbr_transaction;
		int j = 0;
		String[] tab;
		if((this.nbr_transaction) % 2 == 1)
		{

			tab = new String[this.nbr_transaction + 1];
		} else
		{
			tab = new String[this.nbr_transaction];
		}
		for (i = 0; i < this.nbr_transaction; i++)
		{
			tab[i] = niveau1.HashUtil.applySha256(this.liste_transaction[i].getTransaction());
		}

		while (nbr_trans > 1)
		{

			if((nbr_trans) % 2 == 1)
			{
				tab[nbr_trans] = tab[nbr_trans - 1];
				nbr_trans++;
			}
			j = 0;
			for (i = 0; i < nbr_trans; i += 2)
			{
				tempo1 = tab[i].concat(tab[i + 1]);
				tempo1 = niveau1.HashUtil.applySha256(tempo1);
				tab[j] = tempo1;
				j++;
			}
			nbr_trans /= 2;
		}
		this.merkleRoot = tab[0];
	}

	private boolean minage(String hash, int difficulte)
	{
		boolean satisfait = true;
		int i = 0;
		for (; i < difficulte; i++)
		{
			if(hash.charAt(i) != '0') // tant que le nombre de 0 devant le hash ne correspond pas � la difficult� 
			{
				satisfait = false;
			}
		}
		return satisfait;
	}
	/**
	 * calcule le hash d'un block avec la difficult� donn� en param�tre
	 * @param difficulte vient de la blockchain
	 */
	public void calculerHashBlock(int difficulte)
	{
		String tempo;
		
		if(difficulte < 1) // si pas de difficult�, on hash une seule fois et on touche pas � la none
		{
			tempo = niveau1.HashUtil.applySha256(this.hash_precedent + this.timestamp + this.merkleRoot + this.nonce);
			this.blockHash = tempo;
		} else
		{
			do
			{
				tempo = niveau1.HashUtil.applySha256(this.hash_precedent + this.timestamp + this.merkleRoot + this.nonce);
				this.nonce++;
			} while (!minage(tempo, difficulte));
			this.blockHash = tempo;
		}
	}

	/***
	 * copie le block source dans le block sur lequel la m�thode est invoqu�.
	 * m�thode utilis�e pour cr�er une copie d'un block sans modifier l'original
	 * 
	 * @param source
	 *            : un objet de type Block que l'on veut copier
	 */
	public void copyBlockFrom(Block source)
	{
		this.index = source.index;
		this.timestamp = source.timestamp;
		this.nbr_transaction = source.nbr_transaction;
		this.liste_transaction = source.liste_transaction;
		this.hash_precedent = source.hash_precedent;
		this.nonce = source.nonce;
		this.blockHash = "";
		this.calculerMerkelBlock();
	}

	
	
	static public Block[] genererListeBlock(int difficulte, int nbrBlocks, int nbrTransBlk)
	{
		int i;
		Block[] listBlock = new Block[nbrBlocks];
		listBlock[0] = new Block(0,0); // ajoute le block genesis
		listBlock[0].calculerHashBlock(0); // calcule le hash avec une difficult� de 0 (pour ne pas toucher � la nonce)
		for (i = 1; i < nbrBlocks; i++)
		{
			listBlock[i] = new Block(i,nbrTransBlk);
			listBlock[i].setHash_precedent(listBlock[i - 1].getblockHash()); // chaine les blocks
			listBlock[i].calculerHashBlock(difficulte);// calcule le hash du block
			
		}
		return listBlock;
	}

	/** -------------- setters && getters -------------- **/
	/**
	 * set the index
	 * 
	 * @param index
	 *            : valeur de l'index � set
	 */
	public void setIndex(int index)
	{
		this.index = index;
	}

	/**
	 * @return the timestamp as a string
	 */
	public String getStringTimestamp()
	{
		return timestamp.toString();
	}

	/**
	 * @param timestamp
	 *            the timestamp to set
	 */
	public void setTimestamp(String timestamp)
	{
		this.timestamp = timestamp;
	}

	/**
	 * @return the hash_precedent
	 */
	public String getHash_precedent()
	{
		return hash_precedent;
	}

	/**
	 * @param hash_precedent
	 *            the hash_precedent to set
	 */
	public void setHash_precedent(String hash_precedent)
	{
		this.hash_precedent = hash_precedent;
	}

	/**
	 * @return the merkleRoot
	 */
	public String getmerkleRoot()
	{
		return merkleRoot;
	}

	/**
	 * @param merkleRoot
	 *            the merkleRoot to set
	 */
	public void setmerkleRoot(String merkleRoot)
	{
		this.merkleRoot = merkleRoot;
	}

	/**
	 * @return the blockHash
	 */
	public String getblockHash()
	{
		return blockHash;
	}

	/**
	 * @param blockHash
	 *            the blockHash to set
	 */
	public void setblockHash(String blockHash)
	{
		this.blockHash = blockHash;
	}

	/**
	 * @return the nonce
	 */
	public int getNonce()
	{
		return nonce;
	}

	/**
	 * @param nonce
	 *            the nonce to set
	 */
	public void setNonce(int nonce)
	{
		this.nonce = nonce;
	}

	/**
	 * 
	 * @return the liste de transaction
	 */
	public Transaction[] getTransactionTab()
	{
		return this.liste_transaction;
	}

	/**
	 * 
	 * @return the index of the block
	 */
	public int getIndex()
	{
		return this.index;
	}

	public int getNbTransaction()
	{
		return this.nbr_transaction;
	}
}
