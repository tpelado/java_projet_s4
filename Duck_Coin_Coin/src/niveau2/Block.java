package niveau2;

/**
 * VOIR NIVEAU 1 POUR DES COMMENTAIRES A JOUR, J'EN AI PROBABLEMENT OUBLIE ICI 
 */
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.concurrent.ThreadLocalRandom; // Utilisé pour générer des nombres pseudos aléatoires 

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
	 * constructeur de l'objet block si l'index est donné, alors on génère un block aléatoire d'index "index"
	 * 
	 * @param index
	 *            valeur de l'index pour le block à construire
	 */
	public Block(int index)
	{
		final SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss a");
		Timestamp test = new Timestamp(System.currentTimeMillis());
		this.timestamp = sdf.format(test);
		this.nonce = 0;
		this.index = index;
		if(index == 0) // si c'est le block genesis
		{

			Transaction[] tab_trans = new Transaction[1];
			Transaction trans = niveau2.Transaction.genererGenesis();
			tab_trans[0] = trans;
			this.nbr_transaction = 1;
			this.liste_transaction = tab_trans;
		} else
		{
			this.nbr_transaction = ThreadLocalRandom.current().nextInt(1, 10 + 1);
			this.liste_transaction = niveau2.Transaction.genererListeTransaction(this.nbr_transaction);

		}
		this.hash_precedent = "0";
		this.blockHash = null;
		this.calculerMerkelBlock();
	}

	/**
	 * constructeur alternatif de l'objet block si l'index n'est pas donné, alors on génère un block vide
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
	 * @deprecated pas utilisé ici
	 */
	public void afficherBlock()
	{
		int i = 0;
		System.out.println("index = " + this.index);
		System.out.println("Nonce = " + this.nonce);
		System.out.println("timestamp = " + this.timestamp);
		System.out.println("hash_précédent = " + this.hash_precedent);
		System.out.println("nombre de transactions = " + this.nbr_transaction);
		System.out.println("transactions = ");
		for (i = 0; i < this.nbr_transaction; i++)
		{
			System.out.println("transaction : ");
			this.getTransactionTab()[i].afficherTransaction();
		}
		System.out.println("blockHash   = " + this.blockHash);
		System.out.println("merkleRoot  = " + this.merkleRoot);

	}

	/**
	 * génère le hash de merkel en itératif
	 */
	public void calculerMerkelBlock()
	{
		int i = 0;
		String tempo1/* ,tempo2 */;
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
			tab[i] = niveau2.HashUtil.applySha256(this.liste_transaction[i].getStringTransaction());
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
				tempo1 = niveau2.HashUtil.applySha256(tempo1);
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
			if(hash.charAt(i) != '0')
			{
				satisfait = false;
			}
		}
		return satisfait;
	}

	public void calculerHashBlock(int difficulte)
	{
		String tempo;
		if(difficulte < 1)
		{
			tempo = niveau2.HashUtil.applySha256(this.hash_precedent + this.timestamp + this.merkleRoot + this.nonce);
			this.blockHash = tempo;
		} else
		{
			do
			{
				tempo = niveau2.HashUtil.applySha256(this.hash_precedent + this.timestamp + this.merkleRoot + this.nonce);
				this.nonce++;
			} while (!minage(tempo, difficulte));
			this.blockHash = tempo;
		}
	}

	/***
	 * copie le block source dans le block sur lequel la méthode est invoqué méthode utilisée pour créer une copie d'un block sans modifier l'original
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

	/**
	 * génère une liste de block pour la blockchain
	 * @param difficulte la difficulté de minage
	 * @param nbrBlocks le nombre de blocks a generer
	 * @return une liste de blocks 
	 */
	static public Block[] genererListeBlock(int difficulte, int nbrBlocks)
	{
		int i;
		Block[] listBlock = new Block[nbrBlocks];
		listBlock[0] = new Block(0); // ajoute le block genesis
		listBlock[0].calculerHashBlock(0); // calcule le hash avec une difficulté de 0 (pour ne pas toucher à la nonce)
		for (i = 1; i < nbrBlocks; i++)
		{
			listBlock[i] = new Block(i);
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
	 *            : valeur de l'index à set
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
